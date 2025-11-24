package net.trique.wardentools.platform;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.trique.wardentools.attachments.CommonDataAttachment;
import net.trique.wardentools.networking.packet.C2SModPacket;
import net.trique.wardentools.networking.packet.S2CModPacket;
import net.trique.wardentools.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public boolean isClient() {
        return FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public <T> void registerDataAttachment(CommonDataAttachment<T> attachment) {
        AttachmentType<T> type = createType(attachment);
        attachment.setAttachment(type);
    }

    @SuppressWarnings({"UnstableApiUsage", "unchecked"})
    @Override
    public <T> T getAttachedValue(Object object, CommonDataAttachment<T> attachment) {
        AttachmentType<T> type = (AttachmentType<T>) attachment.getAttachment();
        if (object instanceof AttachmentTarget attachmentTarget) {
            return attachmentTarget.getAttached(type);
        }
        throw new RuntimeException(object +" does not support attachments!");
    }

    @SuppressWarnings({"UnstableApiUsage", "unchecked"})
    @Override
    public <T> void setAttachedValue(Object object, CommonDataAttachment<T> attachment, T value) {
        AttachmentType<T> type = (AttachmentType<T>) attachment.getAttachment();
        if (object instanceof AttachmentTarget attachmentTarget) {
            attachmentTarget.setAttached(type, value);
        } else {
            throw new RuntimeException(object + " does not support attachments!");
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    <T> AttachmentType<T> createType(CommonDataAttachment<T> attachment) {
        AttachmentRegistry.Builder<T> builder = AttachmentRegistry.builder();
        if (attachment.isCopyOnDeath()) {
            builder.copyOnDeath();
        }
        builder.initializer(() -> attachment.getDefaultValueSupplier().apply(null));
        if (attachment.getCodec() != null) {
            builder.persistent(attachment.getCodec());
        }
        if (attachment.canSync()) {
            builder.syncWith(attachment.getStreamCodec(),(attachmentTarget, player) -> true);//who to notify of value
        }
        return builder.buildAndRegister(attachment.getName());
    }

    @Override
    public <MSG extends S2CModPacket<?>> void registerClientPlayPacket(CustomPacketPayload.Type<MSG> type, StreamCodec<RegistryFriendlyByteBuf, MSG> streamCodec) {
        PayloadTypeRegistry.playS2C().register(type,streamCodec);//payload needs to be registered on server/client, packethandler is client only
        if (isClient()) {
            ClientPlayNetworking.registerGlobalReceiver(type,(payload, context) -> payload.handleClient());
        }
    }

    @Override
    public <MSG extends C2SModPacket<?>> void registerServerPlayPacket(CustomPacketPayload.Type<MSG> type, StreamCodec<RegistryFriendlyByteBuf, MSG> streamCodec) {
        PayloadTypeRegistry.playC2S().register(type, streamCodec);
        ServerPlayNetworking.registerGlobalReceiver(type,(payload, context) ->  payload.handleServer(context.player()));
    }

    @Override
    public void sendToClient(S2CModPacket<?> msg, ServerPlayer player) {
        ServerPlayNetworking.send(player,msg);
    }

    @Override
    public void sendToServer(C2SModPacket<?> msg) {
        ClientPlayNetworking.send(msg);
    }

}
