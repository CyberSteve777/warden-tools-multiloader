package net.trique.wardentools.util;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.trique.wardentools.Constants;

public class ModHelper {
    public static ResourceLocation getLoc(String key) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, key);
    }

    public static String getTranslationKey(String key) {
        return Constants.MOD_ID + "." + key;
    }

    public static <B extends FriendlyByteBuf, V extends Enum<V>> StreamCodec<B, V> enumStreamCodec(final Class<V> enumClass) {
        return new StreamCodec<>() {
            @Override
            public V decode(B buf) {
                return buf.readEnum(enumClass);
            }

            @Override
            public void encode(B buf, V value) {
                buf.writeEnum(value);
            }
        };
    }
}
