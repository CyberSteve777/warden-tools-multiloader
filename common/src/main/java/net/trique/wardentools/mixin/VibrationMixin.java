package net.trique.wardentools.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;
import net.trique.wardentools.registry.EffectRegistry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(VibrationSystem.Listener.class)
public class VibrationMixin {
    @WrapMethod(method = "handleGameEvent")
    private boolean wardentools$handleSculkAdaption(ServerLevel level, Holder<GameEvent> gameEvent,
                                                    GameEvent.Context context, Vec3 pos,
                                                    Operation<Boolean> original) {
        if (context.sourceEntity() instanceof LivingEntity living
                && living.hasEffect(EffectRegistry.SCULK_ADAPTION)) {
            return false;
        }
        return original.call(level, gameEvent, context, pos);
    }
}