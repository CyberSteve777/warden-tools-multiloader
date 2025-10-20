package net.trique.wardentools.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.Team;
import net.trique.wardentools.registry.EffectRegistry;
import net.trique.wardentools.util.ClientFunctions;
import net.trique.wardentools.util.warden_curse.WardenCurseClientHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @WrapOperation(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getTeamColor()I"))
    private int setOutlineColor(Entity entity, Operation<Integer> original) {
        LocalPlayer player = ClientFunctions.getLocalPlayer();
        if ((player.hasEffect(EffectRegistry.WARDEN_CURSE) && !entity.is(player) &&
                WardenCurseClientHelper.getEntitiesToRenderGlowing().contains(entity.getId()))) {
            return FastColor.ARGB32.color(255, 41, 223, 235);
        }
        return original.call(entity);
    }
}
