package net.trique.wardentools.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.trique.wardentools.registry.EnchantmentEffectComponentRegistry;
import org.apache.commons.lang3.mutable.MutableFloat;

public class WTEnchantmentHelper extends EnchantmentHelper {
    public static float getRangeBonus(ServerLevel serverLevel, ItemStack tool) {
        MutableFloat mutablefloat = new MutableFloat();
        runIterationOnItem(tool, ((enchantmentHolder, level) -> {
            enchantmentHolder.value().modifyUnfilteredValue(EnchantmentEffectComponentRegistry.INCREASE_RANGE.get(),
                    serverLevel.getRandom(), level, mutablefloat);
        }));
        return Math.max(0.0F, mutablefloat.floatValue());
    }
}
