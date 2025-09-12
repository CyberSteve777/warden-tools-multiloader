package net.trique.wardentools.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.trique.wardentools.registry.ItemRegistry;

public class WTLootTableModifiers {
    private static final ResourceLocation ANCIENT_CITY_LOOT_LOCATION = BuiltInLootTables.ANCIENT_CITY.location();
    private static final ResourceLocation SCULK_SHRIEKER_LOOT_LOCATION = Blocks.SCULK_SHRIEKER.getLootTable().location();
    private static final ResourceLocation WARDEN_LOOT_LOCATION = EntityType.WARDEN.getDefaultLootTable().location();


    public static void modifyLootTables() {
        // TODO: Implement loot table modification similar to NeoForge
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if(ANCIENT_CITY_LOOT_LOCATION.equals(key.location())) {
                LootPool.Builder TemplatePoolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ItemRegistry.WARDEN_UPGRADE_SMITHING_TEMPLATE.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f)))
                                .when(LootItemRandomChanceCondition.randomChance(0.1f))
                        );
                LootPool.Builder SculkShellPoolBuilder = LootPool.lootPool()
                        .add(LootItem.lootTableItem(ItemRegistry.SCULK_SHELL.get())
                                .when(LootItemRandomChanceCondition.randomChance(0.25f))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f))))
                        .setRolls(ConstantValue.exactly(1));
                LootPool.Builder EchoShardPoolBuilder = LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.ECHO_SHARD)
                                .when(LootItemRandomChanceCondition.randomChance(0.5f))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
                        .setRolls(ConstantValue.exactly(1));
                LootPool.Builder EchoApplePoolBuilder = LootPool.lootPool()
                        .add(LootItem.lootTableItem(ItemRegistry.ECHO_APPLE.get())
                                .when(LootItemRandomChanceCondition.randomChance(0.5f))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
                        .setRolls(ConstantValue.exactly(1));

                tableBuilder.withPool(TemplatePoolBuilder);
                tableBuilder.withPool(EchoShardPoolBuilder);
                tableBuilder.withPool(EchoApplePoolBuilder);
                tableBuilder.withPool(SculkShellPoolBuilder);
            }



            if (SCULK_SHRIEKER_LOOT_LOCATION.equals(key.location())) {
                Holder<Enchantment> FORTUNE = registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE);
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .add(LootItem.lootTableItem(ItemRegistry.SHRIEKER_FANG.get())
                        );
                tableBuilder.withPool(poolBuilder);
            }

            if(WARDEN_LOOT_LOCATION.equals(key.location())) {
                Holder<Enchantment> LOOTING = registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LOOTING);
                LootPool.Builder WardenSoulPoolBuilder = LootPool.lootPool()
                        .when(LootItemRandomChanceCondition.randomChance(0.25f))
                        .add(LootItem.lootTableItem(ItemRegistry.WARDEN_SOUL.get()))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f)));
                tableBuilder.withPool(WardenSoulPoolBuilder);
            }
        });
    }
}