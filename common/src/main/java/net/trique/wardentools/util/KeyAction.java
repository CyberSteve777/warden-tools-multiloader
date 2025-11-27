package net.trique.wardentools.util;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;

public enum KeyAction implements StringRepresentable {
    CONSUME_CHARGES;

    @Override
    public String getSerializedName() {
        return name();
    }

    public static final Codec<KeyAction> CODEC = StringRepresentable.fromEnum(KeyAction::values);
    public static final StreamCodec<RegistryFriendlyByteBuf,KeyAction> STREAM_CODEC = ModHelper.enumStreamCodec(KeyAction.class);
}
