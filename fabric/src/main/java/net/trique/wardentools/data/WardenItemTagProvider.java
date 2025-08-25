package net.trique.wardentools.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.trique.wardentools.WardenTools;

import java.util.concurrent.CompletableFuture;

public class WardenItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public static final TagKey<Item> SCULKHYST_CLUSTER_MAX_HARVESTABLES = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(WardenTools.MOD_ID,"sculkhyst_cluster_max_harvestables"));

    public WardenItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        tag(ItemTags.ARROWS).add(WardenItems.SCULK_ARROW);
        tag(ItemTags.SWORDS).add(WardenItems.SCULKIFIED_SWORD);
        tag(ItemTags.SHOVELS).add(WardenItems.SCULKIFIED_SHOVEL);
        tag(ItemTags.AXES).add(WardenItems.SCULKIFIED_AXE);
        tag(ItemTags.HOES).add(WardenItems.SCULKIFIED_HOE);
        tag(ItemTags.PICKAXES).add(WardenItems.SCULKIFIED_PICKAXE);
        tag(ItemTags.HEAD_ARMOR).add(WardenItems.SCULKIFIED_HELMET);
        tag(ItemTags.CHEST_ARMOR).add(WardenItems.SCULKIFIED_CHESTPLATE);
        tag(ItemTags.LEG_ARMOR).add(WardenItems.SCULKIFIED_LEGGINGS);
        tag(ItemTags.FOOT_ARMOR).add(WardenItems.SCULKIFIED_BOOTS);
        tag(ItemTags.SWORDS).add(WardenItems.WARDEN_SWORD);
        tag(ItemTags.SHOVELS).add(WardenItems.WARDEN_SHOVEL);
        tag(ItemTags.AXES).add(WardenItems.WARDEN_AXE);
        tag(ItemTags.HOES).add(WardenItems.WARDEN_HOE);
        tag(ItemTags.PICKAXES).add(WardenItems.WARDEN_PICKAXE);
        tag(ItemTags.HEAD_ARMOR).add(WardenItems.WARDEN_HELMET);
        tag(ItemTags.CHEST_ARMOR).add(WardenItems.WARDEN_CHESTPLATE);
        tag(ItemTags.LEG_ARMOR).add(WardenItems.WARDEN_LEGGINGS);
        tag(ItemTags.FOOT_ARMOR).add(WardenItems.WARDEN_BOOTS);
        tag(ItemTags.TRIMMABLE_ARMOR).add(
                WardenItems.WARDEN_HELMET,
                WardenItems.WARDEN_CHESTPLATE,
                WardenItems.WARDEN_LEGGINGS,
                WardenItems.WARDEN_BOOTS);
        tag(ItemTags.TRIMMABLE_ARMOR).add(
                WardenItems.SCULKIFIED_HELMET,
                WardenItems.SCULKIFIED_CHESTPLATE,
                WardenItems.SCULKIFIED_LEGGINGS,
                WardenItems.SCULKIFIED_BOOTS);
        tag(SCULKHYST_CLUSTER_MAX_HARVESTABLES).forceAddTag(ItemTags.HOES);
    }
}