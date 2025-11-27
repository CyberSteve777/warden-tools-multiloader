package net.trique.wardentools.item.staff;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.trique.wardentools.particle.sonic_wave.SonicWaveParticleOption;
import net.trique.wardentools.registry.DataComponentRegistry;
import net.trique.wardentools.registry.ItemRegistry;
import net.trique.wardentools.registry.TriggerTypeRegistry;
import net.trique.wardentools.util.KeyAction;
import net.trique.wardentools.util.WardenEchoStaffHelper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WardenEchoStaffItem extends EchoStaffItem {
    public WardenEchoStaffItem(Properties settings, int cooldown, int useDuration, int distance, int particleDelta, float damage, double horizontalKnockbackCoefficient, double verticalKnockbackCoefficient) {
        super(settings, cooldown, useDuration, distance, particleDelta, damage, horizontalKnockbackCoefficient, verticalKnockbackCoefficient);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (world instanceof ServerLevel serverLevel && user instanceof Player player) {
            ItemStack echoShardStack = findEchoShard(player);
            if (shouldPerformSpecialAttack(stack, user)) {
                performSpecialAttack(stack, serverLevel, user);
            } else {
                spawnSonicBoom(stack, serverLevel, user);
            }
            if (!player.hasInfiniteMaterials()) {
                echoShardStack.shrink(1);
                player.getCooldowns().addCooldown(this, cooldown);
                stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
        }
        return stack;
    }

    @Override
    protected void spawnSonicBoom(ItemStack stack, ServerLevel world, LivingEntity user) {
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.WARDEN_SONIC_BOOM, user.getSoundSource(), 5.0f, 1.0f);

        Vec3 source = user.getEyePosition();
        float enhanced_distance = calculateFinalDistance(stack, world, distance);
        Vec3 target = source.add(user.getLookAngle().scale(enhanced_distance));
        Vec3 offsetToTarget = target.subtract(source);
        Vec3 normalized = offsetToTarget.normalize();

        Set<Entity> hit = new HashSet<>();
        for (int particleIndex = 1; particleIndex <= Mth.floor(offsetToTarget.length()) + particleDelta; ++particleIndex) {
            Vec3 particlePos = source.add(normalized.scale(particleIndex));
            world.sendParticles(ParticleTypes.SONIC_BOOM, particlePos.x, particlePos.y, particlePos.z, 1, 0.0, 0.0, 0.0, 0.0);

            hit.addAll(world.getEntities(user, new AABB(new BlockPos((int) particlePos.x(),
                            (int) particlePos.y(), (int) particlePos.z())).inflate(1),
                    it -> !(it.isAlive() && it.isAlliedTo(user))));
        }

        for (Entity hitTarget : hit) {
            DamageSource damageSource = world.damageSources().sonicBoom(user);
            hitTarget.hurt(damageSource, calculateEnchantedDamage(world, stack, hitTarget, damageSource, damage));
            if (hitTarget instanceof LivingEntity living) {
                double vertical = verticalKnockbackCoefficient * (1.0 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                double horizontal = horizontalKnockbackCoefficient * (1.0 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                living.push(normalized.x() * horizontal, normalized.y() * vertical, normalized.z() * horizontal);
            }
            if (!hitTarget.isAlive()){
                int charges = stack.getOrDefault(DataComponentRegistry.CHARGE_COUNT.get(), 0);
                stack.set(DataComponentRegistry.CHARGE_COUNT.get(), ++charges);
            }
        }
        if (user instanceof ServerPlayer player) {
            TriggerTypeRegistry.AFFECTED_ENTITIES_TRIGGER.get().trigger(player, stack, hit);
        }

    }

    private int calculateAmountOfChargesToConsume(ItemStack stack, LivingEntity user) {
        if (user instanceof Player) {
            int charges = stack.getOrDefault(DataComponentRegistry.CHARGE_COUNT.get(), 0);
            return Math.min(5, charges);
        }
        return 5;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.literal("Charges: " + stack.getOrDefault(DataComponentRegistry.CHARGE_COUNT.get(), 0)));
    }

    private boolean shouldPerformSpecialAttack(ItemStack stack, LivingEntity user) {
        int charges = calculateAmountOfChargesToConsume(stack, user);
        if (user instanceof Player player) {
            return charges > 0 && WardenEchoStaffHelper.playerPressedButton(player, KeyAction.CONSUME_CHARGES);
        }
        return charges == 5;
    }

    protected void performSpecialAttack(ItemStack stack, ServerLevel world, LivingEntity user) {
        int charges = calculateAmountOfChargesToConsume(stack, user);
        float r = calculateFinalDistance(stack, world, 5);
        Vec3 center = user.position();
        List<Entity> entities = world.getEntities(user, new AABB(center, center).inflate(r), it -> it.isAlive() &&
                it.distanceToSqr(center) <= r * r && !(it.isAlliedTo(user)));
        for (Entity entity : entities) {
            float distSq = entity.distanceTo(user);
            float final_damage = calculateDamage(damage,distSq,charges,r);
            DamageSource damageSource = world.damageSources().sonicBoom(user);
            entity.hurt(damageSource, calculateEnchantedDamage(world, stack, entity, damageSource, final_damage));

            if (entity instanceof LivingEntity living) {
                double vertical = verticalKnockbackCoefficient * (1.0 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                double horizontal = horizontalKnockbackCoefficient * (1.0 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));

                Vec3 normalized = entity.position().subtract(user.position()).normalize();

                living.push(normalized.x() * horizontal, normalized.y() * vertical, normalized.z() * horizontal);
            }
        }
        stack.set(DataComponentRegistry.CHARGE_COUNT.get(), Math.max(0, stack.getOrDefault(DataComponentRegistry.CHARGE_COUNT.get(), 0) - charges));
//        for (int i = 0; i < 10; i++) {
//
//        }
        world.sendParticles(new SonicWaveParticleOption(0,r),center.x, center.y, center.z, 1, 0.0, 0.0, 0.0, 0);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack ingredient) {
        return ingredient.is(ItemRegistry.WARDEN_INGOT.get());
    }

    private float calculateDamage(float base_damage, float distance, int charges, float radius){
        float damage = base_damage*1.5f;
        float damage_per_stack_multiplier = 0.1f;
        float maxMinDamage = damage/2;
        damage = damage * (1+(charges*damage_per_stack_multiplier));
        float falloff_multiplier = (damage-maxMinDamage) / radius;
        damage -= falloff_multiplier*(distance-1);
        return damage;
    }
}
