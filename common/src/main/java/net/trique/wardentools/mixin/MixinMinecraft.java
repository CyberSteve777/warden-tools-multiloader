package net.trique.wardentools.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.trique.wardentools.Constants;
import net.minecraft.client.Minecraft;
import net.trique.wardentools.registry.EffectRegistry;
import net.trique.wardentools.util.ClientFunctions;
import net.trique.wardentools.util.vibra_sense.VibraSenseClientHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    
    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(CallbackInfo info) {
        
        Constants.LOGGER.info("This line is printed by an example mod common mixin!");
        Constants.LOGGER.info("MC Version: {}", Minecraft.getInstance().getVersionType());
    }

    @WrapMethod(method = "shouldEntityAppearGlowing")
    private boolean renderEchoLocatedEntity(Entity entity, Operation<Boolean> original) {
        LocalPlayer player = ClientFunctions.getLocalPlayer();
        return original.call(entity) || (player.hasEffect(EffectRegistry.ECHOLOCATE) && !entity.is(player) &&
                VibraSenseClientHelper.getEntitiesToRenderGlowing().contains(entity.getId()));
    }
}