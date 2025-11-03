package net.trique.wardentools.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.trique.wardentools.Constants;
import net.trique.wardentools.util.WTBlockTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static net.trique.wardentools.registry.BlockRegistry.*;

public class WTBlockTagsProvider extends BlockTagsProvider {
    public WTBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_HOE).add(
                BUDDING_SCULKHYST.get(),
                SMALL_SCULKHYST_BUD.get(),
                MEDIUM_SCULKHYST_BUD.get(),
                LARGE_SCULKHYST_BUD.get(),
                SCULKHYST_CLUSTER.get()
        );
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                WARDEN_BLOCK.get(),
                BLOCK_OF_AMETHYST_INGOTS.get(),
                ROSE_GOLD_BLOCK.get(),
                ECHO_BLOCK.get()
        );
        tag(BlockTags.NEEDS_IRON_TOOL).add(
                BUDDING_SCULKHYST.get(),
                SMALL_SCULKHYST_BUD.get(),
                MEDIUM_SCULKHYST_BUD.get(),
                LARGE_SCULKHYST_BUD.get(),
                SCULKHYST_CLUSTER.get(),
                ROSE_GOLD_BLOCK.get(),
                BLOCK_OF_AMETHYST_INGOTS.get(),
                ECHO_BLOCK.get()
        );
        tag(BlockTags.NEEDS_DIAMOND_TOOL).add(
                WARDEN_BLOCK.get()
        );
        tag(Tags.Blocks.CLUSTERS).add(
                SCULKHYST_CLUSTER.get()
        );
        tag(WTBlockTags.INCORRECT_FOR_SCULKHYST_TOOLS);
        tag(WTBlockTags.INCORRECT_FOR_WARDEN_TOOLS);
        tag(WTBlockTags.STORAGE_BLOCKS_AMETHYST_INGOT).add(BLOCK_OF_AMETHYST_INGOTS.get());
        tag(WTBlockTags.STORAGE_BLOCKS_ECHO).add(ECHO_BLOCK.get());
        tag(WTBlockTags.STORAGE_BLOCKS_WARDEN).add(WARDEN_BLOCK.get());
        tag(WTBlockTags.STORAGE_BLOCKS_ROSE_GOLD).add(ROSE_GOLD_BLOCK.get());
        tag(Tags.Blocks.STORAGE_BLOCKS).addTags(
                WTBlockTags.STORAGE_BLOCKS_ROSE_GOLD,
                WTBlockTags.STORAGE_BLOCKS_ECHO,
                WTBlockTags.STORAGE_BLOCKS_AMETHYST_INGOT,
                WTBlockTags.STORAGE_BLOCKS_WARDEN
        );
    }
}
