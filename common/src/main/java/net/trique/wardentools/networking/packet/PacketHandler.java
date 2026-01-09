package net.trique.wardentools.networking.packet;

import net.minecraft.server.level.ServerPlayer;
import net.trique.wardentools.platform.Services;

public class PacketHandler {

    public static void registerPackets() {
        Services.PLATFORM.registerClientPlayPacket(S2CAddEntityGlowPacket.TYPE, S2CAddEntityGlowPacket.CODEC);
        Services.PLATFORM.registerClientPlayPacket(S2CAddBlockOutlinePacket.TYPE, S2CAddBlockOutlinePacket.CODEC);
        Services.PLATFORM.registerServerPlayPacket(C2SKeybindPacket.TYPE, C2SKeybindPacket.CODEC);
    }

    public static void sendToServer(C2SModPacket<?> packet) {
        Services.PLATFORM.sendToServer(packet);
    }

    public static void sendToClient(S2CModPacket<?> packet, ServerPlayer player) {
            Services.PLATFORM.sendToClient(packet, player);
    }
}
