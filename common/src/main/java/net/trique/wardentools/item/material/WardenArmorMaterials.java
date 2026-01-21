package net.trique.wardentools.item.material;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.Util;
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

public enum WardenArmorMaterials implements ArmorMaterial {
    ;
    private static final SoundEvent SONIC_BOOM_SOUND = SoundEvents.WARDEN_SONIC_BOOM;

    public static void init() {
        Constants.LOGGER.info("Registering armor materials for Warden Tools...");
    }

    private static Holder<ArmorMaterial> registerMaterial(String id, Map<Type, Integer> defensePoints, int enchantability, SoundEvent equipSound, Supplier<Ingredient> repairIngredientSupplier, float toughness, float knockbackResistance) {
        return new ArmorMaterial(defensePoints, enchantability, equipSound, repairIngredientSupplier, toughness, knockbackResistance);
    }

    public static final Holder<ArmorMaterial> SCULKIFIED = registerMaterial("sculkified", Util.make(new EnumMap<>(Type.class), map -> {
                map.put(Type.BOOTS, 3);
                map.put(Type.LEGGINGS, 6);
                map.put(Type.CHESTPLATE, 8);
                map.put(Type.HELMET, 3);
            }), 18, SoundEvents.ARMOR_EQUIP_DIAMOND,
            () -> Ingredient.of(ItemRegistry.ECHO_INGOT.get()), 2.5f, 0.05f, false);

    public static final Holder<ArmorMaterial> WARDEN = registerMaterial("warden", Util.make(new EnumMap<>(Type.class), map -> {
                map.put(Type.BOOTS, 5);
                map.put(Type.LEGGINGS, 8);
                map.put(Type.CHESTPLATE, 10);
                map.put(Type.HELMET, 5);
            }), 21, SONIC_BOOM_SOUND,
            () -> Ingredient.of(ItemRegistry.WARDEN_INGOT.get()), 5.0f, 0.15f, false);

    @Override
    public int getDurabilityForType(Type type) {
        return 0;
    }

    @Override
    public int getDefenseForType(Type type) {
        return 0;
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public SoundEvent getEquipSound() {
        return null;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public float getToughness() {
        return 0;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}