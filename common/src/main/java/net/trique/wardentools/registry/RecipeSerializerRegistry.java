package net.trique.wardentools.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.trique.wardentools.Constants;
import net.trique.wardentools.registration.RegistrationProvider;
import net.trique.wardentools.registration.RegistryObject;
import net.trique.wardentools.util.WardenMaskRecipe;

public class RecipeSerializerRegistry {
    private static final RegistrationProvider<RecipeSerializer<?>> RECIPE_SERIALIZER_PROVIDER = RegistrationProvider.get(
            BuiltInRegistries.RECIPE_SERIALIZER, Constants.MOD_ID
    );

    public static final RegistryObject<RecipeSerializer<?>, WardenMaskRecipe.Serializer> WARDEN_MASK_RECIPE_SERIALIZER =
            RECIPE_SERIALIZER_PROVIDER.register("warden_mask_shaped", WardenMaskRecipe.Serializer::new);

    public static void init() {
        Constants.LOGGER.info("Registering Warden Tools recipe serializers...");
    }
}
