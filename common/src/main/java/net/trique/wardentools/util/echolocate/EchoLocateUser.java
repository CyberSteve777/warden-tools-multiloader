package net.trique.wardentools.util.echolocate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.trique.wardentools.networking.packet.AddEntityGlowPacket;
import net.trique.wardentools.platform.Services;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class EchoLocateUser implements VibrationSystem {
    protected LivingEntity holder;
    protected int amplifier;
    private final VibrationSystem.Data vibrationData = new VibrationSystem.Data();
    private final VibrationSystem.User vibrationUser;

    public EchoLocateUser(LivingEntity livingEntity, int amplifier) {
        holder = livingEntity;
        this.amplifier = amplifier;
        vibrationUser = new VibrationUser(livingEntity);
    }

    @Override
    public Data getVibrationData() {
        return vibrationData;
    }

    @Override
    public User getVibrationUser() {
        return vibrationUser;
    }

    public LivingEntity getHolder() {
        return holder;
    }

    public int getAmplifier() {
        return amplifier;
    }

    class VibrationUser implements VibrationSystem.User {
        private final PositionSource positionSource;

        public VibrationUser(LivingEntity parent) {
            positionSource = new EntityPositionSource(parent, parent.getEyeHeight());
        }

        @Override
        public int getListenerRadius() {
            return 8 * amplifier;
        }

        @Override
        public TagKey<GameEvent> getListenableEvents() {
            return GameEventTags.WARDEN_CAN_LISTEN;
        }

        @Override
        public PositionSource getPositionSource() {
            return positionSource;
        }

        @Override
        public boolean canTriggerAvoidVibration() {
            return true;
        }

        @Override
        public boolean canReceiveVibration(ServerLevel serverLevel, BlockPos blockPos, Holder<GameEvent> gameEventHolder, GameEvent.Context context) {
            if (!holder.isDeadOrDying() && serverLevel.getWorldBorder().isWithinBounds(blockPos)) {
                Entity source = context.sourceEntity();
                if (source instanceof LivingEntity livingEntity) {
                    return this.canTargetEntity(livingEntity);
                }
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void onReceiveVibration(ServerLevel serverLevel, BlockPos blockPos, Holder<GameEvent> gameEventHolder, @Nullable Entity entity, @Nullable Entity possibleShooter, float distance) {

            if (!holder.isDeadOrDying()) {
                holder.playSound(SoundEvents.WARDEN_TENDRIL_CLICKS, 5.0F, holder.getVoicePitch());
                if (holder instanceof ServerPlayer player) {
                    if (entity instanceof LivingEntity livingEntity) {
                        Services.PACKET_HELPER.sendPacket(player, new AddEntityGlowPacket(livingEntity.getId()));
                    }
                    if (possibleShooter instanceof LivingEntity livingEntity) {
                        Services.PACKET_HELPER.sendPacket(player, new AddEntityGlowPacket(livingEntity.getId()));
                    }
                }
            }
        }

        @Contract("null->false")
        protected boolean canTargetEntity(@Nullable Entity entity) {
            if (entity instanceof LivingEntity livingentity) {
                return holder.level() == entity.level() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity) &&
                        !holder.isAlliedTo(entity) && livingentity.getType() != EntityType.ARMOR_STAND
                        && !livingentity.isInvulnerable() && !livingentity.isDeadOrDying() &&
                        holder.level().getWorldBorder().isWithinBounds(livingentity.getBoundingBox());
            }
            return false;
        }
    }
}
