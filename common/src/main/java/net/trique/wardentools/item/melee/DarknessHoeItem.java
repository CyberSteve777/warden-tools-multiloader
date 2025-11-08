package net.trique.wardentools.item.melee;

import me.cybersteve.equiplib.item.handheld.base.IEffectHandHeldItem;
import me.cybersteve.equiplib.util.EffectList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Tier;

public class DarknessHoeItem extends HoeItem implements IEffectHandHeldItem {
    public DarknessHoeItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public EffectList getEffectsForTargetOnAttack(DamageSource source, LivingEntity owner, float amount) {
        return new EffectList.Builder().addEffect(MobEffects.DARKNESS, 100, 0, false,
                false, true).build();
    }
}
