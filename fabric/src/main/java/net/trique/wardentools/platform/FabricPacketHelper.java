package net.trique.wardentools.platform;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.trique.wardentools.platform.services.IPacketHelper;

public class FabricPacketHelper implements IPacketHelper {

    @Override
    public void sendPacket(ServerPlayer serverPlayer, CustomPacketPayload payload) {
        ServerPlayNetworking.send(serverPlayer, payload);
    }
}
