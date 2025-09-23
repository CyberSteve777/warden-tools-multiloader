package net.trique.wardentools.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.trique.wardentools.Constants;

import java.util.concurrent.CompletableFuture;

import static net.trique.wardentools.registry.ItemRegistry.*;


public class WTRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public WTRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        offerCustomSmithingTemplateCopyingRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE::get,
                Items.DIAMOND, Items.COBBLED_DEEPSLATE);


        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.NETHERITE_AXE, ECHO_INGOT.get(), RecipeCategory.COMBAT, SCULKIFIED_AXE.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.NETHERITE_SWORD, ECHO_INGOT.get(), RecipeCategory.COMBAT, SCULKIFIED_SWORD.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.NETHERITE_PICKAXE, ECHO_INGOT.get(), RecipeCategory.TOOLS, SCULKIFIED_PICKAXE.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.NETHERITE_SHOVEL, ECHO_INGOT.get(), RecipeCategory.TOOLS, SCULKIFIED_SHOVEL.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.NETHERITE_HOE, ECHO_INGOT.get(), RecipeCategory.TOOLS, SCULKIFIED_HOE.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.NETHERITE_HELMET, ECHO_INGOT.get(), RecipeCategory.COMBAT, SCULKIFIED_HELMET.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.NETHERITE_CHESTPLATE, ECHO_INGOT.get(), RecipeCategory.COMBAT, SCULKIFIED_CHESTPLATE.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.NETHERITE_LEGGINGS, ECHO_INGOT.get(), RecipeCategory.COMBAT, SCULKIFIED_LEGGINGS.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.NETHERITE_BOOTS, ECHO_INGOT.get(), RecipeCategory.COMBAT, SCULKIFIED_BOOTS.get());

        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                SCULKIFIED_AXE.get(), SCULK_SHELL.get(), RecipeCategory.COMBAT, WARDEN_AXE.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                SCULKIFIED_SWORD.get(), SCULK_SHELL.get(), RecipeCategory.COMBAT, WARDEN_SWORD.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                SCULKIFIED_PICKAXE.get(), SCULK_SHELL.get(), RecipeCategory.TOOLS, WARDEN_PICKAXE.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                SCULKIFIED_SHOVEL.get(), SCULK_SHELL.get(), RecipeCategory.TOOLS, WARDEN_SHOVEL.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                SCULKIFIED_HOE.get(), SCULK_SHELL.get(), RecipeCategory.TOOLS, WARDEN_HOE.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                SCULKIFIED_HELMET.get(), SCULK_SHELL.get(), RecipeCategory.COMBAT, WARDEN_HELMET.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                SCULKIFIED_CHESTPLATE.get(), SCULK_SHELL.get(), RecipeCategory.COMBAT, WARDEN_CHESTPLATE.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                SCULKIFIED_LEGGINGS.get(), SCULK_SHELL.get(), RecipeCategory.COMBAT, WARDEN_LEGGINGS.get());
        offerCustomUpgradeRecipe(recipeOutput, WARDEN_UPGRADE_SMITHING_TEMPLATE.get(),
                SCULKIFIED_BOOTS.get(), SCULK_SHELL.get(), RecipeCategory.COMBAT, WARDEN_BOOTS.get());

        offerWardenMaskRecipe(recipeOutput);
        offerShapedEchoShriekerRecipe(recipeOutput);
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
    }

    protected static String getId(String name) {
        return Constants.MOD_ID + ":" + name;
    }

    protected static void offerCustomUpgradeRecipe(RecipeOutput exporter, Item template, Item input, Item itemMaterialUpgrade, RecipeCategory category, Item result) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(template), Ingredient.of(input), Ingredient.of(itemMaterialUpgrade), category, result).
                unlocks(RecipeProvider.getHasName(() -> itemMaterialUpgrade), RecipeProvider.has(itemMaterialUpgrade)).save(exporter, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, RecipeProvider.getItemName(result) + "_smithing"));
    }

    protected static void offerCustomSmithingTemplateCopyingRecipe(RecipeOutput exporter, ItemLike template, ItemLike duplicationMaterial, ItemLike resource) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, template, 2).define('#', duplicationMaterial).define('C', resource).define('S', template).pattern("#S#").pattern("#C#").pattern("###").
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
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ECHO_STAFF.get(), 1)
                .define('s', WARDEN_SOUL.get())
                .define('w', SCULK_SHELL::get)
                .define('e', Items.ECHO_SHARD)
                .pattern("s")
                .pattern("w")
                .pattern("e")
                .unlockedBy("has_sculk_shell", RecipeProvider.has(SCULK_SHELL.get()))
                .unlockedBy("has_warden_soul", RecipeProvider.has(WARDEN_SOUL.get()))
                .save(exporter);
    }

    private static void offerWardenMaskRecipe(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, WARDEN_MASK.get())
                .define('t', WARDEN_TENDRIL.get())
                .define('e', Items.ECHO_SHARD)
                .define('h', SCULKIFIED_HELMET.get())
                .define('s', SCULK_SHELL.get())
                .pattern("tet")
                .pattern("shs")
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
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ECHO_SHRIEKER.get(), 1)
                .define('f', SHRIEKER_FANG::get)
                .define('e', ECHO_STAFF::get)
                .define('h', Items.ECHO_SHARD)
                .define('r', Items.STRING)
                .pattern("fhr")
                .pattern("e r")
                .pattern("fhr")
                .unlockedBy("has_shrieker_fang", RecipeProvider.has(SHRIEKER_FANG.get()))
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
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, SCULK_ARROW.get(), 1)
                .requires(Items.ECHO_SHARD)
                .requires(Items.ARROW)
                .unlockedBy(RecipeProvider.getHasName(Items.ECHO_SHARD), RecipeProvider.has(Items.ECHO_SHARD))
                .unlockedBy(RecipeProvider.getHasName(Items.ARROW), RecipeProvider.has(Items.ARROW))
                .save(exporter);
    }
}
