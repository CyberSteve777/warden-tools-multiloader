package net.trique.wardentools.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.trique.wardentools.Constants;
import net.trique.wardentools.loot.AddItemModiferWithRandomAmount;
import net.trique.wardentools.loot.AddItemModifier;
import net.trique.wardentools.loot.AddItemToShriekerLootModifier;
import net.trique.wardentools.loot.AddItemToWardenLootModifier;

import java.util.concurrent.CompletableFuture;

import static net.trique.wardentools.registry.ItemRegistry.*;

public class WTGlobalLootModifierProvider extends GlobalLootModifierProvider {

    private static final ResourceLocation ANCIENT_CITY_LOOT_LOCATION = BuiltInLootTables.ANCIENT_CITY.location();

    public WTGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Constants.MOD_ID);
    }

    @Override
    protected void start() {
        add("add_warden_upgrade_smithing_template", new AddItemModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(ANCIENT_CITY_LOOT_LOCATION).build(),
                LootItemRandomChanceCondition.randomChance(0.1f).build()
        }, WARDEN_UPGRADE_SMITHING_TEMPLATE.get()));
        add("add_sculk_shell", new AddItemModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(ANCIENT_CITY_LOOT_LOCATION).build(),
                LootItemRandomChanceCondition.randomChance(0.25f).build()
        }, SCULK_SHELL.get()));
        add("add_echo_apple", new AddItemModiferWithRandomAmount(new LootItemCondition[]{
                LootTableIdCondition.builder(ANCIENT_CITY_LOOT_LOCATION).build(),
                LootItemRandomChanceCondition.randomChance(0.5f).build()
        }, ECHO_APPLE.get(), 1, 3));
        add("add_extra_echo_shards", new AddItemModiferWithRandomAmount(new LootItemCondition[]{
                LootTableIdCondition.builder(ANCIENT_CITY_LOOT_LOCATION).build(),
                LootItemRandomChanceCondition.randomChance(0.5f).build()
        }, Items.ECHO_SHARD, 1, 3));
        add("add_warden_soul_to_warden_loot", new AddItemToWardenLootModifier(new LootItemCondition[0],
                WARDEN_SOUL.get(), 0.3f, 0.1f, 1, 2));
        add("add_warden_tendril_to_warden_loot", new AddItemToWardenLootModifier(new LootItemCondition[0],
                WARDEN_TENDRIL.get(), 0.4f, 0.1f, 1, 2));
        add("add_warden_soul_to_shrieker_loot", new AddItemToShriekerLootModifier(new LootItemCondition[0],
                WARDEN_SOUL.get(), 0.05f, 0.05f, 1, 2));
        add("add_shrieker_fang_to_shrieker_loot", new AddItemToShriekerLootModifier(new LootItemCondition[0],
                SHRIEKER_FANG.get(), 0.3f, 0.1f, 1, 4));
    }
}
