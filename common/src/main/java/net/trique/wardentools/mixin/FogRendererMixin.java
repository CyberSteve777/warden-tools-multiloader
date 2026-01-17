package net.trique.wardentools.mixin;


import net.minecraft.client.renderer.FogRenderer;
import net.trique.wardentools.client.renderer.WardenCurseFogFunction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @Final
    @Shadow
    private static List<FogRenderer.MobEffectFogFunction> MOB_EFFECT_FOG;

    static {
        MOB_EFFECT_FOG.add(new WardenCurseFogFunction());
    }
}
