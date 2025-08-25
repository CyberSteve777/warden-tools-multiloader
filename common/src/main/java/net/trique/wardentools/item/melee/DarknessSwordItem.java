package net.trique.wardentools.item.melee;

import me.cybersteve.equiplib.item.handheld.base.IEffectHandHeldItem;
import me.cybersteve.equiplib.util.EffectList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class DarknessSwordItem extends SwordItem implements IEffectHandHeldItem {
    public DarknessSwordItem(Tier toolMaterial, Properties settings) {
        super(toolMaterial, settings);
    }

    @Override
    public EffectList getEffectsWhenInHand(LivingEntity entity) {
        return EffectList.getEmptyList();
    }

    @Override
    public EffectList getEffectsForSelfOnAttack(DamageSource source, LivingEntity owner, float amount) {
        return EffectList.getEmptyList();
    }

    @Override
    public EffectList getEffectsForTargetOnAttack(DamageSource source, LivingEntity owner, float amount) {
        return new EffectList.Builder().addEffect(MobEffects.DARKNESS, 100, 0, false,
                false, true).build();
    }
}