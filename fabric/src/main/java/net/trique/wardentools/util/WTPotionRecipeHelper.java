package net.trique.wardentools.util;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.trique.wardentools.registry.ItemRegistry;
import net.trique.wardentools.registry.PotionRegistry;

public class WTPotionRecipeHelper {
    public static void addPotionRecipes() {
        FabricBrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD,
                Ingredient.of(Items.ECHO_SHARD),
                PotionRegistry.SCULK_ADAPTION.value());
        FabricBrewingRecipeRegistry.registerPotionRecipe(PotionRegistry.SCULK_ADAPTION.value(),
                Ingredient.of(ItemRegistry.WARDEN_SOUL.get()),
                PotionRegistry.SCULK_SCOURGE.value());
        FabricBrewingRecipeRegistry.registerPotionRecipe(PotionRegistry.SCULK_ADAPTION.value(),
                Ingredient.of(ItemRegistry.WARDEN_TENDRIL.get()),
                PotionRegistry.WARDEN.value());
        FabricBrewingRecipeRegistry.registerPotionRecipe(PotionRegistry.SCULK_ADAPTION.value(),
                Ingredient.of(Items.REDSTONE),
                PotionRegistry.LONG_SCULK_ADAPTION.value());
        FabricBrewingRecipeRegistry.registerPotionRecipe(PotionRegistry.SCULK_SCOURGE.value(),
                Ingredient.of(Items.REDSTONE),
                PotionRegistry.LONG_SCULK_SCOURGE.value());
        FabricBrewingRecipeRegistry.registerPotionRecipe(PotionRegistry.SCULK_SCOURGE.value(),
                Ingredient.of(Items.GLOWSTONE_DUST),
                PotionRegistry.STRONG_SCULK_SCOURGE.value());
        FabricBrewingRecipeRegistry.registerPotionRecipe(PotionRegistry.WARDEN.value(),
                Ingredient.of(Items.REDSTONE),
                PotionRegistry.LONG_WARDEN.value());
        FabricBrewingRecipeRegistry.registerPotionRecipe(PotionRegistry.WARDEN.value(),
                Ingredient.of(Items.GLOWSTONE_DUST),
                PotionRegistry.STRONG_WARDEN.value());
    }
}
