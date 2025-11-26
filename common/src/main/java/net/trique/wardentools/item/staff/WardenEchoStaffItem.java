package net.trique.wardentools.item.staff;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
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
import net.trique.wardentools.util.KeyAction;
import net.trique.wardentools.util.WardenEchoStaffHelper;

import java.util.List;

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
        super.spawnSonicBoom(stack, world, user);
        int charges = stack.getOrDefault(DataComponentRegistry.CHARGE_COUNT.get(), 0);
        stack.set(DataComponentRegistry.CHARGE_COUNT.get(), ++charges);
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
        double r = calculateFinalDistance(stack, world, 5);
        Vec3 center = user.position();
        List<Entity> entities = world.getEntities(user, new AABB(center, center).inflate(r), it -> !(it.isAlive() && it.isAlliedTo(user)));
        for (Entity entity : entities) {
            double distSq = entity.distanceToSqr(center);
            double scaled = (1 + charges / 20d) / (distSq + 1);
            DamageSource damageSource = world.damageSources().sonicBoom(user);
            entity.hurt(damageSource, calculateEnchantedDamage(world, stack, entity, damageSource, (float) (scaled * damage)));

            if (entity instanceof LivingEntity living) {
                double vertical = verticalKnockbackCoefficient * (1.0 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                double horizontal = horizontalKnockbackCoefficient * (1.0 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));

                Vec3 normalized = entity.position().subtract(user.position()).normalize();

                living.push(normalized.x() * horizontal, normalized.y() * vertical, normalized.z() * horizontal);
            }
        }
        stack.set(DataComponentRegistry.CHARGE_COUNT.get(), Math.max(0, stack.getOrDefault(DataComponentRegistry.CHARGE_COUNT.get(), 0) - charges));
        for (int i = 0; i < 10; i++) {
            world.sendParticles(new SonicWaveParticleOption(i * 5), center.x, center.y, center.z, 1, 0.0, 0.0, 0.0, 0);
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack ingredient) {
        return ingredient.is(ItemRegistry.WARDEN_INGOT.get());
    }
}
