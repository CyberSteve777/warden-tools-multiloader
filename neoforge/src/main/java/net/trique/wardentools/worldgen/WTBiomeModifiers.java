package net.trique.wardentools.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.trique.wardentools.Constants;
import net.trique.wardentools.util.WTBiomeTags;
import net.trique.wardentools.world.WardenPlacedFeatures;


public class WTBiomeModifiers {

    public static final ResourceKey<BiomeModifier> ADD_SCULKHYST_GEODE = registerKey("add_sculkhyst_geode");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_SCULKHYST_GEODE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(WTBiomeTags.SCULKHYST_GEODE_CAN_GENERATE_IN),
                HolderSet.direct(placedFeatures.getOrThrow(WardenPlacedFeatures.SCULKHYST_GEODE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
    }
}
