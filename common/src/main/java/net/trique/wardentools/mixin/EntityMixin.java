package net.trique.wardentools.mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.trique.wardentools.registry.GameEventRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract Level level();

    @Inject(method = "playSound(Lnet/minecraft/sounds/SoundEvent;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"), cancellable = true)
    private void makeGameEvent(SoundEvent sound, float volume, float pitch, CallbackInfo ci) {
        Entity self = (Entity) (Object) this;
        level().gameEvent(self, GameEventRegistry.ENTITY_SOUND_EVENT.asHolder(), self.position());
    }
}
