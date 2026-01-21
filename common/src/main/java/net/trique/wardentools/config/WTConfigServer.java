package net.trique.wardentools.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.trique.wardentools.util.ModHelper;

public class WTConfigServer {
    public static final ForgeConfigSpec SPEC;
    public static final WTConfigServer CONFIG;

    public final ForgeConfigSpec.IntValue charges_cap;
    public final ForgeConfigSpec.DoubleValue seconds_to_outline_block;
    public final ForgeConfigSpec.DoubleValue seconds_to_glow_entity;

    private WTConfigServer(ForgeConfigSpec.Builder builder) {
        charges_cap = builder.comment("Charges cap the Warden Echo Staff can consume per single special attack")
                .translation(ModHelper.getTranslationKey("charges_cap"))
                .defineInRange("max_charges", 10, 5, 15);
        seconds_to_outline_block = builder.comment("Seconds the block will be outlined for")
                .translation(ModHelper.getTranslationKey("seconds_to_outline_block"))
                .defineInRange("seconds_to_outline_block", 5., 0., 10.);
        seconds_to_glow_entity = builder.comment("Seconds the entity will appear glowing for")
                .translation(ModHelper.getTranslationKey("seconds_to_glow_entity"))
                .defineInRange("seconds_to_glow_entity", 5., 0., 10.);

    }

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        CONFIG = new WTConfigServer(configBuilder);
        SPEC = configBuilder.build();
    }
}
