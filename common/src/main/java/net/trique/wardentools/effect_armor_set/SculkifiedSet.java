package net.trique.wardentools.effect_armor_set;

import me.cybersteve.equiplib.armorset.impl.FullEffectArmorSet;
import me.cybersteve.equiplib.util.EffectList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.trique.wardentools.registry.EffectRegistry;
import net.trique.wardentools.util.ModHelper;

public class SculkifiedSet extends FullEffectArmorSet {

    public SculkifiedSet() {
        super(ModHelper.getLoc("sculkified_set"),
                SculkifiedSet::getWearingEffects,
                SculkifiedSet::filler,
                SculkifiedSet::filler);
    }


    private static EffectList getWearingEffects(LivingEntity entity) {
        return new EffectList.Builder()
                .addInfiniteEffect(EffectRegistry.SCULK_ADAPTION, 1,
                        true, false, true)
                .build();
    }

    private static EffectList filler(DamageSource source, LivingEntity wearer, float amount) {
        return EffectList.getEmptyList();
    }
}
