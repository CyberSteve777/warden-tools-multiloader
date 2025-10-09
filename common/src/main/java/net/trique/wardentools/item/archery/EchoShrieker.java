package net.trique.wardentools.item.archery;

import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
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
import net.trique.wardentools.Constants;
import net.trique.wardentools.particle.ShriekParticle.ShriekParticleOptions;
import net.trique.wardentools.registry.ItemRegistry;
import net.trique.wardentools.registry.ParticleRegistry;

import java.util.HashSet;
import java.util.Set;

public class EchoShrieker extends BowItem {
    public EchoShrieker(Properties settings) {
        super(settings.attributes(createAttributeModifiers()));
    }

    private float remainTicks;


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
            remainTicks = loadAmount;
            ItemStack ammo = findEchoShard(player);
            if (!((double) loadAmount < 0.1f)) {
                if (!player.isCreative()) {
                    spawnSonicBoom(world, user);
                    player.getCooldowns().addCooldown(this, 120);
                    stack.hurtAndBreak(1, user, EquipmentSlot.MAINHAND);
                    ammo.shrink(1);
                } else {
//                    Constants.LOGGER.info(loadAmount);
                    spawnSonicBoom(world, user);
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

    private void spawnSonicBoom(Level world, LivingEntity user) {
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.BLOCKS, 5.0f, 1.0f);

        float heightOffset = 1.6f;
        float base_distance = 30f;
        float final_distance = base_distance*remainTicks;
        Vec3 target = user.position().add(user.getLookAngle().scale(final_distance));
        Vec3 source = user.position().add(0.0, heightOffset, 0.0);
        Vec3 offsetToTarget = target.subtract(source);
        Vec3 normalized = offsetToTarget.normalize();


        Set<Entity> hit = new HashSet<>();
        double expansion = 0.5;
        for (int particleIndex = 1; particleIndex < Mth.floor(offsetToTarget.length()); ++particleIndex) {
            Vec3 particlePos = source.add(normalized.scale(particleIndex));
            ((ServerLevel) world).sendParticles(new ShriekParticleOptions(particleIndex*1.4f), particlePos.x, particlePos.y, particlePos.z, 1, 0, 0, 0, 0.0);
            //world.addParticle(new ShriekParticleOptions(1),true, particlePos.x, particlePos.y, particlePos.z, 1, 0,0);

            hit.addAll(world.getEntitiesOfClass(LivingEntity.class, new AABB(new BlockPos((int) particlePos.x(),
                            (int) particlePos.y(), (int) particlePos.z())).inflate(expansion),
                    it -> !(it instanceof TamableAnimal helper && helper.isOwnedBy(user))));
            expansion += 0.1;
        }

        hit.remove(user);

        for (Entity hitTarget : hit) {
            if (hitTarget instanceof LivingEntity living) {
                float distanceToTarget = user.distanceTo(living);
                float damage = finalDamage(remainTicks, distanceToTarget, living);
//                Constants.LOGGER.info("Damage {}\nDistance {}", damage, distanceToTarget);
                living.hurt(world.damageSources().sonicBoom(user), damage);
                living.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100));
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,100,2));
                living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,160,2));
                living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,60));
                double vertical = 0.5 * (1.0 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                double horizontal = 2.5 * (1.0 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                living.push(normalized.x() * horizontal, normalized.y() * vertical, normalized.z() * horizontal);
            }
        }
    }

    private float finalDamage(float chargingAmount, float distanceToTarget, LivingEntity target) {
        float baseDamage = 15f; // Damage if % from target Max hp is very low
        float amplifier = 20f;

        float damage = target.getMaxHealth() * (amplifier / 100);
        if (Math.floor(damage) < baseDamage) {
            damage = baseDamage;
        }

        if (Math.floor(distanceToTarget) > 1) {
            damage *= (1 - (distanceToTarget * 0.03f));
            if (damage < 1) damage = 1;
        }

        return damage * chargingAmount;
    }
}
