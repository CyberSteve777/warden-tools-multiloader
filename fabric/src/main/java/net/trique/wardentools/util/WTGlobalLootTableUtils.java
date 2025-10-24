package net.trique.wardentools.util;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.trique.wardentools.LootTableDuck;
import net.trique.wardentools.loot.*;
import net.trique.wardentools.registry.ItemRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WTGlobalLootTableUtils {

    private static final ResourceLocation ANCIENT_CITY_LOOT_LOCATION = BuiltInLootTables.ANCIENT_CITY.location();
    private static final ResourceLocation SCULK_SHRIEKER_LOOT_LOCATION = Blocks.SCULK_SHRIEKER.getLootTable().location();
    private static final ResourceLocation WARDEN_LOOT_LOCATION = EntityType.WARDEN.getDefaultLootTable().location();

    public static final List<FabricLootModifier> MODIFIERS = new ArrayList<>();

    public static void addModifiers() {
        insertIDs();
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

    public static void modifyLoot(LootTable lootTable, LootContext context, ObjectArrayList<ItemStack> loot) {
        ResourceLocation id = ((LootTableDuck) lootTable).warden_tools$getId();
        MODIFIERS.forEach(fabricLootModifier -> fabricLootModifier.doApply(loot, context, id));
    }


    private static void insertIDs() {
        LootTableEvents.ALL_LOADED.register(((resourceManager, lootRegistry) -> {
            lootRegistry.forEach(lootTable -> {
                ((LootTableDuck) lootTable).warden_tools$setId(lootRegistry.getKey(lootTable));
            });
        }));
    }
}
