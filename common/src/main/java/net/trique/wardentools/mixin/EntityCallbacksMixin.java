package net.trique.wardentools.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.trique.wardentools.util.warden_curse.WardenCurseUser;
import net.trique.wardentools.util.warden_curse.WardenCurseUserAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BiConsumer;

@Mixin(ServerLevel.EntityCallbacks.class)
public class EntityCallbacksMixin {
    @WrapOperation(
            method = {"onTrackingStart*", "onTrackingEnd*", "onSectionChange*"},
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;updateDynamicGameEventListener(Ljava/util/function/BiConsumer;)V"))
    private void wardentools$alsoRegisterCurse(Entity entity, BiConsumer<DynamicGameEventListener<?>, ServerLevel> listenerConsumer, Operation<Void> original) {
        original.call(entity, listenerConsumer);
        if (entity instanceof WardenCurseUserAccessor holder
                && entity.level() instanceof ServerLevel level) {
            WardenCurseUser user = holder.getUser();
            if (user != null) {
                listenerConsumer.accept(user.getListener(), level);
            }
        }
    }
}
