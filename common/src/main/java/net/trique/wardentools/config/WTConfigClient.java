package net.trique.wardentools.config;

import net.minecraftforge.common.*;
import net.trique.wardentools.util.ModHelper;

public class WTConfigClient {
    public static final ForgeConfigSpec SPEC;
    public static final WTConfigClient CONFIG;

    public final ForgeConfigSpec.BooleanValue outline_pos;

    private WTConfigClient(ForgeConfigSpec.Builder builder) {
        outline_pos = builder.comment("Outline position of received vibration")
                .translation(ModHelper.getTranslationKey("outline_pos"))
                .define("outline_pos", false);
    }

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        CONFIG = new WTConfigClient(configBuilder);
        SPEC = configBuilder.build();
    }
}
