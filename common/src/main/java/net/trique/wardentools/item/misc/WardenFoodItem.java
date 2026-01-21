package net.trique.wardentools.item.misc;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class WardenFoodItem {
    public static FoodProperties getSculkShellProperties() {
        return new FoodProperties.Builder()
                .nutrition(10)
                .saturationMod(3.0F)
                .effect(new MobEffectInstance(MobEffects.DARKNESS, 200, 0), 1.0F)
                .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 2), 1.0F)
                .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 2), 1.0F)
                .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 2), 1.0F)
                .alwaysEat().build();
    }


    public static FoodProperties getEchoAppleProperties() {
        return new FoodProperties.Builder()
                .nutrition(5)
                .saturationMod(1.2F)
                .effect(new MobEffectInstance(MobEffects.DARKNESS, 100, 0), 1.0F)
                .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1800, 0), 1.0F)
                .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1800, 0), 1.0F)
                .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1800, 0), 1.0F)
                .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1800, 0), 1.0F)
                .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1800, 0), 1.0f)
                .alwaysEat().build();
    }
}