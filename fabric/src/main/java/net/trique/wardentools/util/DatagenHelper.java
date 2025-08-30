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


}