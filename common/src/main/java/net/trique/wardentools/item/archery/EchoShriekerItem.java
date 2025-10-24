package net.trique.wardentools.item.archery;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.trique.wardentools.particle.echo_particle.EchoParticleOption;
import net.trique.wardentools.registry.ItemRegistry;

import java.util.HashSet;
import java.util.Set;

public class EchoShriekerItem extends BowItem {
    private final float MAX_DISTANCE = 30f;

    public EchoShriekerItem(Properties settings) {
        super(settings.attributes(createAttributeModifiers()));
    }

    public static ItemAttributeModifiers createAttributeModifiers() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 3.0f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, 0f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        boolean bl = !findEchoShard(user).isEmpty();
        if (!user.hasInfiniteMaterials() && !bl) {
            return InteractionResultHolder.fail(itemStack);
        } else {
            user.startUsingItem(hand);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.WARDEN_SONIC_CHARGE, SoundSource.BLOCKS, 3.0f, 1.0f);
            return InteractionResultHolder.consume(itemStack);
        }
    }

    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (!world.isClientSide() && user instanceof Player player) {
            int i = this.getUseDuration(stack, user) - remainingUseTicks;
            float loadAmount = getPowerForTime(i);
            ItemStack ammo = findEchoShard(player);
            if (!((double) loadAmount < 0.1f)) {
                spawnSonicBoom(world, user, loadAmount);
                if (!player.isCreative()) {
                    player.getCooldowns().addCooldown(this, 120);
                    stack.hurtAndBreak(1, user, EquipmentSlot.MAINHAND);
                    ammo.shrink(1);
                }
            }
        }
    }

    protected ItemStack findEchoShard(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(Items.ECHO_SHARD)) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    public boolean isValidRepairItem(ItemStack stack, ItemStack ingredient) {
        return ingredient.is(ItemRegistry.SHRIEKER_FANG.get());
    }

    private void spawnSonicBoom(Level world, LivingEntity user, float remainTicks) {
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.SCULK_SHRIEKER_SHRIEK, user.getSoundSource(), 5.0f, 1.0f);
        float distance = MAX_DISTANCE * remainTicks;
        Vec3 source = user.position().add(0.0, user.getEyeHeight(), 0.0);
        Vec3 target = source.add(user.getLookAngle().scale(distance));
        Vec3 offsetToTarget = target.subtract(source);
        Vec3 normalized = offsetToTarget.normalize();
        Set<Entity> hit = new HashSet<>();
        AABB cube = new AABB(new BlockPos((int) source.x(),
                (int) source.y(), (int) source.z())).inflate(distance);
        hit.addAll(world.getEntitiesOfClass(LivingEntity.class, cube, it -> isAABBInConeSimple(source, offsetToTarget, it.getBoundingBox()) && !((it.isAlliedTo(user)) || (it instanceof TamableAnimal helper && helper.isOwnedBy(user)))));

        for (float particleScale = 1; particleScale < offsetToTarget.length(); particleScale++) {
            Vec3 particlePos = source.add(normalized.scale(particleScale));
            ((ServerLevel) world).sendParticles(new EchoParticleOption(particleScale * 1.4f, user.getXRot(), user.getYRot()), particlePos.x, particlePos.y, particlePos.z,
                    1, 0, 0, 0, 0);
        }

        hit.remove(user);

        for (Entity hitTarget : hit) {
            if (hitTarget instanceof LivingEntity living) {
                float distanceToTarget = user.distanceTo(living);
                float damage = calculateDamage(remainTicks, distanceToTarget);
                living.hurt(world.damageSources().sonicBoom(user), damage);
                living.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100));
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
                living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 160, 2));
                living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60));
                double vertical = 0.5 * (1.0 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                double horizontal = 2.5 * (1.0 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                living.push(normalized.x() * horizontal, normalized.y() * vertical, normalized.z() * horizontal);
            }
        }
    }

    public static boolean isAABBInConeSimple(Vec3 vertex, Vec3 axisVector, AABB aabb) {
        double angleDegrees = (float) Math.toRadians(15);
        float axisLength = (float) axisVector.length();
        if (axisLength == 0) return false;

        Vec3 axisUnit = new Vec3(axisVector.x / axisLength, axisVector.y / axisLength, axisVector.z / axisLength);
        float tanAngle = (float) Math.tan(angleDegrees);

        // Checking center of AABB
        Vec3 center = aabb.getCenter();

        Vec3 toCenter = center.subtract(vertex);
        float centerHeight = (float) toCenter.dot(axisUnit);

        // Если центр вне диапазона высот конуса
        if (centerHeight < 0 || centerHeight > axisLength) {
            return false;
        }

        // Checking distance between center and axis
        Vec3 projection = axisUnit.scale(centerHeight);
        Vec3 perpendicular = toCenter.subtract(projection);
        float distanceToAxis = (float) perpendicular.length();

        // Is center inside of the cone
        if (distanceToAxis <= centerHeight * tanAngle) {
            return true;
        }

        // If center isn't in the cone, checking closest point of the AABB to the cone
        Vec3 closestPoint = getClosestPointOnAABB(aabb, vertex, axisUnit, centerHeight);
        Vec3 toClosest = closestPoint.subtract(vertex);
        double closestHeight = toClosest.dot(axisUnit);

        if (closestHeight < 0 || closestHeight > axisLength) {
            return false;
        }

        Vec3 closestProjection = axisUnit.scale(closestHeight);
        Vec3 closestPerpendicular = toClosest.subtract(closestProjection);
        double closestDistance = closestPerpendicular.length();

        return closestDistance <= closestHeight * tanAngle;
    }

    private static Vec3 getClosestPointOnAABB(AABB aabb, Vec3 vertex, Vec3 axisUnit,
                                              double centerHeight) {

        Vec3 pointOnAxis = vertex.add(axisUnit.scale(centerHeight));
        double closestX = Math.max(aabb.minX, Math.min(pointOnAxis.x, aabb.maxX));
        double closestY = Math.max(aabb.minY, Math.min(pointOnAxis.y, aabb.maxY));
        double closestZ = Math.max(aabb.minZ, Math.min(pointOnAxis.z, aabb.maxZ));

        return new Vec3(closestX, closestY, closestZ);
    }

    private float calculateDamage(float chargingAmount, float distanceToTarget) {
        float baseDamage = 40f; // base damage
        float amplifier = 2f; // multiplier for close-range
        float maxMinDamage = baseDamage / 3; // minimum possible damage after falloff
        float damage = baseDamage;
        if (Math.floor(distanceToTarget) >= 2) {
            damage *= (1 - ((distanceToTarget - 2) * ((baseDamage - maxMinDamage) / (MAX_DISTANCE - 2))));
            if (damage < maxMinDamage) damage = maxMinDamage;
        } else {
            damage *= amplifier;
        }
        return damage * chargingAmount;
    }
}
