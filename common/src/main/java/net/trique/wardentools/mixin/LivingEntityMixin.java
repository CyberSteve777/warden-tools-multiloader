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
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
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


@Debug(export = true)
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Unique
    private EchoLocateUser wardentools$echolocateUser;

    @Unique
    private boolean wardentools$wasDeepDarkBonusApplied = false;

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
        if (wardentools$echolocateUser != null && !this.level().isClientSide()) {
            Constants.LOGGER.info("amplifier: {}", wardentools$echolocateUser.getAmplifier());
            VibrationSystem.Ticker.tick(this.level(), wardentools$echolocateUser.getVibrationData(),
                    wardentools$echolocateUser.getVibrationUser());
        }
    }
    @Inject(method = "tickEffects", at = @At("TAIL"))
    private void updateEchoLocateStatus(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (this.level() instanceof ServerLevel level) {
            if (self.hasEffect(EffectRegistry.ECHOLOCATE)) {
                MobEffectInstance echoLocateInstance = self.getEffect(EffectRegistry.ECHOLOCATE);
                int amplifier = echoLocateInstance.getAmplifier();
                Holder<Biome> currentBiome = level.getBiome(self.getOnPos());
                if (wardentools$echolocateUser == null || wardentools$echolocateUser.getAmplifier() != amplifier) {
                    wardentools$echolocateUser = new EchoLocateUser(self, amplifier);
                }
                if (currentBiome.is(Biomes.DEEP_DARK) && !wardentools$wasDeepDarkBonusApplied) {
                    wardentools$wasDeepDarkBonusApplied = true;
                    wardentools$echolocateUser.setExtraBonus(16);
                } else if (!currentBiome.is(Biomes.DEEP_DARK) && wardentools$wasDeepDarkBonusApplied) {
                    wardentools$wasDeepDarkBonusApplied = false;
                    wardentools$echolocateUser.setExtraBonus(0);
                }
            } else if (wardentools$echolocateUser != null) {
                wardentools$echolocateUser = null;
            }
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
}