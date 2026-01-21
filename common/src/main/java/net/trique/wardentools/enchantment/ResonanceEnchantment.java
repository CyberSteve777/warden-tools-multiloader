package net.trique.wardentools.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.trique.wardentools.item.util.ISonicBoomItem;

public class ResonanceEnchantment extends Enchantment {
    public ResonanceEnchantment(Rarity rarity, EquipmentSlot... applicableSlots) {
        super(rarity, EnchantmentCategory.WEAPON, applicableSlots);
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ISonicBoomItem;
    }
}
