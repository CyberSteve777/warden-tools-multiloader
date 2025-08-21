package net.trique.wardentools.effect;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.trique.wardentools.WardenTools;

public class WardenEffects {

    public static  Holder<MobEffect> SCULK_ADAPTION;

    private static Holder<MobEffect> registerStatuesEffect(MobEffect statusEffect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
                ResourceLocation.fromNamespaceAndPath(WardenTools.MOD_ID, "sculk_adaption"),
                statusEffect
        );
    }

    public static void registerWardenEffects(){
        SCULK_ADAPTION =
                    registerStatuesEffect(new SculkAdaptionEffect(MobEffectCategory.BENEFICIAL,0x009295));
        WardenTools.LOGGER.info("Registering Warden Effects");
    }
}