package net.trique.wardentools.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.trique.wardentools.registry.EffectRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(VibrationSystem.User.class)
public interface VibrationMixin {
    @ModifyExpressionValue(method = "isValidVibration", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;dampensVibrations()Z"))
    private boolean handleSculkAdaption(boolean original, @Local(argsOnly = true) GameEvent.Context context) {
        return original && !(context.sourceEntity() instanceof LivingEntity livingEntity &&
                livingEntity.hasEffect(EffectRegistry.SCULK_ADAPTION));
    }
}
