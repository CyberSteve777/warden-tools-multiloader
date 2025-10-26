package net.trique.wardentools;

import net.trique.wardentools.item.material.WardenArmorMaterials;
import net.trique.wardentools.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;
import net.trique.wardentools.registry.*;
import net.trique.wardentools.registry.WTArmorSets;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class WardenToolsCommon {

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {

        Constants.LOGGER.info("Hello from Common init on {}! we are currently in a {} environment!", Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
        Constants.LOGGER.info("The ID for diamonds is {}", BuiltInRegistries.ITEM.getKey(Items.DIAMOND));

        GameEventRegistry.init();
        EffectRegistry.init();
        WTArmorSets.init();
        ItemRegistry.init();
        BlockRegistry.init();
        WardenArmorMaterials.init();
        ParticleRegistry.init();
        PotionRegistry.init();
        EnchantmentEffectComponentRegistry.init();
        EntityRegistry.init();
        CreativeTabRegistry.init();
    }
}