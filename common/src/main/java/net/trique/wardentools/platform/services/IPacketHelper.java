package net.trique.wardentools.platform.services;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public interface IPacketHelper {
    void sendPacket(ServerPlayer serverPlayer, CustomPacketPayload payload);
}
