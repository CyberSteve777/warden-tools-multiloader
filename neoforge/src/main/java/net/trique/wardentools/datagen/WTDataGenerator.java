package net.trique.wardentools.datagen;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.trique.wardentools.Constants;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class WTDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = generator.addProvider(event.includeServer(),
                new WTEnchantmentProvider(packOutput, event.getLookupProvider())).getRegistryProvider();
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(WTBlockLootTableProvider::new, LootContextParamSets.BLOCK)),
                lookupProvider));
        BlockTagsProvider blockTagsProvider = new WTBlockTagProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new WTWorldGenProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new WTBiomeTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new WTItemTagProvider(packOutput, lookupProvider,
                blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new WTEntityTypeTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new WTEnchantmentTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new WTGameEventTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new WTRecipeProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeClient(), new WTBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new WTItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new WTParticleDescriptionProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), new WTGlobalLootModifierProvider(packOutput, lookupProvider));

    }
}
