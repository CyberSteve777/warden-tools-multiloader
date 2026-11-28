package net.trique.wardentools.util;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.Nullable;

public class WardenMaskRecipeBuilder extends ShapedRecipeBuilder {
    private final Ingredient baseHelmet;

    public WardenMaskRecipeBuilder(RecipeCategory category, ItemLike result, int count, Ingredient baseHelmet) {
        super(category, result, count);
        this.baseHelmet = baseHelmet;
    }

    public static WardenMaskRecipeBuilder create(RecipeCategory category, ItemLike result, ItemLike baseHelmet) {
        return new WardenMaskRecipeBuilder(category, result, 1, Ingredient.of(baseHelmet));
    }

    public static WardenMaskRecipeBuilder create(RecipeCategory category, ItemLike result, TagKey<Item> baseHelmetTag) {
        return new WardenMaskRecipeBuilder(category, result, 1, Ingredient.of(baseHelmetTag));
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        super.save(new RecipeOutput() {
            @Override
            public void accept(ResourceLocation recipeId, Recipe<?> recipe,
                               @Nullable AdvancementHolder advancement, ICondition... conditions) {
                if (recipe instanceof ShapedRecipe s) {
                    recipe = new WardenMaskRecipe(s.getGroup(), s.category(), s.pattern, s.result,
                            baseHelmet, s.showNotification());
                }
                output.accept(recipeId, recipe, advancement, conditions);
            }

            @Override
            public Advancement.Builder advancement() {
                return output.advancement();
            }
        }, id);
    }
}
