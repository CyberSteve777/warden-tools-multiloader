package net.trique.wardentools.loot;

import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.trique.wardentools.Constants;

import java.util.function.Supplier;

public class ModLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Constants.MOD_ID);

    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> ADD_ITEM =
            LOOT_MODIFIER_SERIALIZERS.register("add_item", () -> AddItemModifier.CODEC);

    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> ADD_ITEM_WITH_RANDOM_AMOUNT =
            LOOT_MODIFIER_SERIALIZERS.register("add_item_with_random_amount",
                    () -> AddItemModiferWithRandomAmount.CODEC);

    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> ADD_ITEM_TO_WARDEN_LOOT_MODIFIER =
            LOOT_MODIFIER_SERIALIZERS.register("add_item_to_warden_loot_modifier", () -> AddItemToWardenLootModifier.CODEC);
    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> ADD_ITEM_TO_SHRIEKER_LOOT_MODIFIER =
            LOOT_MODIFIER_SERIALIZERS.register("add_item_to_shrieker_loot_modifier", () -> AddItemToShriekerLootModifier.CODEC);

    public static void register(IEventBus eventBus) {
        LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }
}