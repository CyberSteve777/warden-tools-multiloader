package net.trique.wardentools.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.world.item.ArmorItem;

public class WardenModelProvider extends FabricModelProvider {
    public WardenModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(WardenItems.WARDEN_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(WardenItems.WARDEN_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(WardenItems.WARDEN_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(WardenItems.WARDEN_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(WardenItems.WARDEN_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(WardenItems.SCULKIFIED_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(WardenItems.SCULKIFIED_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(WardenItems.SCULKIFIED_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(WardenItems.SCULKIFIED_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(WardenItems.SCULKIFIED_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(WardenItems.SCULK_SHELL, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(WardenItems.ECHO_APPLE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(WardenItems.WARDEN_SOUL, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(WardenItems.ECHO_INGOT, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(WardenItems.ROSE_GOLD_INGOT, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(WardenItems.AMETHYST_INGOT, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(WardenItems.ECHO_STAFF, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(WardenItems.ROSE_GOLD_UPGRADED_ECHO_STAFF, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(WardenItems.AMETHYST_UPGRADED_ECHO_STAFF, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(WardenItems.ENDER_UPGRADED_ECHO_STAFF, ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModelGenerator.generateArmorTrims(((ArmorItem) WardenItems.WARDEN_HELMET));
        itemModelGenerator.generateArmorTrims(((ArmorItem) WardenItems.WARDEN_CHESTPLATE));
        itemModelGenerator.generateArmorTrims(((ArmorItem) WardenItems.WARDEN_LEGGINGS));
        itemModelGenerator.generateArmorTrims(((ArmorItem) WardenItems.WARDEN_BOOTS));
        itemModelGenerator.generateArmorTrims(((ArmorItem) WardenItems.SCULKIFIED_HELMET));
        itemModelGenerator.generateArmorTrims(((ArmorItem) WardenItems.SCULKIFIED_CHESTPLATE));
        itemModelGenerator.generateArmorTrims(((ArmorItem) WardenItems.SCULKIFIED_LEGGINGS));
        itemModelGenerator.generateArmorTrims(((ArmorItem) WardenItems.SCULKIFIED_BOOTS));
    }
}