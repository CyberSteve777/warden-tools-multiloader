package net.trique.wardentools.util;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.trique.wardentools.registry.ItemRegistry;
import net.trique.wardentools.registry.PotionRegistry;

public class WTPotionRecipeHelper {
    public static void addPotionRecipes() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
                    builder.registerPotionRecipe(
                            Potions.AWKWARD,
                            Ingredient.of(Items.ECHO_SHARD),
                            PotionRegistry.SCULK_ADAPTION
                    );
                    builder.registerPotionRecipe(
                            Potions.AWKWARD,
                            Ingredient.of(ItemRegistry.WARDEN_SOUL.get()),
                            PotionRegistry.SCULK_BLESS
                    );
                    builder.registerPotionRecipe(
                            Potions.AWKWARD,
                            Ingredient.of(ItemRegistry.WARDEN_TENDRIL.get()),
                            PotionRegistry.WARDEN
                    );
                    builder.registerPotionRecipe(
                            PotionRegistry.SCULK_ADAPTION,
                            Ingredient.of(Items.REDSTONE),
                            PotionRegistry.LONG_SCULK_ADAPTION
                    );
                    builder.registerPotionRecipe(
                            PotionRegistry.SCULK_BLESS,
                            Ingredient.of(Items.REDSTONE),
                            PotionRegistry.LONG_SCULK_BLESS
                    );
                    builder.registerPotionRecipe(
                            PotionRegistry.SCULK_BLESS,
                            Ingredient.of(Items.GLOWSTONE_DUST),
                            PotionRegistry.STRONG_SCULK_BLESS
                    );
                    builder.registerPotionRecipe(
                            PotionRegistry.WARDEN,
                            Ingredient.of(Items.REDSTONE),
                            PotionRegistry.LONG_WARDEN
                    );
                    builder.registerPotionRecipe(
                            PotionRegistry.WARDEN,
                            Ingredient.of(Items.GLOWSTONE_DUST),
                            PotionRegistry.STRONG_WARDEN
                    );
                }
        );
    }
}
