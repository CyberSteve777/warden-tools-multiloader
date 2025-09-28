package net.trique.wardentools.networking.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.trique.wardentools.Constants;


public record AddGlowPacket(int id, BlockPos pos) implements CustomPacketPayload {
    public static final Type<AddGlowPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID,
                    "add_glow"
            )
    );

    public static final StreamCodec<FriendlyByteBuf, AddGlowPacket> CODEC = StreamCodec.ofMember(
            AddGlowPacket::write, AddGlowPacket::new
    );

    private AddGlowPacket(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readBlockPos());
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeBlockPos(pos);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
