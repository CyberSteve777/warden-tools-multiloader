package net.trique.wardentools.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.trique.wardentools.item.util.ISonicBoomItem;

public class SonicBoostEnchantment extends DamageEnchantment {
    public SonicBoostEnchantment(Rarity rarity, EquipmentSlot... applicableSlots) {
        super(rarity, ALL, applicableSlots);
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ISonicBoomItem;
    }
}
