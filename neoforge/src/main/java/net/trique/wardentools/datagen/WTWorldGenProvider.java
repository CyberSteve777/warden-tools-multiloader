package net.trique.wardentools.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.trique.wardentools.Constants;
import net.trique.wardentools.world.WardenConfiguredFeatures;
import net.trique.wardentools.world.WardenPlacedFeatures;
import net.trique.wardentools.worldgen.*;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class WTWorldGenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, WardenConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, WardenPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, WTBiomeModifiers::bootstrap);

    public WTWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, BUILDER, Set.of(Constants.MOD_ID));
    }

    @Override
    public String getName() {
        return "WTWorldGen";
    }
}