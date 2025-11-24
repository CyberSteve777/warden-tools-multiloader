package net.trique.wardentools.item.staff;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.trique.wardentools.Constants;
import net.trique.wardentools.registry.DataComponentRegistry;
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
        return super.finishUsingItem(stack, world, user);
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
        tooltipComponents.add(Component.literal("Charges: "+stack.getOrDefault(DataComponentRegistry.CHARGE_COUNT.get(),0)));
    }

    private boolean shouldPerformSpecialAttack(ItemStack stack, LivingEntity user) {
        int charges = stack.getOrDefault(DataComponentRegistry.CHARGE_COUNT.get(), 0);
        if (user instanceof Player player) {
            return charges > 0 && WardenEchoStaffHelper.playerPressedButton(player, KeyAction.CONSUME_CHARGES);
        }
        return charges == 5;
    }

    protected void performSpecialAttack(ItemStack stack, ServerLevel world, LivingEntity user) {
        Constants.LOGGER.info("Special Attack!");
    }
}
