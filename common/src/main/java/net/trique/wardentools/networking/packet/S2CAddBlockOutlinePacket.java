package net.trique.wardentools.networking.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.trique.wardentools.Constants;
import net.trique.wardentools.util.warden_curse.WardenCurseClientHelper;

import static net.trique.wardentools.config.WTConfigClient.CONFIG;

public record S2CAddBlockOutlinePacket(BlockPos pos, int ticks) implements S2CModPacket<RegistryFriendlyByteBuf> {
    public static final Type<S2CAddBlockOutlinePacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID,
                    "add_block_outline"
            )
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CAddBlockOutlinePacket> CODEC = StreamCodec.ofMember(
            S2CAddBlockOutlinePacket::write, S2CAddBlockOutlinePacket::new
    );

    private S2CAddBlockOutlinePacket(RegistryFriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readInt());
    }

    private void write(RegistryFriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(ticks);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public void handleClient() {
        if (CONFIG.outline_pos.get()) WardenCurseClientHelper.addBlockPos(pos, ticks);
    }
}
