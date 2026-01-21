package net.trique.wardentools.effect_armor_set;

import me.cybersteve.equiplib.armorset.base.EffectArmorSet;
import me.cybersteve.equiplib.item.armor.base.IEffectArmorItem;
import me.cybersteve.equiplib.util.ArmorSetHelper;
import me.cybersteve.equiplib.util.EffectList;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.trique.wardentools.registry.EffectRegistry;
import net.trique.wardentools.registry.ItemRegistry;

import java.util.List;

import static net.trique.wardentools.util.ModHelper.getLoc;

public class WardenSet extends EffectArmorSet {

    public WardenSet() {
        super(getLoc("warden_set"));
    }

    @Override
    public EffectList getEffectsWhenWearing(LivingEntity entity) {
        EffectList.Builder builder = new EffectList.Builder();
        if (!entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.WARDEN_MASK.get())) {
            if (ArmorSetHelper.hasFullEffectSetArmorOn(entity, this)) {
                builder = builder.addInfiniteEffect(EffectRegistry.SCULK_ADAPTION, 0, true, false, true)
                        .addInfiniteEffect(MobEffects.FIRE_RESISTANCE, 0, true, false, true)
                        .addInfiniteEffect(MobEffects.DAMAGE_BOOST, 3, true, false, true)
                        .addInfiniteEffect(MobEffects.HEALTH_BOOST, 4, true, false, true);
            }
        } else {
            int wardenCurseAmpl = 0;
            for (EquipmentSlot slot : List.of(EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)) {
                if (entity.getItemBySlot(slot).getItem() instanceof IEffectArmorItem effectArmorItem &&
                        effectArmorItem.getEffectArmorSet().equals(this)) {
                    wardenCurseAmpl++;
                }
            }
            builder = builder.addInfiniteEffect(EffectRegistry.WARDEN_CURSE, wardenCurseAmpl, true, false, true);
            if (ArmorSetHelper.hasFullEffectSetArmorOn(entity, this)) {
                builder = builder.addInfiniteEffect(EffectRegistry.SCULK_ADAPTION, 0, true, false, true)
                        .addInfiniteEffect(MobEffects.FIRE_RESISTANCE, 0, true, false, true)
                        .addInfiniteEffect(MobEffects.DAMAGE_BOOST, 9, true, false, true)
                        .addInfiniteEffect(MobEffects.HEALTH_BOOST, 14, true, false, true);
            }
        }
        return builder.build();
    }
}
