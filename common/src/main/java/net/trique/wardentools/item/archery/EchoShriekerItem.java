package net.trique.wardentools.item.archery;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.trique.wardentools.item.util.ISonicBoomItem;
import net.trique.wardentools.particle.echo_particle.EchoParticleOption;
import net.trique.wardentools.platform.Services;
import net.trique.wardentools.registry.ItemRegistry;
import net.trique.wardentools.registry.TriggerTypeRegistry;
import net.trique.wardentools.util.WTEnchantmentHelper;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EchoShriekerItem extends BowItem implements ISonicBoomItem {
    private final float DEFAULT_DISTANCE = 20f;
    private final float BASE_DAMAGE = 40f;
    private final float BONUS_DAMAGE_MULTIPLIER = 2f; // multiplier for close-range
    private final float BONUS_DAMAGE_RANGE = 2f;

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
            world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.WARDEN_SONIC_CHARGE, user.getSoundSource(), 3.0f, 1.0f);
            return InteractionResultHolder.consume(itemStack);
        }
    }

    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (world instanceof ServerLevel serverLevel && user instanceof Player player) {
            int i = this.getUseDuration(stack, user) - remainingUseTicks;
            float loadAmount = getPowerForTime(i);
            ItemStack ammo = findEchoShard(player);
            if (loadAmount >= 0.1f) {
                spawnSonicBoom(stack, serverLevel, user, loadAmount);
                if (!player.isCreative()) {
                    player.getCooldowns().addCooldown(this, WTEnchantmentHelper.getCooldown(serverLevel, stack, 140));
                    stack.hurtAndBreak(1, user, EquipmentSlot.MAINHAND);
                    ammo.shrink(1);
                }
                player.awardStat(Stats.ITEM_USED.get(this));
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
        return ingredient.is(ItemRegistry.WARDEN_INGOT.get());
    }

    private void spawnSonicBoom(ItemStack stack, ServerLevel world, LivingEntity user, float remainTicks) {
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.SCULK_SHRIEKER_SHRIEK, user.getSoundSource(), 5.0f, 1.0f);
        Vec3 source = user.position().add(0.0, user.getEyeHeight(), 0.0);
        float enhanced_distance = calculateFinalDistance(stack, world, DEFAULT_DISTANCE) * remainTicks * remainTicks;
        Vec3 target = source.add(user.getLookAngle().scale(enhanced_distance));
        Vec3 offsetToTarget = target.subtract(source);
        Vec3 normalized = offsetToTarget.normalize();
        Set<Entity> hit = new HashSet<>();
        AABB cube = new AABB(new BlockPos((int) source.x(),
                (int) source.y(), (int) source.z())).inflate(enhanced_distance);
        hit.addAll(world.getEntities(user, cube, it -> isAABBInConeSimple(source, offsetToTarget, it.getBoundingBox()) && !((it.isAlliedTo(user)) || (it instanceof TamableAnimal helper && helper.isOwnedBy(user)))));
        for (float particleScale = 1; particleScale <= offsetToTarget.length(); particleScale++) {
            Vec3 particlePos = source.add(normalized.scale(particleScale));
            world.sendParticles(new EchoParticleOption(particleScale * 1.4f, user.getXRot(), user.getYRot()), particlePos.x, particlePos.y, particlePos.z,
                    1, 0, 0, 0, 0);
        }
        hit.remove(user);
        for (Entity hitTarget : hit) {
            float distanceToTarget = user.distanceTo(hitTarget);
            float baseDamage = calculateBaseDamage(remainTicks, distanceToTarget);
            DamageSource damageSource = world.damageSources().sonicBoom(user);
            float enchantedDamage = calculateEnchantedDamage(world, stack, hitTarget, damageSource, baseDamage);
            if (hitTarget instanceof LivingEntity living) {
                living.hurt(damageSource, enchantedDamage);
                living.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100));
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
                living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 160, 2));
                living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60));
                double vertical = WTEnchantmentHelper.modifyKnockback(world, stack, living, damageSource, 2f) * (1.0 - (float) living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                double horizontal = WTEnchantmentHelper.modifyKnockback(world, stack, living, damageSource, 6f) * (1.0 - (float) living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                living.push(normalized.x() * horizontal, normalized.y() * vertical, normalized.z() * horizontal);
            }
        }
        if (user instanceof ServerPlayer player) {
            TriggerTypeRegistry.AFFECTED_ENTITIES_TRIGGER.get().trigger(player, stack, hit);
        }
    }

    public static boolean isAABBInConeSimple(Vec3 vertex, Vec3 axisVector, AABB aabb) {
        float angleDegrees = (float) Math.toRadians(15);
        float axisLength = (float) axisVector.length();
        if (axisLength == 0) return false;
        Vec3 axisUnit = axisVector.normalize();
        float tanAngle = (float) Math.tan(angleDegrees);

        // Checking center of AABB
        Vec3 center = aabb.getCenter();

        Vec3 toCenter = center.subtract(vertex);
        float centerHeight = (float) toCenter.dot(axisUnit);

        // if the center is outside "cone heights"
        if (centerHeight < 0 || centerHeight > axisLength) {
            return false;
        }

        // Checking distance between center and axis
        Vec3 projection = axisUnit.scale(centerHeight);
        Vec3 perpendicular = toCenter.subtract(projection);
        float distanceToAxis = (float) perpendicular.length();

        // Check if center inside the cone
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

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        String echo_shard = Items.ECHO_SHARD.getDescription().getString();
        if (Services.PLATFORM.isClient()) {
            if (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT) ||
                    InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_RIGHT_SHIFT)) {
                tooltipComponents.add(Component.translatable("wardentools.warden_echo_shrieker_desc", echo_shard, BONUS_DAMAGE_RANGE, BONUS_DAMAGE_MULTIPLIER).withStyle(ChatFormatting.DARK_AQUA, ChatFormatting.ITALIC));
            } else {
                tooltipComponents.add(Component.translatable("wardentools.warden_echo_shrieker_damage", BASE_DAMAGE).withStyle(ChatFormatting.AQUA));
                tooltipComponents.add(Component.translatable("wardentools.warden_echo_shrieker_distance", DEFAULT_DISTANCE).withStyle(ChatFormatting.AQUA));
                tooltipComponents.add(Component.empty());
                tooltipComponents.add(Component.translatable("wardentools.warden_echo_shrieker_hint").withStyle(ChatFormatting.DARK_AQUA, ChatFormatting.ITALIC));
            }
        }
    }

    private float calculateBaseDamage(float chargingAmount, float distanceToTarget) {
        float maxMinDamage = BASE_DAMAGE / 3; // minimum possible damage after falloff (but without charging amount
        float damage = BASE_DAMAGE;
        float falloff_multiplier = (BASE_DAMAGE - maxMinDamage) / (DEFAULT_DISTANCE - 2); // damage falloff per block
        if (Math.floor(distanceToTarget) >= BONUS_DAMAGE_RANGE) {
            damage -= (distanceToTarget - BONUS_DAMAGE_RANGE) * falloff_multiplier;
            if (damage < maxMinDamage) damage = maxMinDamage;
        } else {
            damage *= BONUS_DAMAGE_MULTIPLIER;
        }
        return damage * chargingAmount;
    }

    @Override
    public int getEnchantmentValue() {
        return 21;
    }
}
