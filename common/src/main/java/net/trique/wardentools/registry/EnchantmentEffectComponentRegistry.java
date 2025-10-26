package net.trique.wardentools.registry;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.trique.wardentools.Constants;
import net.trique.wardentools.registration.RegistrationProvider;
import net.trique.wardentools.registration.RegistryObject;

import java.util.List;
import java.util.function.UnaryOperator;

public class EnchantmentEffectComponentRegistry {
    private static final RegistrationProvider<DataComponentType<?>> ENCHANTMENT_EFFECT_COMPONENT_PROVIDER = RegistrationProvider.get(
            BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, Constants.MOD_ID
    );

    public static RegistryObject<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>>  INCREASE_RANGE =
            register("increase_value",
            (builder -> builder.persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_ITEM).listOf())));


    public static void init() {
        Constants.LOGGER.info("Registering warden enchantment effect components...");
    }

    protected static <T> RegistryObject<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> operator) {
        return ENCHANTMENT_EFFECT_COMPONENT_PROVIDER.register(name, () -> operator.apply(DataComponentType.builder()).cacheEncoding().build());
    }
}
