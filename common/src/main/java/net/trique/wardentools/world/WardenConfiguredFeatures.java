package net.trique.wardentools.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.trique.wardentools.Constants;

import java.util.List;

import static net.trique.wardentools.registry.BlockRegistry.*;

public class WardenConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> SCULKHYST_GEODE_KEY = registerKey("sculkhyst_geode");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

        register(context, SCULKHYST_GEODE_KEY, Feature.GEODE ,
                new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR),
                        BlockStateProvider.simple(SCULKHYST_BLOCK.get()),
                        BlockStateProvider.simple(BUDDING_SCULKHYST.get()),
                        BlockStateProvider.simple(Blocks.CALCITE),
                        BlockStateProvider.simple(Blocks.SMOOTH_BASALT),
                        List.of(SMALL_SCULKHYST_BUD.get().defaultBlockState(), MEDIUM_SCULKHYST_BUD.get().defaultBlockState(),
                                LARGE_SCULKHYST_BUD.get().defaultBlockState(), SCULKHYST_CLUSTER.get().defaultBlockState()),
                        BlockTags.FEATURES_CANNOT_REPLACE , BlockTags.GEODE_INVALID_BLOCKS),
                        new GeodeLayerSettings(1.7, 2.2, 3.2, 4.2),
                        new GeodeCrackSettings(0.95, 2.0, 2),
                        0.35, 0.083,
                        true, UniformInt.of(4, 6),
                        UniformInt.of(3, 4), UniformInt.of(1, 2),
                        -16, 16, 0.05, 1));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                   ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}