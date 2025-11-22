package net.trique.wardentools.networking.packet;


import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.trique.wardentools.util.KeyAction;
import net.trique.wardentools.util.ModHelper;
import net.trique.wardentools.util.WardenEchoStaffHelper;


public record C2SKeybindPacket(KeyAction action) implements C2SModPacket<RegistryFriendlyByteBuf> {

    public static final ResourceLocation ID = ModHelper.getLoc("keybind");

    public static final Type<C2SKeybindPacket> PACKET_ID = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SKeybindPacket> CODEC =
            StreamCodec.composite(KeyAction.STREAM_CODEC, C2SKeybindPacket::action,C2SKeybindPacket::new);

    @Override
    public void handleServer(ServerPlayer player) {
        switch (action) {
            case CONSUME_CHARGES -> {
                WardenEchoStaffHelper.addPlayer(player.getId());
            }
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }

    public static void sendToServer(KeyAction action) {
        PacketHandler.sendToServer(new C2SKeybindPacket(action));
    }
}