package net.trique.wardentools.client.renderer;

import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.trique.wardentools.registry.EffectRegistry;

public class WardenCurseFogFunction implements FogRenderer.MobEffectFogFunction {
    @Override
    public MobEffect getMobEffect() {
        return EffectRegistry.WARDEN_CURSE;
    }

    @Override
    public void setupFog(FogRenderer.FogData fogData, LivingEntity livingEntity, MobEffectInstance effectInstance, float farPlaneDistance, float f) {
        float g = effectInstance.isInfiniteDuration() ? 5.0F : Mth.lerp(Math.min(1.0F, (float)effectInstance.getDuration() / 20.0F), farPlaneDistance, 5.0F);
        if (fogData.mode == FogRenderer.FogMode.FOG_SKY) {
            fogData.start = 0.0F;
            fogData.end = g * 0.8F;
        } else {
            fogData.start = g * 0.25F;
            fogData.end = g;
        }
    }
}
