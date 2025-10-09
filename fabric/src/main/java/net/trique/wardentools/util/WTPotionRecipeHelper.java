package net.trique.wardentools.util;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.trique.wardentools.registry.ItemRegistry;
import net.trique.wardentools.registry.PotionRegistry;

public class WTPotionRecipeHelper {
    public static void addPotionRecipes() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
                    builder.registerPotionRecipe(
                            Potions.AWKWARD,
                            Ingredient.of(ItemRegistry.WARDEN_SOUL.get()),
                            PotionRegistry.SCULK_ADAPTION_POTION
                    );
                    builder.registerPotionRecipe(
                            Potions.AWKWARD,
                            Ingredient.of(ItemRegistry.WARDEN_TENDRIL.get()),
                            PotionRegistry.WARDEN_POTION
                    );
                }
        );
    }
}
