package net.trique.wardentools.mixin;


import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.entity.Entity;
import net.trique.wardentools.client.renderer.WardenCurseFogFunction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @Final
    @Shadow
    private static List<FogRenderer.MobEffectFogFunction> MOB_EFFECT_FOG;

    @Inject(method = "getPriorityFogFunction", at = @At(value = "HEAD"))
    private static void addCustomFogFunction(Entity entity, float partialTick, CallbackInfoReturnable<FogRenderer.MobEffectFogFunction> cir) {
        MOB_EFFECT_FOG.add(new WardenCurseFogFunction());
    }
}
