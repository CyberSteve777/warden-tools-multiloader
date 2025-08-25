package net.trique.wardentools.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.trique.wardentools.Constants;
import net.trique.wardentools.registry.EffectRegistry;
import net.trique.wardentools.util.echolocate.EchoLocateUser;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Debug(export = true)
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Unique
    private VibrationSystem wardentools$echolocateUser;

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract boolean hasEffect(Holder<MobEffect> effect);

    @WrapMethod(method = "canBeAffected")
    private boolean preventDarknessIfSculkAdapted(MobEffectInstance effectInstance, Operation<Boolean> original) {
        if (effectInstance.is(MobEffects.DARKNESS) && this.hasEffect(EffectRegistry.SCULK_ADAPTION)) {
            return false;
        }
        return original.call(effectInstance);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void clearDarknessIfSculkAdapted(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;

        if (!self.level().isClientSide()) {
            if (self.hasEffect(EffectRegistry.SCULK_ADAPTION)) {
                if (self.hasEffect(MobEffects.DARKNESS)) {
                    self.removeEffect(MobEffects.DARKNESS);
                }
            }
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tickEchoLocateIfPossible(CallbackInfo ci) {
        if (wardentools$echolocateUser instanceof EchoLocateUser user && !this.level().isClientSide()) {
            Constants.LOGGER.info("amplifier: {}", user.getAmplifier());
            VibrationSystem.Ticker.tick(this.level(), user.getVibrationData(),
                    user.getVibrationUser());
        }
    }

    @Inject(method = "onEffectAdded", at = @At("TAIL"))
    private void setEchoLocateUser(MobEffectInstance effectInstance, Entity entity, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (effectInstance.is(EffectRegistry.ECHOLOCATE)) {
            wardentools$echolocateUser = new EchoLocateUser(self, effectInstance.getAmplifier());
        }
    }

    @Inject(method = "onEffectRemoved", at = @At("TAIL"))
    private void removeEchoLocateUser(MobEffectInstance effectInstance, CallbackInfo ci) {
        if (effectInstance.is(EffectRegistry.ECHOLOCATE)) {
            wardentools$echolocateUser = null;
        }
    }

    @Inject(method = "onEffectUpdated", at = @At("TAIL"))
    private void updateEchoLocateUser(MobEffectInstance effectInstance, boolean forced, Entity entity, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!self.level().isClientSide() && effectInstance.is(EffectRegistry.ECHOLOCATE)) {
            wardentools$echolocateUser = new EchoLocateUser(self, effectInstance.getAmplifier());
        }
    }

    @WrapMethod(method = "hurt")
    private boolean reduceSonicBoomDamage(DamageSource source, float amount, Operation<Boolean> original) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self.hasEffect(EffectRegistry.SCULK_ADAPTION) && source.is(DamageTypes.SONIC_BOOM)) {
            return original.call(source, amount * 0.4f);
        }
        return original.call(source, amount);
    }

    @Override
    public boolean dampensVibrations() {
        LivingEntity self = (LivingEntity) (Object) this;
        return self.hasEffect(EffectRegistry.SCULK_ADAPTION);
    }
}