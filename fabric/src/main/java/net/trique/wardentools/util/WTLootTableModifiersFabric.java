package net.trique.wardentools.util;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.trique.wardentools.LootTableDuck;
import net.trique.wardentools.loot.*;
import net.trique.wardentools.registry.ItemRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class WTLootTableModifiersFabric {
    private static final ResourceLocation ANCIENT_CITY_LOOT_LOCATION = BuiltInLootTables.ANCIENT_CITY.location();
    private static final ResourceLocation SCULK_SHRIEKER_LOOT_LOCATION = Blocks.SCULK_SHRIEKER.getLootTable().location();
    private static final ResourceLocation WARDEN_LOOT_LOCATION = EntityType.WARDEN.getDefaultLootTable().location();

    public static final List<FabricLootModifier> MODIFIERS = new ArrayList<>();

    //        add("add_warden_upgrade_smithing_template", new AddItemModifier(new LootItemCondition[]{
    //                LootTableIdCondition.builder(ANCIENT_CITY_LOOT_LOCATION).build(),
    //                LootItemRandomChanceCondition.randomChance(0.1f).build()
    //        }, WARDEN_UPGRADE_SMITHING_TEMPLATE.get()));
    //        add("add_sculk_shell", new AddItemModifier(new LootItemCondition[]{
    //                LootTableIdCondition.builder(ANCIENT_CITY_LOOT_LOCATION).build(),
    //                LootItemRandomChanceCondition.randomChance(0.25f).build()
    //        }, SCULK_SHELL.get()));
    //        add("add_echo_apple", new AddItemModifierWithRandomAmount(new LootItemCondition[]{
    //                LootTableIdCondition.builder(ANCIENT_CITY_LOOT_LOCATION).build(),
    //                LootItemRandomChanceCondition.randomChance(0.5f).build()
    //        }, ECHO_APPLE.get(), 1, 3));
    //        add("add_extra_echo_shards", new AddItemModifierWithRandomAmount(new LootItemCondition[]{
    //                LootTableIdCondition.builder(ANCIENT_CITY_LOOT_LOCATION).build(),
    //                LootItemRandomChanceCondition.randomChance(0.5f).build()
    //        }, Items.ECHO_SHARD, 1, 3));
    //        add("add_warden_soul_to_warden_loot", new AddItemToWardenLootModifier(new LootItemCondition[]{
    //                LootTableIdCondition.builder(WARDEN_LOOT_LOCATION).build()
    //        }, WARDEN_SOUL.get(), 0.3f, 0.1f, 1, 2));
    //        add("add_warden_soul_to_shrieker_loot", new AddItemToShriekerLootModifier(new LootItemCondition[]{
    //                LootTableIdCondition.builder(SCULK_SHRIEKER_LOOT_LOCATION).build()
    //        }, WARDEN_SOUL.get(), 0.05f, 0.05f, 1, 2));
    //        add("add_shrieker_fang_to_shrieker_loot", new AddItemToShriekerLootModifier(new LootItemCondition[]{
    //                LootTableIdCondition.builder(SCULK_SHRIEKER_LOOT_LOCATION).build()
    //        }, SHRIEKER_FANG.get(), 0.3f, 0.1f, 1, 4));

    public static void addModifiers() {
        MODIFIERS.add(new AddItemModifier(new LootItemCondition[]{LootItemRandomChanceCondition.randomChance(.1f).build()},
                Set.of(ANCIENT_CITY_LOOT_LOCATION), ItemRegistry.WARDEN_UPGRADE_SMITHING_TEMPLATE.get()));

        MODIFIERS.add(new AddItemModifier(new LootItemCondition[]{LootItemRandomChanceCondition.randomChance(.25f).build()},
                Set.of(ANCIENT_CITY_LOOT_LOCATION), ItemRegistry.SCULK_SHELL.get()));

        MODIFIERS.add(new AddItemModifierWithRandomAmount(new LootItemCondition[]{LootItemRandomChanceCondition.randomChance(.5f).build()},
                Set.of(ANCIENT_CITY_LOOT_LOCATION), ItemRegistry.ECHO_APPLE.get(), 1, 3));

        MODIFIERS.add(new AddItemModifierWithRandomAmount(new LootItemCondition[]{LootItemRandomChanceCondition.randomChance(.5f).build()},
                Set.of(ANCIENT_CITY_LOOT_LOCATION), Items.ECHO_SHARD, 1, 3));


        MODIFIERS.add(new AddItemToWardenLootModifier(new LootItemCondition[0],
                Set.of(WARDEN_LOOT_LOCATION), ItemRegistry.WARDEN_SOUL.get(), 0.3f, 0.1f, 1, 2));

        MODIFIERS.add(new AddItemToWardenLootModifier(new LootItemCondition[0],
                Set.of(WARDEN_LOOT_LOCATION), ItemRegistry.WARDEN_TENDRIL.get(), 0.4f, 0.1f, 1, 2));


        MODIFIERS.add(new AddItemToShriekerLootModifier(new LootItemCondition[0],
                Set.of(SCULK_SHRIEKER_LOOT_LOCATION), ItemRegistry.WARDEN_SOUL.get(), 0.05f, 0.05f, 1, 2));

        MODIFIERS.add(new AddItemToShriekerLootModifier(new LootItemCondition[0],
                Set.of(SCULK_SHRIEKER_LOOT_LOCATION), ItemRegistry.SHRIEKER_FANG.get(), 0.3f, 0.1f, 1, 4));
    }

    public static void modifyLootTables() {
        // TODO: Implement loot table modification similar to NeoForge
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (ANCIENT_CITY_LOOT_LOCATION.equals(key.location())) {
                LootPool.Builder templatePoolBuilder = LootPool.lootPool()
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                        .add(LootItem.lootTableItem(ItemRegistry.WARDEN_UPGRADE_SMITHING_TEMPLATE.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f))));
                LootPool.Builder sculkShellPoolBuilder = LootPool.lootPool()
                        .when(LootItemRandomChanceCondition.randomChance(0.25f))
                        .add(LootItem.lootTableItem(ItemRegistry.SCULK_SHELL.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f))));
                LootPool.Builder echoShardPoolBuilder = LootPool.lootPool()
                        .when(LootItemRandomChanceCondition.randomChance(0.5f))
                        .add(LootItem.lootTableItem(Items.ECHO_SHARD)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))));
                LootPool.Builder echoApplePoolBuilder = LootPool.lootPool()
                        .when(LootItemRandomChanceCondition.randomChance(0.5f))
                        .add(LootItem.lootTableItem(ItemRegistry.ECHO_APPLE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))));
                tableBuilder.withPool(templatePoolBuilder);
                tableBuilder.withPool(echoShardPoolBuilder);
                tableBuilder.withPool(echoApplePoolBuilder);
                tableBuilder.withPool(sculkShellPoolBuilder);
            }


            if (SCULK_SHRIEKER_LOOT_LOCATION.equals(key.location())) {
                LootPool.Builder shriekerFangPoolBuilder = LootPool.lootPool()
                        .when(getFortuneConditionBuilder(registries, 0.3f, 0.1f))
                        .add(LootItem.lootTableItem(ItemRegistry.SHRIEKER_FANG.get()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 4f)));
                LootPool.Builder wardenSoulPoolBuilder = LootPool.lootPool()
                        .when(getFortuneConditionBuilder(registries, 0.05f, 0.05f))
                        .add(LootItem.lootTableItem(ItemRegistry.WARDEN_SOUL.get()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 2f)));
                tableBuilder.withPool(shriekerFangPoolBuilder);
                tableBuilder.withPool(wardenSoulPoolBuilder);
            }

            if (WARDEN_LOOT_LOCATION.equals(key.location())) {
                LootPool.Builder wardenSoulPoolBuilder = LootPool.lootPool()
                        .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(registries, 0.3f, 0.1f))
                        .add(LootItem.lootTableItem(ItemRegistry.WARDEN_SOUL.get()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 2f)));
                LootPool.Builder wardenTendrilPoolBuilder = LootPool.lootPool()
                        .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(registries, 0.4f, 0.1f))
                        .add(LootItem.lootTableItem(ItemRegistry.WARDEN_TENDRIL.get()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 2f)));
                tableBuilder.withPool(wardenSoulPoolBuilder);
                tableBuilder.withPool(wardenTendrilPoolBuilder);
            }
        });
    }

    public static void modifyLoot(LootTable lootTable, LootContext context, ObjectArrayList<ItemStack> loot) {
        ResourceLocation id = ((LootTableDuck) lootTable).warden_tools$getId();
        MODIFIERS.forEach(fabricLootModifier -> fabricLootModifier.doApply(loot, context, id));
    }

    public static <T> void injectID(Optional<T> returnValue, ResourceLocation resourceLocation) {
        returnValue.ifPresent(value -> {
            if (value instanceof LootTable lootTable) {
                ((LootTableDuck) lootTable).warden_tools$setId(resourceLocation);
            }
        });
    }

    private static LootItemCondition.Builder getFortuneConditionBuilder(HolderLookup.Provider registries, float base, float perLevelAfterFirst) {
        HolderLookup.RegistryLookup<Enchantment> registryLookup = registries.lookupOrThrow(Registries.ENCHANTMENT);
        return () -> new LootItemRandomChanceWithEnchantedBonusCondition(base, new LevelBasedValue.Linear(base + perLevelAfterFirst, perLevelAfterFirst), registryLookup.getOrThrow(Enchantments.FORTUNE));
    }
}