package net.trique.wardentools.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.trique.wardentools.Constants;


public record AddEntityGlowPacket(int Id) implements CustomPacketPayload {
    public static final Type<AddEntityGlowPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID,
                    "add_entity_glow"
            )
    );

    public static final StreamCodec<FriendlyByteBuf, AddEntityGlowPacket> CODEC = StreamCodec.ofMember(
            AddEntityGlowPacket::write, AddEntityGlowPacket::new
    );

    public AddEntityGlowPacket(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(Id);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
