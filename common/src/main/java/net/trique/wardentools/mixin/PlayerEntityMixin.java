package net.trique.wardentools.mixin;


import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.trique.wardentools.registry.EffectRegistry;
import net.trique.wardentools.util.warden_curse.WardenCurseUser;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Unique
    private WardenCurseUser wardentools$echolocateUser;

    @Unique
    private boolean wardentools$wasDeepDarkBonusApplied = false;

    @Unique
    private DynamicGameEventListener<VibrationSystem.Listener> wardentools$dynamicGameEventListener;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void updateEchoLocateStatus(CallbackInfo ci) {
        Player self = (Player) (Object) this;
        if (this.level() instanceof ServerLevel level) {
            if (self.hasEffect(EffectRegistry.WARDEN_CURSE)) {
                MobEffectInstance echoLocateInstance = self.getEffect(EffectRegistry.WARDEN_CURSE);
                int amplifier = echoLocateInstance.getAmplifier();
                Holder<Biome> currentBiome = level.getBiome(self.getOnPos());
                if (wardentools$echolocateUser == null || wardentools$echolocateUser.getAmplifier() != amplifier) {
                    wardentools$echolocateUser = new WardenCurseUser(self, amplifier);
                }
                if (currentBiome.is(Biomes.DEEP_DARK) && !wardentools$wasDeepDarkBonusApplied) {
                    wardentools$wasDeepDarkBonusApplied = true;
                    wardentools$echolocateUser.setExtraBonus(16);
                } else if (!currentBiome.is(Biomes.DEEP_DARK) && wardentools$wasDeepDarkBonusApplied) {
                    wardentools$wasDeepDarkBonusApplied = false;
                    wardentools$echolocateUser.setExtraBonus(0);
                }
                wardentools$dynamicGameEventListener = new DynamicGameEventListener<>
                        (new VibrationSystem.Listener(wardentools$echolocateUser));
            } else if (wardentools$echolocateUser != null) {
                wardentools$dynamicGameEventListener = null;
                wardentools$echolocateUser = null;
            }
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tickEchoLocateIfPossible(CallbackInfo ci) {
        if (wardentools$echolocateUser != null && !this.level().isClientSide()) {
//            Constants.LOGGER.info("amplifier: {}", wardentools$echolocateUser.getAmplifier());
            VibrationSystem.Ticker.tick(this.level(), wardentools$echolocateUser.getVibrationData(),
                    wardentools$echolocateUser.getVibrationUser());
        }
    }

    @Override
    public void updateDynamicGameEventListener(BiConsumer<DynamicGameEventListener<?>, ServerLevel> listenerConsumer) {
        if (wardentools$dynamicGameEventListener != null && this.level() instanceof ServerLevel level) {
            listenerConsumer.accept(wardentools$dynamicGameEventListener, level);
        }
    }

    @Override
    public boolean dampensVibrations() {
        return hasEffect(EffectRegistry.SCULK_ADAPTION);
    }
}
