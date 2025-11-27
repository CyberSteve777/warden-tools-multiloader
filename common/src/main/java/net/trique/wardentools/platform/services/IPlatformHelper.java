package net.trique.wardentools.platform.services;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.trique.wardentools.attachments.CommonDataAttachment;
import net.trique.wardentools.networking.packet.C2SModPacket;
import net.trique.wardentools.networking.packet.S2CModPacket;
import org.jetbrains.annotations.Nullable;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */

    boolean isClient();

    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }

    <T> void registerDataAttachment(CommonDataAttachment<T> attachment);

    @Nullable
    <T> T getAttachedValue(Object object, CommonDataAttachment<T> attachment);

    default <T> T getOrCreateAttachedValue(Entity entity, CommonDataAttachment<T> attachment) {
        T value = getAttachedValue(entity, attachment);
        if (value != null) {
            return value;
        }
        setAttachedValue(entity, attachment, attachment.getDefaultValueSupplier().apply(entity));
        return getAttachedValue(entity, attachment);
    }

    <T> void setAttachedValue(Object object, CommonDataAttachment<T> attachment, @Nullable T value);

    <MSG extends S2CModPacket<?>> void registerClientPlayPacket(CustomPacketPayload.Type<MSG> type, StreamCodec<RegistryFriendlyByteBuf, MSG> streamCodec);

    <MSG extends C2SModPacket<?>> void registerServerPlayPacket(CustomPacketPayload.Type<MSG> type, StreamCodec<RegistryFriendlyByteBuf, MSG> streamCodec);

    void sendToClient(S2CModPacket<?> msg, ServerPlayer player);

    void sendToServer(C2SModPacket<?> msg);

    SimpleParticleType getSimpleParticle();

}