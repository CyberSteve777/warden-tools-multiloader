package net.trique.wardentools.networking.packet;


import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.trique.wardentools.attachments.CommonDataAttachments;
import net.trique.wardentools.platform.Services;
import net.trique.wardentools.util.KeyAction;
import net.trique.wardentools.util.ModHelper;

import java.util.EnumSet;


public record C2SKeybindPacket(KeyAction action, boolean isHeld) implements C2SModPacket<RegistryFriendlyByteBuf> {

    public static final ResourceLocation ID = ModHelper.getLoc("keybind");

    public static final Type<C2SKeybindPacket> PACKET_ID = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SKeybindPacket> CODEC =
            StreamCodec.composite(KeyAction.STREAM_CODEC, C2SKeybindPacket::action,
                    ByteBufCodecs.BOOL, C2SKeybindPacket::isHeld,
                    C2SKeybindPacket::new);

    @Override
    public void handleServer(ServerPlayer player) {
        EnumSet<KeyAction> currentlyHeld = Services.PLATFORM.getOrCreateAttachedValue(player, CommonDataAttachments.ACTIVE_KEYBINDS);
        if (isHeld) {
            currentlyHeld.add(action);
        } else {
            currentlyHeld.remove(action);
        }
        Services.PLATFORM.setAttachedValue(player, CommonDataAttachments.ACTIVE_KEYBINDS, currentlyHeld);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }

    public static void sendToServer(KeyAction action, boolean down) {
        PacketHandler.sendToServer(new C2SKeybindPacket(action, down));
    }
}