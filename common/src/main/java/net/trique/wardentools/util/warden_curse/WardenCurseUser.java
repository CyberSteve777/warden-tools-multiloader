package net.trique.wardentools.util.warden_curse;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.trique.wardentools.config.WTConfigServer;
import net.trique.wardentools.item.melee.WardenMaskItem;
import net.trique.wardentools.networking.packet.AddBlockOutlinePacket;
import net.trique.wardentools.networking.packet.AddEntityGlowPacket;
import net.trique.wardentools.platform.Services;
import net.trique.wardentools.registry.EffectRegistry;
import net.trique.wardentools.util.WTGameEventTags;
import software.bernie.geckolib.animatable.GeoItem;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class WardenCurseUser implements VibrationSystem {
    protected LivingEntity holder;
    protected int extraBonus = 0;
    private VibrationSystem.Data vibrationData = new VibrationSystem.Data();
    private final VibrationSystem.User vibrationUser;
    private final net.trique.wardentools.util.warden_curse.Ticker COOLDOWN_TICKER =
            new net.trique.wardentools.util.warden_curse.Ticker();

    public WardenCurseUser(LivingEntity livingEntity) {
        holder = livingEntity;
        vibrationUser = new VibrationUser();
    }

    public WardenCurseUser(LivingEntity livingEntity, int extraBonus) {
        this(livingEntity);
        this.extraBonus = extraBonus;
    }

    @Override
    public Data getVibrationData() {
        return vibrationData;
    }

    public void setVibrationData(Data vibrationData) {
        this.vibrationData = vibrationData;
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

    public boolean hasCooldown() {
        return COOLDOWN_TICKER.hasRemainingDuration();
    }

    public void tickCooldown() {
        if (hasCooldown()) {
            COOLDOWN_TICKER.tick();
        }
    }

    class VibrationUser implements VibrationSystem.User {
        private final PositionSource positionSource;

        public VibrationUser() {
            positionSource = new EntityPositionSource(holder, holder.getEyeHeight());
        }

        @Override
        public int getListenerRadius() {
            if (holder.hasEffect(EffectRegistry.WARDEN_CURSE)) {
                int amplifier = holder.getEffect(EffectRegistry.WARDEN_CURSE).getAmplifier();
                return 8 * (amplifier + 1) + extraBonus;
            }
            return 0;
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
        public boolean isValidVibration(Holder<GameEvent> gameEvent, GameEvent.Context context) {
            if (!gameEvent.is(this.getListenableEvents())) {
                return false;
            } else {
                Entity entity = context.sourceEntity();
                if (entity != null) {
                    if (entity.isSpectator()) {
                        return false;
                    }
                    if (entity.isSteppingCarefully() && gameEvent.is(GameEventTags.IGNORE_VIBRATIONS_SNEAKING)) {
                        if (this.canTriggerAvoidVibration() && entity instanceof ServerPlayer serverplayer &&
                                !serverplayer.is(holder)) {
                            CriteriaTriggers.AVOID_VIBRATION.trigger(serverplayer);
                        }
                        return false;
                    }
                    if (entity.dampensVibrations()) {
                        return false;
                    }
                }
                return context.affectedState() == null || !context.affectedState().is(BlockTags.DAMPENS_VIBRATIONS);
            }
        }

        @Override
        public boolean canReceiveVibration(ServerLevel serverLevel, BlockPos blockPos, Holder<GameEvent> gameEventHolder, GameEvent.Context context) {
            if (!holder.isDeadOrDying() && holder.hasEffect(EffectRegistry.WARDEN_CURSE)
                    && serverLevel.getWorldBorder().isWithinBounds(blockPos) && !hasCooldown()) {
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
                int amplifier = holder.getEffect(EffectRegistry.WARDEN_CURSE).getAmplifier();
                COOLDOWN_TICKER.setDuration(Math.max(10, 40 - amplifier * 10));
                if ((entity != null && !holder.is(entity)) || (possibleShooter != null && !holder.is(possibleShooter))) {
                    ItemStack head = holder.getItemBySlot(EquipmentSlot.HEAD);
                    if (head.getItem() instanceof WardenMaskItem mask) {
                        mask.triggerArmorAnim(holder, GeoItem.getOrAssignId(head, serverLevel),
                                "warden_mask", "tendrils_click");
                    }
                    serverLevel.playSound(null, holder.getX(), holder.getY(), holder.getZ(),
                            SoundEvents.WARDEN_TENDRIL_CLICKS, holder.getSoundSource(), 1.0F, holder.getVoicePitch());
                }
                if (holder instanceof ServerPlayer player) {
                    int entity_glow_seconds = (int) (WTConfigServer.CONFIG.seconds_to_glow_entity.get() * 20);
                    int block_outline_seconds = (int) (WTConfigServer.CONFIG.seconds_to_outline_block.get() * 20);
                    if (entity != null && !entity.equals(holder)) {
                        Services.PACKET_HELPER.sendPacket(player, new AddEntityGlowPacket(entity.getId(),
                                entity_glow_seconds));
                        Services.PACKET_HELPER.sendPacket(player, new AddBlockOutlinePacket(blockPos,
                                block_outline_seconds));
                    }
                    if (possibleShooter != null && !possibleShooter.equals(holder)) {
                        Services.PACKET_HELPER.sendPacket(player, new AddEntityGlowPacket(possibleShooter.getId(),
                                entity_glow_seconds));
                        Services.PACKET_HELPER.sendPacket(player, new AddBlockOutlinePacket(
                                possibleShooter.getOnPos().above(), block_outline_seconds
                        ));
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
