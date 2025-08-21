package net.trique.wardentools.item;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.trique.wardentools.WardenTools;
import net.trique.wardentools.util.SonicBoomSound;

public class WardenArmorMaterials{
    public static void initialize(){}

    public static Holder<ArmorMaterial> registerMaterial(String id, Map<Type, Integer> defensePoints, int enchantability, Holder<SoundEvent> equipSound, Supplier<Ingredient> repairIngredientSupplier, float toughness, float knockbackResistance, boolean dyeable){
        List<ArmorMaterial.Layer> layers = List.of(
                new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(WardenTools.MOD_ID,id),"",dyeable)
        );

        ArmorMaterial material = new ArmorMaterial(defensePoints, enchantability, equipSound, repairIngredientSupplier, layers, toughness, knockbackResistance);
        material = Registry.register(BuiltInRegistries.ARMOR_MATERIAL, ResourceLocation.fromNamespaceAndPath(WardenTools.MOD_ID, id),material);
        return Holder.direct(material);
    }

    public static final Holder<ArmorMaterial> SCULKIFIED = registerMaterial("sculkified",
            Map.of(
                    Type.BOOTS,4,
                    Type.LEGGINGS, 7,
                    Type.CHESTPLATE, 9,
                    Type.HELMET,4,
                    Type.BODY,12
            ),18, SonicBoomSound.SONIC_BOOM_SOUND,
            () -> Ingredient.of(WardenItems.ECHO_INGOT),4.0f,0.1f,false);

    public static final Holder<ArmorMaterial> WARDEN = registerMaterial("warden",
            Map.of(
                    Type.BOOTS,5,
                    Type.LEGGINGS, 8,
                    Type.CHESTPLATE, 10,
                    Type.HELMET,5,
                    Type.BODY,13
            ),21, SonicBoomSound.SONIC_BOOM_SOUND,
            () -> Ingredient.of(WardenItems.SCULK_SHELL),5.0f,0.1f,false);

}