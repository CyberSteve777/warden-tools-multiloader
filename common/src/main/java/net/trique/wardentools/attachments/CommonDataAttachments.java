package net.trique.wardentools.attachments;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.trique.wardentools.Constants;
import net.trique.wardentools.platform.Services;
import net.trique.wardentools.util.KeyAction;

import java.util.*;

public class CommonDataAttachments {

    private static final Map<ResourceLocation,CommonDataAttachment<?>> MAP =new HashMap<>();

    static final StreamCodec<RegistryFriendlyByteBuf, EnumSet<KeyAction>> STREAM_CODEC = KeyAction.STREAM_CODEC.apply(ByteBufCodecs.collection(
       value -> EnumSet.noneOf(KeyAction.class)
    ));

    public static final CommonDataAttachment<EnumSet<KeyAction>> ACTIVE_KEYBINDS =
            register(CommonDataAttachment.create(o -> EnumSet.noneOf(KeyAction.class))
                    .networkSynchronized(STREAM_CODEC)
                    .build("active_keybinds"));

    public static CommonDataAttachment<?> lookup(ResourceLocation location) {
        return MAP.get(location);
    }

    static <T> CommonDataAttachment<T> register(CommonDataAttachment<T> type) {
        Services.PLATFORM.registerDataAttachment(type);
        Objects.requireNonNull(type.getAttachment());
        MAP.put(type.name,type);
        return type;
    }

    public static void init() {
        Constants.LOGGER.info("Registering Warden Tools Data Attachments...");
    }
}
