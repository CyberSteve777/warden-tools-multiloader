package net.trique.wardentools.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.Minecraft;
import net.trique.wardentools.registry.EffectRegistry;
import net.trique.wardentools.util.ClientFunctions;
import net.trique.wardentools.util.warden_curse.WardenCurseClientHelper;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @WrapMethod(method = "shouldEntityAppearGlowing")
    private boolean renderLocatedEntity(Entity entity, Operation<Boolean> original) {
        LocalPlayer player = ClientFunctions.getLocalPlayer();
        return original.call(entity) || (player.hasEffect(EffectRegistry.WARDEN_CURSE) && !entity.is(player) &&
                WardenCurseClientHelper.getEntitiesToRenderGlowing().contains(entity.getId()));
    }
}