package net.trique.wardentools.platform;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.trique.wardentools.platform.services.IPacketHelper;

public class NeoForgePacketHelper implements IPacketHelper {
    @Override
    public void sendPacket(ServerPlayer serverPlayer, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayer(serverPlayer, payload);
    }
}
