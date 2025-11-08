package net.trique.wardentools.item.armor;

import me.cybersteve.equiplib.armorset.base.EffectArmorSet;
import me.cybersteve.equiplib.item.armor.base.IEffectArmorItem;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;


public class WardenArmorItem extends ArmorItem implements IEffectArmorItem {
    protected EffectArmorSet set;

    public WardenArmorItem(Holder<ArmorMaterial> material, Type type, Properties settings, EffectArmorSet set) {
        super(material, type, settings);
        this.set = set;
    }

    @Override
    public EffectArmorSet getEffectArmorSet() {
        return set;
    }
}
