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
import net.trique.wardentools.Constants;
import net.trique.wardentools.particle.sonic_wave.SonicWaveParticleOption;
import net.trique.wardentools.registry.DataComponentRegistry;
import net.trique.wardentools.registry.ItemRegistry;
import net.trique.wardentools.registry.TriggerTypeRegistry;
import net.trique.wardentools.util.KeyAction;
import net.trique.wardentools.util.WTEnchantmentHelper;
import net.trique.wardentools.util.WardenEchoStaffHelper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.trique.wardentools.config.WTConfigServer.CONFIG;

public class WardenEchoStaffItem extends EchoStaffItem {
    protected final float BASE_ATTACK_CHARGE_TIME = 20f;
    protected final float SPECIAL_ATTACK_CHARGE_TIME = 100f;

    public WardenEchoStaffItem(Properties settings, int cooldown, int distance, float damage, float horizontalKnockbackCoefficient, float verticalKnockbackCoefficient) {
        super(settings, cooldown, distance, damage, horizontalKnockbackCoefficient, verticalKnockbackCoefficient);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUsageTicks) {
        if (world instanceof ServerLevel serverLevel && user instanceof Player player) {
            ItemStack echoShardStack = findEchoShard(player);
            int tick_progress = this.getUseDuration(stack, user) - remainingUsageTicks;
            if (shouldPerformSpecialAttack(stack, user, tick_progress)) {
                performSpecialAttack(stack, serverLevel, user, tick_progress);
            } else {
                float progress = getChargePowerForTime(tick_progress, BASE_ATTACK_CHARGE_TIME);
                if (progress >= 0.95f) {
                    spawnSonicBoom(stack, serverLevel, user);
                }
            }
            if (!player.hasInfiniteMaterials()) {
                echoShardStack.shrink(1);
                player.getCooldowns().addCooldown(this, WTEnchantmentHelper.getCooldown(serverLevel, stack, cooldown));
                stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
        }
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
        for (int particleIndex = 1; particleIndex <= Mth.floor(offsetToTarget.length()) + 7; ++particleIndex) {
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
                living.hurt(damageSource, calculateEnchantedDamage(world, stack, hitTarget, damageSource, damage));
                double vertical = WTEnchantmentHelper.modifyKnockback(world, stack, living, damageSource, verticalKnockbackCoefficient * (1.0f - (float) living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));
                double horizontal = WTEnchantmentHelper.modifyKnockback(world, stack, living, damageSource, horizontalKnockbackCoefficient * (1.0f - (float) living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));
                living.push(normalized.x() * horizontal, normalized.y() * vertical, normalized.z() * horizontal);
                if (!living.isAlive() && living.getLastDamageSource() instanceof DamageSource kill_source && kill_source.equals(damageSource)) {
                    int charges = stack.getOrDefault(DataComponentRegistry.CHARGE_COUNT.get(), 0);
                    stack.set(DataComponentRegistry.CHARGE_COUNT.get(), ++charges);
                }
            }

        }
        if (user instanceof ServerPlayer player) {
            TriggerTypeRegistry.AFFECTED_ENTITIES_TRIGGER.get().trigger(player, stack, hit);
        }

    }

    private int calculateAmountOfChargesToConsume(ItemStack stack, LivingEntity user, int tick_progress) {
        if (user instanceof Player) {
            int charges = stack.getOrDefault(DataComponentRegistry.CHARGE_COUNT.get(), 0);
            return Math.min(Mth.floor(Math.min(1f, tick_progress / SPECIAL_ATTACK_CHARGE_TIME) * CONFIG.max_charges.get()), charges);
        }
        return CONFIG.max_charges.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        int charges = stack.getOrDefault(DataComponentRegistry.CHARGE_COUNT.get(), 0);
        tooltipComponents.add(Component.literal(String.format("Charges: %d", charges)));
        tooltipComponents.add(Component.literal(String.format("Basic attack damage: %.0f\nSpecial attack damage: %.0f", damage, damage * 1.5f)));
        tooltipComponents.add(Component.literal(String.format("Basic attack range: %d\nSpecial attack range: %.0f", distance, 5f)));
    }

    private boolean shouldPerformSpecialAttack(ItemStack stack, LivingEntity user, int charge_ticks) {
        int charges = calculateAmountOfChargesToConsume(stack, user, charge_ticks);
        boolean res = charges > 0;
        if (user instanceof Player player) {
            res &= WardenEchoStaffHelper.playerPressedButton(player, KeyAction.CONSUME_CHARGES);
        }
        return res;
    }

    protected void performSpecialAttack(ItemStack stack, ServerLevel world, LivingEntity user, int tick_progress) {
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.WARDEN_SONIC_BOOM, user.getSoundSource(), 5.0f, 1.0f);
        int charges = calculateAmountOfChargesToConsume(stack, user, tick_progress);
        float r = calculateFinalDistance(stack, world, 5);
        Vec3 center = user.position();
        world.sendParticles(new SonicWaveParticleOption(0, r), center.x, center.y, center.z, 1, 0.0, 0.0, 0.0, 0);
        Set<Entity> entities = new HashSet<>(world.getEntities(user, new AABB(center, center).inflate(r), it -> it.isAlive() &&
                it.distanceToSqr(center) <= r * r && !(it.isAlliedTo(user))));
        for (Entity entity : entities) {
            float distSq = entity.distanceTo(user);
            float final_damage = calculateDamage(damage, distSq, charges, r);
            DamageSource damageSource = world.damageSources().sonicBoom(user);
            entity.hurt(damageSource, calculateEnchantedDamage(world, stack, entity, damageSource, final_damage));

            if (entity instanceof LivingEntity living) {
                living.hurt(damageSource, calculateEnchantedDamage(world, stack, entity, damageSource, final_damage));
                double vertical = verticalKnockbackCoefficient * (1.0 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                double horizontal = horizontalKnockbackCoefficient * (1.0 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));

                Vec3 normalized = entity.position().subtract(user.position()).normalize();

                living.push(normalized.x() * horizontal, normalized.y() * vertical, normalized.z() * horizontal);
            }
        }
        if (user instanceof ServerPlayer player) {
            TriggerTypeRegistry.AFFECTED_ENTITIES_TRIGGER.get().trigger(player, stack, entities);
        }
        stack.set(DataComponentRegistry.CHARGE_COUNT.get(), Math.max(0, stack.getOrDefault(DataComponentRegistry.CHARGE_COUNT.get(), 0) - charges));
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack ingredient) {
        return ingredient.is(ItemRegistry.WARDEN_INGOT.get());
    }

    private float calculateDamage(float base_damage, float distance, int charges, float radius) {
        float damage = base_damage * 1.5f;
        float damage_per_stack_multiplier = 0.1f;
        float maxMinDamage = damage / 2;
        damage = damage * (1 + (charges * damage_per_stack_multiplier));
        float falloff_multiplier = (damage - maxMinDamage) / radius;
        damage -= falloff_multiplier * (distance - 1);
        return damage;
    }

    @Override
    public int getEnchantmentValue() {
        return 21;
    }
}
