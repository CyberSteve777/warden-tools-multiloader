package net.trique.wardentools.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.trique.wardentools.WardenTools;
import net.trique.wardentools.block.WardenBlocks;
import net.trique.wardentools.potion.WardenPotions;

public class WardenItemGroup {
    public static CreativeModeTab WARDENITEMGROUP = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(WardenTools.MOD_ID, "wardenitemgroup"),
            FabricItemGroup.builder().title(Component.literal("Warden Tools"))
                    .icon(() -> new ItemStack(WardenItems.WARDEN_CHESTPLATE))
                    .displayItems((displayContext, entries) -> {
                        entries.accept(WardenBlocks.SCULKHYST_BLOCK);
                        entries.accept(WardenBlocks.BUDDING_SCULKHYST);
                        entries.accept(WardenBlocks.SMALL_SCULKHYST_BUD);
                        entries.accept(WardenBlocks.MEDIUM_SCULKHYST_BUD);
                        entries.accept(WardenBlocks.LARGE_SCULKHYST_BUD);
                        entries.accept(WardenBlocks.SCULKHYST_CLUSTER);
                        entries.accept(WardenItems.SCULKIFIED_SHOVEL);
                        entries.accept(WardenItems.SCULKIFIED_PICKAXE);
                        entries.accept(WardenItems.SCULKIFIED_AXE);
                        entries.accept(WardenItems.SCULKIFIED_HOE);
                        entries.accept(WardenItems.SCULKIFIED_SWORD);
                        entries.accept(WardenItems.SCULKIFIED_HELMET);
                        entries.accept(WardenItems.SCULKIFIED_CHESTPLATE);
                        entries.accept(WardenItems.SCULKIFIED_LEGGINGS);
                        entries.accept(WardenItems.SCULKIFIED_BOOTS);
                        entries.accept(WardenItems.WARDEN_SHOVEL);
                        entries.accept(WardenItems.WARDEN_PICKAXE);
                        entries.accept(WardenItems.WARDEN_AXE);
                        entries.accept(WardenItems.WARDEN_HOE);
                        entries.accept(WardenItems.WARDEN_SWORD);
                        entries.accept(WardenItems.WARDEN_HELMET);
                        entries.accept(WardenItems.WARDEN_CHESTPLATE);
                        entries.accept(WardenItems.WARDEN_LEGGINGS);
                        entries.accept(WardenItems.WARDEN_BOOTS);
                        entries.accept(WardenItems.ECHO_STAFF);
                        entries.accept(WardenItems.ROSE_GOLD_UPGRADED_ECHO_STAFF);
                        entries.accept(WardenItems.AMETHYST_UPGRADED_ECHO_STAFF);
                        entries.accept(WardenItems.ENDER_UPGRADED_ECHO_STAFF);
                        entries.accept(WardenItems.ECHO_APPLE);
                        entries.accept(Items.ECHO_SHARD);
                        entries.accept(WardenItems.ECHO_INGOT);
                        entries.accept(WardenItems.ROSE_GOLD_INGOT);
                        entries.accept(WardenItems.AMETHYST_INGOT);
                        entries.accept(WardenItems.WARDEN_UPGRADE_SMITHING_TEMPLATE);
                        entries.accept(WardenItems.ECHO_SHRIEKER);
                        entries.accept(WardenItems.WARDEN_SOUL);
                        entries.accept(WardenItems.SCULK_SHELL);
                        entries.accept(WardenItems.SHRIEKER_FANG);
                        entries.accept(WardenItems.SCULK_ARROW);
                        entries.accept(PotionContents.createItemStack(Items.POTION, WardenPotions.SCULK_ADAPTION_POTION));
                        entries.accept(PotionContents.createItemStack(Items.SPLASH_POTION, WardenPotions.SCULK_ADAPTION_POTION));
                        entries.accept(PotionContents.createItemStack(Items.LINGERING_POTION, WardenPotions.SCULK_ADAPTION_POTION));
                        entries.accept(PotionContents.createItemStack(Items.TIPPED_ARROW, WardenPotions.SCULK_ADAPTION_POTION));
                    }).build());
    public static void registerWardenGroups() {}
}