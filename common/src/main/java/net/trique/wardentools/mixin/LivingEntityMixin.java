package net.trique.wardentools.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
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
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.trique.wardentools.Constants;
import net.trique.wardentools.registry.EffectRegistry;
import net.trique.wardentools.util.WTBiomeTags;
import net.trique.wardentools.util.warden_curse.WardenCurseUser;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;


@Debug(export = true)
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Unique
    private WardenCurseUser wardentools$wardenCurseUser;

    @Unique
    private DynamicGameEventListener<VibrationSystem.Listener> wardentools$dynamicGameEventListener;


    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract boolean hasEffect(Holder<MobEffect> effect);

    @Shadow
    public abstract boolean removeEffect(Holder<MobEffect> effect);

    @WrapMethod(method = "canBeAffected")
    private boolean preventDarknessIfSculkAdapted(MobEffectInstance effectInstance, Operation<Boolean> original) {
        if (effectInstance.is(MobEffects.DARKNESS) && this.hasEffect(EffectRegistry.SCULK_ADAPTION)) {
            return false;
        }
        return original.call(effectInstance);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void clearDarknessIfSculkAdapted(CallbackInfo ci) {
        if (!level().isClientSide()) {
            if (hasEffect(EffectRegistry.SCULK_ADAPTION)) {
                if (hasEffect(MobEffects.DARKNESS)) {
                    removeEffect(MobEffects.DARKNESS);
                }
            }
        }
    }

    @WrapMethod(method = "hurt")
    private boolean reduceSonicBoomDamage(DamageSource source, float amount, Operation<Boolean> original) {
        if (hasEffect(EffectRegistry.SCULK_ADAPTION) && source.is(DamageTypes.SONIC_BOOM)) {
            return original.call(source, amount * 0.4f);
        }
        return original.call(source, amount);
    }
    @Override
    public boolean dampensVibrations() {
        return hasEffect(EffectRegistry.SCULK_ADAPTION);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void addVibrationDataFromWardenCurse(CompoundTag compound, CallbackInfo ci) {
        if (hasEffect(EffectRegistry.WARDEN_CURSE) && wardentools$wardenCurseUser != null) {
            VibrationSystem.Data.CODEC.encodeStart(NbtOps.INSTANCE, wardentools$wardenCurseUser.getVibrationData())
                    .resultOrPartial(Constants.LOGGER::error)
                    .ifPresent(tag -> compound.put("WTVibrationData", tag));
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readVibrationDataForWardenCurse(CompoundTag compound, CallbackInfo ci) {
        if (compound.contains("WTVibrationData", 10)) {
            VibrationSystem.Data.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, compound.getCompound("WTVibrationData")))
                    .resultOrPartial(Constants.LOGGER::error).ifPresent(data -> {
                        if (hasEffect(EffectRegistry.WARDEN_CURSE) && wardentools$wardenCurseUser != null) {
                            wardentools$wardenCurseUser.setVibrationData(data);
                        }
                    });
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void updateWardenCurseStatus(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (this.level() instanceof ServerLevel level) {
            if (self.hasEffect(EffectRegistry.WARDEN_CURSE)) {
                MobEffectInstance echoLocateInstance = self.getEffect(EffectRegistry.WARDEN_CURSE);
                int amplifier = echoLocateInstance.getAmplifier();
                Holder<Biome> currentBiome = level.getBiome(self.getOnPos());
                if (wardentools$wardenCurseUser == null || wardentools$wardenCurseUser.getAmplifier() != amplifier) {
                    wardentools$wardenCurseUser = new WardenCurseUser(self, amplifier);
                }
                if (currentBiome.is(WTBiomeTags.WARDEN_CURSE_RECEIVE_BONUS_IN) && wardentools$wardenCurseUser.getExtraBonus() == 0) {
                    wardentools$wardenCurseUser.setExtraBonus(16);
                } else if (!currentBiome.is(WTBiomeTags.WARDEN_CURSE_RECEIVE_BONUS_IN) && wardentools$wardenCurseUser.getExtraBonus() != 0) {
                    wardentools$wardenCurseUser.setExtraBonus(0);
                }
                wardentools$dynamicGameEventListener = new DynamicGameEventListener<>
                        (new VibrationSystem.Listener(wardentools$wardenCurseUser));
            } else if (wardentools$wardenCurseUser != null) {
                wardentools$dynamicGameEventListener = null;
                wardentools$wardenCurseUser = null;
            }
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tickWardenCurseIfPossible(CallbackInfo ci) {
        if (wardentools$wardenCurseUser != null && !this.level().isClientSide()) {
//            Constants.LOGGER.info("amplifier: {}", wardentools$echolocateUser.getAmplifier());
            VibrationSystem.Ticker.tick(this.level(), wardentools$wardenCurseUser.getVibrationData(),
                    wardentools$wardenCurseUser.getVibrationUser());
        }
    }

    @Override
    public void updateDynamicGameEventListener(BiConsumer<DynamicGameEventListener<?>, ServerLevel> listenerConsumer) {
        if (wardentools$dynamicGameEventListener != null && this.level() instanceof ServerLevel level) {
            listenerConsumer.accept(wardentools$dynamicGameEventListener, level);
        }
    }

}