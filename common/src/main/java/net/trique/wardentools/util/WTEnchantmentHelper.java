package net.trique.wardentools.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.apache.commons.lang3.mutable.MutableFloat;

public class WTEnchantmentHelper extends EnchantmentHelper {
    public float getRangeBonus(ServerLevel serverLevel, ItemStack tool, Entity entity, float projectileSpread) {
        MutableFloat mutablefloat = new MutableFloat(projectileSpread);
//        runIterationOnItem(tool, ((enchantmentHolder, level) -> enchantmentHolder.value().modifyDamage()));
        return Math.max(0.0F, mutablefloat.floatValue());
    }
}
