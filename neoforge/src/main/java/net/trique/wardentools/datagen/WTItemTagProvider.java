package net.trique.wardentools.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.trique.wardentools.Constants;
import net.trique.wardentools.util.WTItemTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static net.trique.wardentools.registry.ItemRegistry.*;

public class WTItemTagProvider extends ItemTagsProvider {
    public WTItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(
                        SCULKIFIED_HELMET.get(), SCULKIFIED_CHESTPLATE.get(), SCULKIFIED_LEGGINGS.get(),
                        SCULKIFIED_BOOTS.get(), WARDEN_HELMET.get(), WARDEN_CHESTPLATE.get(), WARDEN_LEGGINGS.get(),
                        WARDEN_BOOTS.get());
        tag(ItemTags.CLUSTER_MAX_HARVESTABLES).add(
                SCULKIFIED_PICKAXE.get(), WARDEN_PICKAXE.get()
        );
        tag(ItemTags.SWORDS).add(
                SCULKIFIED_SWORD.get(), WARDEN_SWORD.get()
        );
        tag(ItemTags.AXES).add(
                SCULKIFIED_AXE.get(), WARDEN_AXE.get()
        );
        tag(ItemTags.PICKAXES).add(
                SCULKIFIED_PICKAXE.get(), WARDEN_PICKAXE.get()
        );
        tag(ItemTags.SHOVELS).add(
                SCULKIFIED_SHOVEL.get(), WARDEN_SHOVEL.get()
        );
        tag(ItemTags.HOES).add(
                SCULKIFIED_HOE.get(), WARDEN_HOE.get()
        );
        tag(ItemTags.HEAD_ARMOR).add(
                SCULKIFIED_HELMET.get(), WARDEN_HELMET.get()
        );
        tag(ItemTags.CHEST_ARMOR).add(
                SCULKIFIED_CHESTPLATE.get(), WARDEN_CHESTPLATE.get()
        );
        tag(ItemTags.LEG_ARMOR).add(
                SCULKIFIED_LEGGINGS.get(), WARDEN_LEGGINGS.get()
        );
        tag(ItemTags.FOOT_ARMOR).add(
                SCULKIFIED_BOOTS.get(), WARDEN_BOOTS.get()
        );
        tag(ItemTags.ARROWS).add(
                SCULK_ARROW.get()
        );
        tag(WTItemTags.SCULKHYST_CLUSTER_MAX_HARVESTABLES).addTag(
                ItemTags.HOES
        );
        tag(WTItemTags.SONIC_BOOM_ITEM_ENCHANTABLE).add(
                ECHO_STAFF.get(),
                ROSE_GOLD_UPGRADED_ECHO_STAFF.get(),
                AMETHYST_UPGRADED_ECHO_STAFF.get(),
                ENDER_UPGRADED_ECHO_STAFF.get(),
                ECHO_SHRIEKER.get()
        );
    }
}
