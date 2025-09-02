package net.trique.wardentools.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.trique.wardentools.registry.BlockRegistry;
import net.trique.wardentools.util.WTItemTags;

import java.util.Set;

import static net.trique.wardentools.registry.BlockRegistry.*;

public class WTBlockLootTableProvider extends BlockLootSubProvider {
    protected WTBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        add(SCULKHYST_BLOCK.get(), createSilkTouchOnlyTable(SCULKHYST_BLOCK.get()));
        add(SMALL_SCULKHYST_BUD.get(), createSilkTouchOnlyTable(SMALL_SCULKHYST_BUD.get()));
        add(MEDIUM_SCULKHYST_BUD.get(), createSilkTouchOnlyTable(MEDIUM_SCULKHYST_BUD.get()));
        add(LARGE_SCULKHYST_BUD.get(), createSilkTouchOnlyTable(SMALL_SCULKHYST_BUD.get()));
        add(BUDDING_SCULKHYST.get(), noDrop());
        add(SCULKHYST_CLUSTER.get(), createSculkhystClusterDrops(SCULKHYST_CLUSTER.get(), Items.ECHO_SHARD));

    }


    public LootTable.Builder createSculkhystClusterDrops(Block block, Item item) {
        HolderLookup.RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(block, LootItem.lootTableItem(item)
                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0f)))
                .apply(ApplyBonusCount.addOreBonusCount(registryLookup.getOrThrow(Enchantments.FORTUNE)))
                .when(MatchTool.toolMatches(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(WTItemTags.SCULKHYST_CLUSTER_MAX_HARVESTABLES)))
                .otherwise(applyExplosionDecay(block, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0f)))))
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlockRegistry.getModBlocks();
    }
}
