package net.trique.wardentools.util;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.trique.wardentools.WardenTools;

public class DatagenHelper {

    public static void offerCustomUpgradeRecipe(RecipeOutput exporter, Item template, Item input, Item itemMaterialUpgrade, RecipeCategory category, Item result) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(template), Ingredient.of(input), Ingredient.of(itemMaterialUpgrade), category, result).
                unlocks(RecipeProvider.getHasName(itemMaterialUpgrade), RecipeProvider.has(itemMaterialUpgrade)).save(exporter, ResourceLocation.fromNamespaceAndPath(WardenTools.MOD_ID,RecipeProvider.getItemName(result)+ "_smithing")  );
    }

    public static void offerCustomSmithingTemplateCopyingRecipe(RecipeOutput exporter, ItemLike template, ItemLike duplicationMaterial, ItemLike resource) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, template, 2).define('#', duplicationMaterial).define('C', resource).define('S', template).pattern("#S#").pattern("#C#").pattern("###").
                unlockedBy(RecipeProvider.getHasName(template), RecipeProvider.has(template)).save(exporter);
    }

    public static void offerShapedEchoAppleRecipe(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, WardenItems.ECHO_APPLE,1)
                .define('e', Items.ECHO_SHARD)
                .define('a',()-> Items.APPLE)
                .pattern(" e ")
                .pattern("eae")
                .pattern(" e ")
                .unlockedBy("has_apple", RecipeProvider.has(Items.APPLE))
                .unlockedBy("has_echo_shard", RecipeProvider.has(Items.ECHO_SHARD))
                .save(exporter);
    }

    public static void offerShapedAmethystIngotRecipe(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, WardenItems.AMETHYST_INGOT,1)
                .define('e', Items.AMETHYST_SHARD)
                .define('a',()-> WardenItems.ECHO_INGOT)
                .pattern(" e ")
                .pattern("eae")
                .pattern(" e ")
                .unlockedBy("has_amethyst_shard", RecipeProvider.has(Items.AMETHYST_SHARD))
                .unlockedBy("has_echo_ingot", RecipeProvider.has(WardenItems.ECHO_INGOT))
                .save(exporter);
    }

    public static void offerShapedEchoIngotRecipe(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, WardenItems.ECHO_INGOT,1)
                .define('e', Items.ECHO_SHARD)
                .define('a',()-> Items.COPPER_INGOT)
                .pattern(" e ")
                .pattern("eae")
                .pattern(" e ")
                .unlockedBy("has_echo_shard", RecipeProvider.has(Items.ECHO_SHARD))
                .unlockedBy("has_copper_ingot", RecipeProvider.has(Items.COPPER_INGOT))
                .save(exporter);
    }

    public static void offerShapedEchoStaffRecipe(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, WardenItems.ECHO_STAFF, 1)
                .define('s', WardenItems.WARDEN_SOUL)
                .define('w', () -> WardenItems.SCULK_SHELL)
                .define('e', Items.ECHO_SHARD)
                .pattern("s")
                .pattern("w")
                .pattern("e")
                .unlockedBy("has_sculk_shell", RecipeProvider.has(WardenItems.SCULK_SHELL))
                .unlockedBy("has_warden_soul", RecipeProvider.has(WardenItems.WARDEN_SOUL))
                .save(exporter);
    }

    public static void offerShapedEchoShriekerRecipe(RecipeOutput exporter){
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, WardenItems.ECHO_SHRIEKER,1)
                .define('f', WardenItems.SHRIEKER_FANG)
                .define('h',Items.ECHO_SHARD)
                .define('r',Items.STRING)
                .pattern("fhr")
                .pattern("  r")
                .pattern("fhr")
                .unlockedBy("has_shrieker_fang", RecipeProvider.has(WardenItems.SHRIEKER_FANG))
                .save(exporter);
    }
}