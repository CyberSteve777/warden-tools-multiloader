package net.trique.wardentools.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.trique.wardentools.Constants;


public record AddEntityGlowPacket(int id) implements CustomPacketPayload {
    public static final Type<AddEntityGlowPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID,
                    "add_entity_glow"
            )
    );

    public static final StreamCodec<FriendlyByteBuf, AddEntityGlowPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,AddEntityGlowPacket::id, AddEntityGlowPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
