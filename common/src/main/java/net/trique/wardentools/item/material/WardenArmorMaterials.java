package net.trique.wardentools.item.material;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.trique.wardentools.Constants;
import net.trique.wardentools.registration.RegistrationProvider;
import net.trique.wardentools.registration.RegistryObject;
import net.trique.wardentools.registry.ItemRegistry;

public class WardenArmorMaterials {
    protected static final RegistrationProvider<ArmorMaterial> ARMOR_MATERIALS = RegistrationProvider.get(Registries.ARMOR_MATERIAL, Constants.MOD_ID);
    private static final Holder<SoundEvent> SONIC_BOOM_SOUND = Holder.direct(SoundEvents.WARDEN_SONIC_BOOM);

    public static void init() {
        Constants.LOGGER.info("Registering armor materials for Warden Tools...");
    }

    private static Holder<ArmorMaterial> registerMaterial(String id, Map<Type, Integer> defensePoints, int enchantability, Holder<SoundEvent> equipSound, Supplier<Ingredient> repairIngredientSupplier, float toughness, float knockbackResistance, boolean dyeable) {
        List<ArmorMaterial.Layer> layers = List.of(
                new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, id), "", dyeable)
        );
        RegistryObject<ArmorMaterial, ArmorMaterial> materialRegistryObject = ARMOR_MATERIALS.register(id,
                () -> new ArmorMaterial(defensePoints, enchantability, equipSound, repairIngredientSupplier, layers, toughness, knockbackResistance));
        return materialRegistryObject.asHolder();
    }

    public static final Holder<ArmorMaterial> SCULKIFIED = registerMaterial("sculkified",
            Map.of(
                    Type.BOOTS, 4,
                    Type.LEGGINGS, 7,
                    Type.CHESTPLATE, 9,
                    Type.HELMET, 4,
                    Type.BODY, 12
            ), 18, SoundEvents.ARMOR_EQUIP_NETHERITE,
            () -> Ingredient.of(ItemRegistry.ECHO_INGOT.get()), 4.0f, 0.1f, false);

    public static final Holder<ArmorMaterial> WARDEN = registerMaterial("warden",
            Map.of(
                    Type.BOOTS, 5,
                    Type.LEGGINGS, 8,
                    Type.CHESTPLATE, 10,
                    Type.HELMET, 5,
                    Type.BODY, 13
            ), 21, SONIC_BOOM_SOUND,
            () -> Ingredient.of(ItemRegistry.SCULK_SHELL.get()), 5.0f, 0.1f, false);

}