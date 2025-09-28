package net.trique.wardentools.networking.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.trique.wardentools.Constants;

public record AddBlockOutlinePacket(BlockPos pos, int ticks) implements CustomPacketPayload {
    public static final Type<AddBlockOutlinePacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID,
                    "add_block_outline"
            )
    );

    public static final StreamCodec<FriendlyByteBuf, AddBlockOutlinePacket> CODEC = StreamCodec.ofMember(
            AddBlockOutlinePacket::write, AddBlockOutlinePacket::new
    );

    private AddBlockOutlinePacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readInt());
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(ticks);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
