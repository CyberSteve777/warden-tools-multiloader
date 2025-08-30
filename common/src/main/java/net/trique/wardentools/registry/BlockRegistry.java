package net.trique.wardentools.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.trique.wardentools.Constants;
import net.trique.wardentools.block.BuddingSculkhystBlock;
import net.trique.wardentools.block.SculkhystBlock;
import net.trique.wardentools.registration.RegistrationProvider;
import net.trique.wardentools.registration.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockRegistry {

    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, Constants.MOD_ID);

    public static final RegistryObject<Block, SculkhystBlock> SCULKHYST_BLOCK = registerBlock("sculkhyst_block", () ->
            new SculkhystBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(1.5f).sound(SoundType.SCULK), UniformInt.of(8, 16)));

    public static final RegistryObject<Block, BuddingSculkhystBlock> BUDDING_SCULKHYST = registerBlock("budding_sculkhyst", () ->
            new BuddingSculkhystBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).randomTicks().strength(1.5f).sound(SoundType.SCULK).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block, AmethystClusterBlock> SCULKHYST_CLUSTER = registerBlock("sculkhyst_cluster", () ->
            new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).forceSolidOn().noOcclusion().randomTicks().sound(SoundType.SCULK).strength(1.5f).lightLevel(state -> 4).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block, AmethystClusterBlock> LARGE_SCULKHYST_BUD = registerBlock("large_sculkhyst_bud", () ->
            new AmethystClusterBlock(5, 3, BlockBehaviour.Properties.ofFullCopy(SCULKHYST_CLUSTER.get()).sound(SoundType.SCULK).forceSolidOn().lightLevel(state -> 3).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block, AmethystClusterBlock> MEDIUM_SCULKHYST_BUD = registerBlock("medium_sculkhyst_bud", () ->
            new AmethystClusterBlock(4, 3, BlockBehaviour.Properties.ofFullCopy(SCULKHYST_CLUSTER.get()).sound(SoundType.SCULK).forceSolidOn().lightLevel(state -> 2).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block, AmethystClusterBlock> SMALL_SCULKHYST_BUD = registerBlock("small_sculkhyst_bud", () ->
            new AmethystClusterBlock(3, 4, BlockBehaviour.Properties.ofFullCopy(SCULKHYST_CLUSTER.get()).sound(SoundType.SCULK).forceSolidOn().lightLevel(state -> 1).pushReaction(PushReaction.DESTROY)));



    public static void init() {
        Constants.LOGGER.info("Registering blocks for {}", Constants.MOD_NAME);
    }


    protected static <T extends Block> RegistryObject<Block, T> registerBlock(String name, Supplier<T> block) {
        return registerBlock(name, block, b -> () -> new BlockItem(b.get(), ItemRegistry.getItemProperties()));
    }

    protected static <T extends Block> RegistryObject<Block, T> registerBlock(String name, Supplier<T> block, Function<RegistryObject<Block, T>, Supplier<? extends BlockItem>> item) {
        var reg = BLOCKS.register(name, block);
        ItemRegistry.ITEMS.register(name, () -> item.apply(reg).get());
        return reg;
    }
}
