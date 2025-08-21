package net.trique.wardentools.block;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.trique.wardentools.WardenTools;

public class WardenBlocks {

    public static final Block SCULKHYST_BLOCK = registerBlock("sculkhyst_block",
            new SculkhystBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(1.5f).sound(SoundType.SCULK), UniformInt.of(8, 16)));

    public static final Block BUDDING_SCULKHYST = registerBlock("budding_sculkhyst",
            new BuddingSculkhystBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).randomTicks().strength(1.5f).sound(SoundType.SCULK).pushReaction(PushReaction.DESTROY)));

    public static final Block SCULKHYST_CLUSTER = registerBlock("sculkhyst_cluster",
            new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).forceSolidOn().noOcclusion().randomTicks().sound(SoundType.SCULK).strength(1.5f).lightLevel(state -> 4).pushReaction(PushReaction.DESTROY)));

    public static final Block LARGE_SCULKHYST_BUD = registerBlock("large_sculkhyst_bud",
            new AmethystClusterBlock(5, 3, BlockBehaviour.Properties.ofFullCopy(SCULKHYST_CLUSTER).sound(SoundType.SCULK).forceSolidOn().lightLevel(state -> 3).pushReaction(PushReaction.DESTROY)));

    public static final Block MEDIUM_SCULKHYST_BUD = registerBlock("medium_sculkhyst_bud",
            new AmethystClusterBlock(4, 3, BlockBehaviour.Properties.ofFullCopy(SCULKHYST_CLUSTER).sound(SoundType.SCULK).forceSolidOn().lightLevel(state -> 2).pushReaction(PushReaction.DESTROY)));

    public static final Block SMALL_SCULKHYST_BUD = registerBlock("small_sculkhyst_bud",
            new AmethystClusterBlock(3, 4, BlockBehaviour.Properties.ofFullCopy(SCULKHYST_CLUSTER).sound(SoundType.SCULK).forceSolidOn().lightLevel(state -> 1).pushReaction(PushReaction.DESTROY)));
    
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(WardenTools.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        Item item = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(WardenTools.MOD_ID, name),
                new BlockItem(block, new Item.Properties()));
        return item;
    }

    public static void registerWardenBlocks() {
        WardenTools.LOGGER.info("Registering Warden Blocks for " + WardenTools.MOD_ID);
    }
}