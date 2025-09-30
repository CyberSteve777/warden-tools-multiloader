package net.trique.wardentools.util.warden_curse;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.trique.wardentools.Constants;
import net.trique.wardentools.config.WTConfigServer;
import net.trique.wardentools.item.melee.WardenMaskItem;
import net.trique.wardentools.networking.packet.AddBlockOutlinePacket;
import net.trique.wardentools.networking.packet.AddEntityGlowPacket;
import net.trique.wardentools.platform.Services;
import net.trique.wardentools.util.WTGameEventTags;
import software.bernie.geckolib.animatable.GeoItem;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class WardenCurseUser implements VibrationSystem {
    protected LivingEntity holder;
    protected int amplifier;
    protected int extraBonus;
    private final VibrationSystem.Data vibrationData = new VibrationSystem.Data();
    private final VibrationSystem.User vibrationUser;

    public WardenCurseUser(LivingEntity livingEntity, int amplifier, int extraBonus) {
        holder = livingEntity;
        this.amplifier = amplifier;
        this.extraBonus = extraBonus;
        vibrationUser = new VibrationUser(livingEntity);
    }

    public WardenCurseUser(LivingEntity livingEntity, int amplifier) {
        this(livingEntity, amplifier, 0);
    }

    @Override
    public Data getVibrationData() {
        return vibrationData;
    }

    @Override
    public User getVibrationUser() {
        return vibrationUser;
    }

    public int getExtraBonus() {
        return extraBonus;
    }

    public void setExtraBonus(int extraBonus) {
        this.extraBonus = extraBonus;
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
            return 8 * (amplifier + 1) + extraBonus;
        }

        @Override
        public TagKey<GameEvent> getListenableEvents() {
            return WTGameEventTags.VIBRA_SENSE_CAN_LISTEN;
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
//            Constants.LOGGER.info("Can receive vibration called");
//            return true;
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
                    int entity_glow_seconds = (int)(WTConfigServer.CONFIG.seconds_to_glow_entity.get() * 20);
                    int block_outline_seconds = (int)(WTConfigServer.CONFIG.seconds_to_outline_block.get() * 20);
                    if (entity != null) {
                        Services.PACKET_HELPER.sendPacket(player, new AddEntityGlowPacket(entity.getId(),
                                entity_glow_seconds));
                        Services.PACKET_HELPER.sendPacket(player, new AddBlockOutlinePacket(blockPos,
                                block_outline_seconds));
                    }
                    if (possibleShooter != null) {
                        Services.PACKET_HELPER.sendPacket(player, new AddEntityGlowPacket(possibleShooter.getId(),
                                entity_glow_seconds));
                        Services.PACKET_HELPER.sendPacket(player, new AddBlockOutlinePacket(
                                possibleShooter.getOnPos().above(), block_outline_seconds
                        ));
                    }
                    ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);
                    if (head.getItem() instanceof WardenMaskItem mask) {
                        Constants.LOGGER.info("should trigger animation");
                        mask.triggerArmorAnim(player, GeoItem.getOrAssignId(head, player.serverLevel()),
                                "warden_mask", "tendrils_click");
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
