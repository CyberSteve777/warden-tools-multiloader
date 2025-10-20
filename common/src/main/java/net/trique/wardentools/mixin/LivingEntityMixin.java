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
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.trique.wardentools.Constants;
import net.trique.wardentools.registry.EffectRegistry;
import net.trique.wardentools.util.WTBiomeTags;
import net.trique.wardentools.util.WTEntityTypeTags;
import net.trique.wardentools.util.warden_curse.WardenCurseUser;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;


@Debug(export = true)
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Unique
    private WardenCurseUser wardentools$wardenCurseUser = new WardenCurseUser((LivingEntity) (Object) this);

    @Unique
    private DynamicGameEventListener<VibrationSystem.Listener> wardentools$dynamicGameEventListener =
            new DynamicGameEventListener<>(new VibrationSystem.Listener(wardentools$wardenCurseUser));

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract boolean hasEffect(Holder<MobEffect> effect);

    @Shadow
    public abstract boolean removeEffect(Holder<MobEffect> effect);

    @Shadow
    public abstract MobEffectInstance getEffect(Holder<MobEffect> effect);

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
    private boolean reduceDamageWithSculkBless(DamageSource source, float amount, Operation<Boolean> original) {
        if (hasEffect(EffectRegistry.SCULK_BLESS) && (source.is(DamageTypes.SONIC_BOOM) ||
                (source.getEntity() instanceof LivingEntity livingEntity &&
                        (livingEntity.getType().is(WTEntityTypeTags.SCULK_BLESS_REDUCES_DAMAGE_FROM) ||
                                livingEntity.hasEffect(EffectRegistry.SCULK_ADAPTION))))) {
            int amplifier = getEffect(EffectRegistry.SCULK_BLESS).getAmplifier();
            return original.call(source, amount * 0.1f * (amplifier + 1));
        }
        return original.call(source, amount);
    }

    @Override
    public boolean dampensVibrations() {
        return hasEffect(EffectRegistry.SCULK_ADAPTION);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void addVibrationDataFromWardenCurse(CompoundTag compound, CallbackInfo ci) {
        VibrationSystem.Data.CODEC.encodeStart(NbtOps.INSTANCE, wardentools$wardenCurseUser.getVibrationData())
                .resultOrPartial(Constants.LOGGER::error)
                .ifPresent(tag -> compound.put("WTWardenCurseVibrationData", tag));
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readVibrationDataForWardenCurse(CompoundTag compound, CallbackInfo ci) {
        if (compound.contains("WTWardenCurseVibrationData", 10)) {
            VibrationSystem.Data.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, compound.getCompound("WTVibrationData")))
                    .resultOrPartial(Constants.LOGGER::error).ifPresent(data -> wardentools$wardenCurseUser.setVibrationData(data));
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void updateWardenCurseBonus(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (this.level() instanceof ServerLevel level) {
            if (self.hasEffect(EffectRegistry.WARDEN_CURSE)) {
                Holder<Biome> currentBiome = level.getBiome(self.getOnPos());
                if (currentBiome.is(WTBiomeTags.WARDEN_CURSE_RECEIVE_BONUS_IN) && wardentools$wardenCurseUser.getExtraBonus() == 0) {
                    wardentools$wardenCurseUser.setExtraBonus(16);
                } else if (!currentBiome.is(WTBiomeTags.WARDEN_CURSE_RECEIVE_BONUS_IN) && wardentools$wardenCurseUser.getExtraBonus() != 0) {
                    wardentools$wardenCurseUser.setExtraBonus(0);
                }
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickWardenCurse(CallbackInfo ci) {
        if (level() instanceof ServerLevel serverLevel) {
//            Constants.LOGGER.info("amplifier: {}", wardentools$echolocateUser.getAmplifier());
            VibrationSystem.Ticker.tick(serverLevel, wardentools$wardenCurseUser.getVibrationData(),
                    wardentools$wardenCurseUser.getVibrationUser());
            wardentools$wardenCurseUser.tickCooldown();
        }
    }

    @Override
    public void updateDynamicGameEventListener(BiConsumer<DynamicGameEventListener<?>, ServerLevel> listenerConsumer) {
        if (level() instanceof ServerLevel level) {
            listenerConsumer.accept(wardentools$dynamicGameEventListener, level);
        }
    }

    @WrapMethod(method = "hurt")
    public boolean applyExtraDamageFromSculkBless(DamageSource source, float amount, Operation<Boolean> original) {
        if (source.getEntity() instanceof LivingEntity attacker && attacker.hasEffect(EffectRegistry.SCULK_BLESS) &&
                (getType().is(WTEntityTypeTags.SCULK_BLESS_DEALS_EXTRA_DAMAGE_TO) ||
                        hasEffect(EffectRegistry.SCULK_ADAPTION))) {
            int amplifier = attacker.getEffect(EffectRegistry.SCULK_BLESS).getAmplifier();
            return original.call(source, amount * (1 + 0.5f * (1 + amplifier)));
        }
        return original.call(source, amount);
    }

}