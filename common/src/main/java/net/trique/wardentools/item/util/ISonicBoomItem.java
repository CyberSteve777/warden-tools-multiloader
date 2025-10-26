package net.trique.wardentools.item.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.trique.wardentools.util.WTEnchantmentHelper;

public interface ISonicBoomItem {
    default float calculateEnchantedDamage(ServerLevel level, ItemStack tool, Entity entity, DamageSource damageSource, float damage) {
        return WTEnchantmentHelper.modifyDamage(level, tool, entity, damageSource, damage);
    }

    default float calculateBonusDistance(ItemStack stack, ServerLevel world) {
        return WTEnchantmentHelper.getRangeBonus(world, stack);
    }

    default float getChargePowerForTime(int charge) {
        float f = (float)charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
    }
}
