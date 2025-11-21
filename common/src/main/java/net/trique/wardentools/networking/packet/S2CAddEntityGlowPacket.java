package net.trique.wardentools.networking.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.trique.wardentools.Constants;
import net.trique.wardentools.util.warden_curse.WardenCurseClientHelper;


public record S2CAddEntityGlowPacket(int id, int ticks) implements S2CModPacket<RegistryFriendlyByteBuf> {
    public static final Type<S2CAddEntityGlowPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID,
                    "add_entity_glow"
            )
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CAddEntityGlowPacket> CODEC = StreamCodec.ofMember(
            S2CAddEntityGlowPacket::write, S2CAddEntityGlowPacket::new
    );

    private S2CAddEntityGlowPacket(RegistryFriendlyByteBuf buf) {
        this(buf.readInt(), buf.readInt());
    }

    private void write(RegistryFriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeInt(ticks);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public void handleClient() {
        WardenCurseClientHelper.addEntity(id, ticks);
    }
}
