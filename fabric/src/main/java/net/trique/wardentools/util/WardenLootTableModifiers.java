package net.trique.wardentools.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class WardenLootTableModifiers {
    private static final ResourceLocation ANCIENT_CITY_ID = BuiltInLootTables.ANCIENT_CITY.location();
    private static final ResourceLocation SCULK_SHRIEKER_ID = Blocks.SCULK_SHRIEKER.getLootTable().location();
    private static final ResourceLocation SCULK_CATALYST_ID = Blocks.SCULK_CATALYST.getLootTable().location();

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if(ANCIENT_CITY_ID.equals(key.location())) {
                LootPool.Builder TemplatePoolBuilder = LootPool.lootPool();
                TemplatePoolBuilder
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(WardenItems.WARDEN_UPGRADE_SMITHING_TEMPLATE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f)))
                                .when(LootItemRandomChanceCondition.randomChance(0.1f))
                        );
                LootPool.Builder EchoShardPoolBuilder = LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.ECHO_SHARD)
                                .when(LootItemRandomChanceCondition.randomChance(1f))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0f))))
                        .setRolls(ConstantValue.exactly(1));
                LootPool.Builder EchoApplePoolBuilder = LootPool.lootPool()
                        .add(LootItem.lootTableItem(WardenItems.ECHO_APPLE)
                                .when(LootItemRandomChanceCondition.randomChance(0.75f))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .setRolls(ConstantValue.exactly(1));
                LootPool.Builder SculkShellPoolBuilder = LootPool.lootPool()
                        .add(LootItem.lootTableItem(WardenItems.SCULK_SHELL)
                                .when(LootItemRandomChanceCondition.randomChance(0.25f))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f))))
                        .setRolls(ConstantValue.exactly(1));
                tableBuilder.withPool(TemplatePoolBuilder);
                tableBuilder.withPool(EchoShardPoolBuilder);
                tableBuilder.withPool(EchoApplePoolBuilder);
                tableBuilder.withPool(SculkShellPoolBuilder);
            }

            if(SCULK_SHRIEKER_ID.equals(key.location())) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .when(LootItemRandomChanceCondition.randomChance(0.25f))
                        .add(LootItem.lootTableItem(WardenItems.SHRIEKER_FANG))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f)));
                tableBuilder.withPool(poolBuilder);
            }

            if(SCULK_CATALYST_ID.equals(key.location())) {
                LootPool.Builder WardenSoulPoolBuilder = LootPool.lootPool()
                        .when(LootItemRandomChanceCondition.randomChance(0.25f))
                        .add(LootItem.lootTableItem(WardenItems.WARDEN_SOUL))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f)));
                tableBuilder.withPool(WardenSoulPoolBuilder);
            }
        });
    }
}