package net.trique.wardentools.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.trique.wardentools.registry.ItemRegistry;

public class WTLootTableModifiers {
    private static final ResourceLocation ANCIENT_CITY_LOOT_LOCATION = BuiltInLootTables.ANCIENT_CITY.location();
    private static final ResourceLocation SCULK_SHRIEKER_LOOT_LOCATION = Blocks.SCULK_SHRIEKER.getLootTable().location();
    private static final ResourceLocation WARDEN_LOOT_LOCATION = EntityType.WARDEN.getDefaultLootTable().location();

    public static void modifyLootTables() {
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

    private static LootItemCondition.Builder getFortuneConditionBuilder(HolderLookup.Provider registries, float base, float perLevelAfterFirst) {
        HolderLookup.RegistryLookup<Enchantment> registryLookup = registries.lookupOrThrow(Registries.ENCHANTMENT);
        return () -> new LootItemRandomChanceWithEnchantedBonusCondition(base, new LevelBasedValue.Linear(base + perLevelAfterFirst, perLevelAfterFirst), registryLookup.getOrThrow(Enchantments.FORTUNE));
    }
}