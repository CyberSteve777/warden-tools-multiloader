package net.trique.wardentools.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.trique.wardentools.Constants;

import java.util.concurrent.CompletableFuture;

import static net.trique.wardentools.registry.ItemRegistry.*;
import static net.trique.wardentools.registry.BlockRegistry.*;


public class WTRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public WTRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        offerCustomSmithingTemplateCopyingRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE::get,
                Items.DIAMOND, Items.COBBLED_DEEPSLATE);

        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.DIAMOND_AXE, ECHO_INGOT.get(), RecipeCategory.COMBAT, SCULKIFIED_AXE.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.DIAMOND_SWORD, ECHO_INGOT.get(), RecipeCategory.COMBAT, SCULKIFIED_SWORD.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.DIAMOND_PICKAXE, ECHO_INGOT.get(), RecipeCategory.TOOLS, SCULKIFIED_PICKAXE.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.DIAMOND_SHOVEL, ECHO_INGOT.get(), RecipeCategory.TOOLS, SCULKIFIED_SHOVEL.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.DIAMOND_HOE, ECHO_INGOT.get(), RecipeCategory.TOOLS, SCULKIFIED_HOE.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.DIAMOND_HELMET, ECHO_INGOT.get(), RecipeCategory.COMBAT, SCULKIFIED_HELMET.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.DIAMOND_CHESTPLATE, ECHO_INGOT.get(), RecipeCategory.COMBAT, SCULKIFIED_CHESTPLATE.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.DIAMOND_LEGGINGS, ECHO_INGOT.get(), RecipeCategory.COMBAT, SCULKIFIED_LEGGINGS.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.DIAMOND_BOOTS, ECHO_INGOT.get(), RecipeCategory.COMBAT, SCULKIFIED_BOOTS.get());

        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                SCULKIFIED_AXE.get(), WARDEN_INGOT.get(), RecipeCategory.COMBAT, WARDEN_AXE.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                SCULKIFIED_SWORD.get(), WARDEN_INGOT.get(), RecipeCategory.COMBAT, WARDEN_SWORD.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                SCULKIFIED_PICKAXE.get(), WARDEN_INGOT.get(), RecipeCategory.TOOLS, WARDEN_PICKAXE.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                SCULKIFIED_SHOVEL.get(), WARDEN_INGOT.get(), RecipeCategory.TOOLS, WARDEN_SHOVEL.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                SCULKIFIED_HOE.get(), WARDEN_INGOT.get(), RecipeCategory.TOOLS, WARDEN_HOE.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                SCULKIFIED_HELMET.get(), WARDEN_INGOT.get(), RecipeCategory.COMBAT, WARDEN_HELMET.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                SCULKIFIED_CHESTPLATE.get(), WARDEN_INGOT.get(), RecipeCategory.COMBAT, WARDEN_CHESTPLATE.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                SCULKIFIED_LEGGINGS.get(), WARDEN_INGOT.get(), RecipeCategory.COMBAT, WARDEN_LEGGINGS.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                SCULKIFIED_BOOTS.get(), WARDEN_INGOT.get(), RecipeCategory.COMBAT, WARDEN_BOOTS.get());

        offerWardenMaskRecipe(recipeOutput);
        offerShapedEchoShriekerRecipe(recipeOutput);
        offerShapedSckulifiedBowRecipe(recipeOutput);
        offerCrossShapedRecipe(recipeOutput, RecipeCategory.MISC, Items.COPPER_INGOT, Items.ECHO_SHARD,
                ECHO_INGOT.get(), 1);
        offerCrossShapedRecipe(recipeOutput, RecipeCategory.MISC, ECHO_INGOT.get(), Items.AMETHYST_SHARD,
                AMETHYST_INGOT.get(), 1);
        offerCrossShapedRecipe(recipeOutput, RecipeCategory.FOOD, Items.APPLE, Items.ECHO_SHARD,
                ECHO_APPLE.get(), 1);
        offerShapedEchoStaffRecipe(recipeOutput);
        offerShapedUpgradedEchoStaffRecipe(recipeOutput, ECHO_STAFF.get(), ROSE_GOLD_INGOT.get(), ROSE_GOLD_UPGRADED_ECHO_STAFF.get());
        offerShapedUpgradedEchoStaffRecipe(recipeOutput, ECHO_STAFF.get(), AMETHYST_INGOT.get(), AMETHYST_UPGRADED_ECHO_STAFF.get());
        offerShapedUpgradedEchoStaffRecipe(recipeOutput, ECHO_STAFF.get(), Items.ENDER_EYE, ENDER_UPGRADED_ECHO_STAFF.get());
        offerShapelessRoseGoldIngotRecipe(recipeOutput);
        offerShapelessSculkArrowRecipe(recipeOutput);
        offerShapelessWardenIngotRecipe(recipeOutput);
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, ROSE_GOLD_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, ROSE_GOLD_BLOCK.get());
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, AMETHYST_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, BLOCK_OF_AMETHYST_INGOTS.get());
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, ECHO_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, ECHO_BLOCK.get());
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, WARDEN_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, WARDEN_BLOCK.get());
    }

    protected static void offerCustomUpgradeRecipe(RecipeOutput exporter, Item template, Item input, Item itemMaterialUpgrade, RecipeCategory category, Item result) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(template), Ingredient.of(input), Ingredient.of(itemMaterialUpgrade), category, result).
                unlocks(RecipeProvider.getHasName(itemMaterialUpgrade), RecipeProvider.has(itemMaterialUpgrade)).save(exporter, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, RecipeProvider.getItemName(result) + "_smithing"));
    }

    protected static void offerCustomSmithingTemplateCopyingRecipe(RecipeOutput exporter, ItemLike template, ItemLike duplicationMaterial, ItemLike resource) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, template, 2)
                .define('#', duplicationMaterial)
                .define('C', resource)
                .define('S', template)
                .pattern("#S#")
                .pattern("#C#")
                .pattern("###").
                unlockedBy(RecipeProvider.getHasName(template), RecipeProvider.has(template)).save(exporter);
    }

    protected static void offerCrossShapedRecipe(RecipeOutput exporter, RecipeCategory category, ItemLike baseItem,
                                                 ItemLike material, ItemLike result, int count) {
        ShapedRecipeBuilder.shaped(category, result, count)
                .define('m', material)
                .define('b', baseItem)
                .pattern(" m ")
                .pattern("mbm")
                .pattern(" m ")
                .unlockedBy(RecipeProvider.getHasName(baseItem), RecipeProvider.has(baseItem))
                .unlockedBy(RecipeProvider.getHasName(material), RecipeProvider.has(material))
                .save(exporter);
    }

    private static void offerShapedEchoStaffRecipe(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ECHO_STAFF.get())
                .define('e', Items.ECHO_SHARD)
                .define('i', ECHO_INGOT::get)
                .define('s', Items.STICK)
                .pattern("e")
                .pattern("i")
                .pattern("s")
                .unlockedBy(RecipeProvider.getHasName(ECHO_INGOT.get()), RecipeProvider.has(ECHO_INGOT.get()))
                .save(exporter);
    }

    private static void offerWardenMaskRecipe(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, WARDEN_MASK.get())
                .define('t', WARDEN_TENDRIL.get())
                .define('s', Blocks.SCULK_SENSOR)
                .define('h', SCULKIFIED_HELMET.get())
                .define('w', WARDEN_INGOT.get())
                .pattern("tst")
                .pattern("whw")
                .unlockedBy(RecipeProvider.getHasName(WARDEN_TENDRIL::get), RecipeProvider.has(WARDEN_TENDRIL::get))
                .save(exporter);
    }

    private static void offerShapedUpgradedEchoStaffRecipe(RecipeOutput exporter, ItemLike baseStaff,
                                                           ItemLike upgradeMaterial, ItemLike result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result, 1)
                .define('s', baseStaff)
                .define('m', upgradeMaterial)
                .pattern("mmm")
                .pattern("msm")
                .unlockedBy(RecipeProvider.getHasName(baseStaff), RecipeProvider.has(baseStaff))
                .unlockedBy(RecipeProvider.getHasName(upgradeMaterial), RecipeProvider.has(upgradeMaterial))
                .save(exporter);
    }

    private static void offerShapedEchoShriekerRecipe(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ECHO_SHRIEKER.get())
                .define('f', SHRIEKER_FANG::get)
                .define('w', WARDEN_INGOT::get)
                .define('s', Items.STRING)
                .pattern("fws")
                .pattern("w s")
                .pattern("fws")
                .unlockedBy(RecipeProvider.getHasName(WARDEN_INGOT.get()), RecipeProvider.has(WARDEN_INGOT.get()))
                .save(exporter);
    }

    private static void offerShapedSckulifiedBowRecipe(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ECHO_LOCATOR.get(), 1)
                .define('f', SHRIEKER_FANG::get)
                .define('i', ECHO_INGOT::get)
                .define('s', Items.STRING)
                .pattern(" fs")
                .pattern("i s")
                .pattern(" fs")
                .unlockedBy(RecipeProvider.getHasName(SHRIEKER_FANG.get()), RecipeProvider.has(SHRIEKER_FANG.get()))
                .save(exporter);
    }

    private static void offerShapelessRoseGoldIngotRecipe(RecipeOutput exporter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ROSE_GOLD_INGOT.get(), 2)
                .requires(Items.COPPER_INGOT, 4)
                .requires(Items.GOLD_INGOT, 4)
                .unlockedBy(RecipeProvider.getHasName(Items.COPPER_INGOT), RecipeProvider.has(Items.COPPER_INGOT))
                .unlockedBy(RecipeProvider.getHasName(Items.GOLD_INGOT), RecipeProvider.has(Items.GOLD_INGOT))
                .save(exporter);
    }

    private static void offerShapelessSculkArrowRecipe(RecipeOutput exporter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, SCULK_ARROW.get())
                .requires(Items.ECHO_SHARD)
                .requires(Items.ARROW)
                .unlockedBy(RecipeProvider.getHasName(Items.ECHO_SHARD), RecipeProvider.has(Items.ECHO_SHARD))
                .unlockedBy(RecipeProvider.getHasName(Items.ARROW), RecipeProvider.has(Items.ARROW))
                .save(exporter);
    }

    private static void offerShapelessWardenIngotRecipe(RecipeOutput exporter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, WARDEN_INGOT.get(), 2)
                .requires(SCULK_SHELL::get)
                .requires(WARDEN_SOUL::get)
                .requires(ECHO_INGOT::get, 2)
                .requires(Items.NETHERITE_INGOT, 2)
                .unlockedBy(RecipeProvider.getHasName(WARDEN_SOUL.get()), RecipeProvider.has(WARDEN_SOUL.get()))
                .save(exporter);
    }
}
