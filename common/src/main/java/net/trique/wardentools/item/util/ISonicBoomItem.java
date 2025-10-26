package net.trique.wardentools.item.util;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.trique.wardentools.util.WTEnchantments;

public interface ISonicBoomItem {
    default float calculateEnchantedDamage(ServerLevel level, ItemStack tool, Entity entity, DamageSource damageSource, float damage) {
        return EnchantmentHelper.modifyDamage(level, tool, entity, damageSource, damage);
    }

    default float calculateBonusDistance(ItemStack stack, Level world) {
        Holder<Enchantment> echo_concentration = world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolder(WTEnchantments.PROPAGATION).orElseThrow();
        int level = EnchantmentHelper.getItemEnchantmentLevel(echo_concentration, stack);
        return 5f * level;
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
