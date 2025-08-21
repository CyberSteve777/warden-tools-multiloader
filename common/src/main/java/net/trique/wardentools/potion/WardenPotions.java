package net.trique.wardentools.potion;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.trique.wardentools.WardenTools;
import net.trique.wardentools.effect.WardenEffects;
import net.trique.wardentools.item.WardenItems;

public class WardenPotions {
    public static final Holder<Potion> SCULK_ADAPTION_POTION =
            Registry.registerForHolder(BuiltInRegistries.POTION, ResourceLocation.fromNamespaceAndPath(WardenTools.MOD_ID, "sculk_adaption"),
                    new Potion(
                            new MobEffectInstance(
                                    BuiltInRegistries.MOB_EFFECT.wrapAsHolder(WardenEffects.SCULK_ADAPTION.value()),
                                    2400,
                                    0
                            )
                    )
            );

    public static void registerWardenPotionRecipes() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> builder.addMix(
                Potions.AWKWARD,
                WardenItems.WARDEN_SOUL,
                BuiltInRegistries.POTION.wrapAsHolder(SCULK_ADAPTION_POTION.value())
        ));
    }
}