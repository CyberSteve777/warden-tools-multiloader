package net.trique.wardentools.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.trique.wardentools.util.ModHelper;

public final class WTConfigClient {
    public static final ModConfigSpec SPEC;
    public static final WTConfigClient CONFIG;

    public final ModConfigSpec.BooleanValue outline_pos;

    private WTConfigClient(ModConfigSpec.Builder builder) {
        outline_pos = builder.comment("Outline position of received vibration")
                .translation(ModHelper.getTranslationKey("outline_pos"))
                .define("outline_pos", false);
    }

    static {
        ModConfigSpec.Builder configBuilder = new ModConfigSpec.Builder();
        CONFIG = new WTConfigClient(configBuilder);
        SPEC = configBuilder.build();
    }
}
