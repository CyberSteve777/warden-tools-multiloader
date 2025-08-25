package net.trique.wardentools.effect_armor_set;

import me.cybersteve.equiplib.armorset.impl.FullEffectArmorSet;
import me.cybersteve.equiplib.util.EffectList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.trique.wardentools.registry.EffectRegistry;

import static net.trique.wardentools.util.ModHelper.getLoc;

public class WardenSet extends FullEffectArmorSet {
    public WardenSet() {
        super(getLoc("warden_set"), WardenSet::getWearingEffects, WardenSet::filler, WardenSet::filler);
    }

    private static EffectList getWearingEffects(LivingEntity entity) {
        EffectList.Builder builder = new EffectList.Builder();
        builder = builder.addInfiniteEffect(EffectRegistry.SCULK_ADAPTION, 0, true,
                false, true)
                .addInfiniteEffect(MobEffects.FIRE_RESISTANCE, 0, true, false, true);

        return builder.build();
    }

    private static EffectList filler(DamageSource source, LivingEntity target, float amount) {
        return EffectList.getEmptyList();
    }
}
