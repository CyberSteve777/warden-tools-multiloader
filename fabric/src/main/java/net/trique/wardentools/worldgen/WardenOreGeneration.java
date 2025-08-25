package net.trique.wardentools.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.trique.wardentools.world.WardenPlacedFeatures;

public class WardenOreGeneration {
    public static void generateOres() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.DEEP_DARK, Biomes.GROVE, Biomes.CHERRY_GROVE, Biomes.MEADOW, Biomes.JAGGED_PEAKS, Biomes.SNOWY_SLOPES, Biomes.STONY_PEAKS, Biomes.FROZEN_PEAKS),
                GenerationStep.Decoration.UNDERGROUND_ORES, WardenPlacedFeatures.SCULKHYST_GEODE_PLACED_KEY);
    }
}