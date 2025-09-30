package net.trique.wardentools.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.trique.wardentools.util.ModHelper;

public class WTConfigServer {
    public static final ModConfigSpec SPEC;
    public static final WTConfigServer CONFIG;

    public final ModConfigSpec.DoubleValue seconds_to_outline_block;
    public final ModConfigSpec.DoubleValue seconds_to_glow_entity;

    private WTConfigServer(ModConfigSpec.Builder builder) {
        seconds_to_outline_block = builder.comment("Seconds the block will be outlined for")
                .translation(ModHelper.getTranslationKey("seconds_to_outline_block"))
                .defineInRange("seconds_to_outline_block", 5., 0., 10.);
        seconds_to_glow_entity = builder.comment("Seconds the entity will appear glowing for")
                .translation(ModHelper.getTranslationKey("seconds_to_glow_entity"))
                .defineInRange("seconds_to_glow_entity", 5., 0., 10.);

    }

    static {
        ModConfigSpec.Builder configBuilder = new ModConfigSpec.Builder();
        CONFIG = new WTConfigServer(configBuilder);
        SPEC = configBuilder.build();
    }
}
