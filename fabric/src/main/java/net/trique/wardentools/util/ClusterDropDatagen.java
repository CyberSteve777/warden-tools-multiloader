package net.trique.wardentools.util;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
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
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.trique.wardentools.data.WardenItemTagProvider;

import java.util.concurrent.CompletableFuture;

public class ClusterDropDatagen extends FabricBlockLootTableProvider {


    public ClusterDropDatagen(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public LootTable.Builder createClusterDrops(Block block, Item item) {
        HolderLookup.RegistryLookup<Enchantment> impl = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(block, LootItem.lootTableItem(item)
                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2)))
                .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(WardenItemTagProvider.SCULKHYST_CLUSTER_MAX_HARVESTABLES) ))
                .apply(ApplyBonusCount.addOreBonusCount(impl.getOrThrow(Enchantments.FORTUNE)))
                .otherwise(applyExplosionDecay(block,LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0,1)))))
        );
    }
    @Override
    public void generate() {
        add(WardenBlocks.SCULKHYST_CLUSTER,createClusterDrops(WardenBlocks.SCULKHYST_CLUSTER, Items.ECHO_SHARD));
    }
}
