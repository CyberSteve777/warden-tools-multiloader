package net.trique.wardentools.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.trique.wardentools.Constants;
import net.trique.wardentools.util.WTBiomeTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class WTBiomeTagProvider extends BiomeTagsProvider {
    public WTBiomeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(WTBiomeTags.SCULKHYST_GEODE_CAN_GENERATE_IN)
                .addTags(Tags.Biomes.IS_MOUNTAIN)
                .add(Biomes.DEEP_DARK);
        tag(WTBiomeTags.WARDEN_CURSE_RECEIVE_BONUS_IN)
                .add(Biomes.DEEP_DARK);
    }
}
