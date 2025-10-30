package net.trique.wardentools.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.trique.wardentools.util.WTLootTables;

import java.util.function.BiConsumer;

public class WTAdvancementLootTableSubProvider implements LootTableSubProvider {
    private final HolderLookup.Provider registries;

    public WTAdvancementLootTableSubProvider(HolderLookup.Provider provider) {
        registries = provider;
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
        biConsumer.accept(WTLootTables.ECHO_WEAPON_ADVANCEMENT_REWARD, LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.ECHO_SHARD)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(5f))))));
    }
}
