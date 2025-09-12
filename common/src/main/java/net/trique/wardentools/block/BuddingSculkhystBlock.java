package net.trique.wardentools.block;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.trique.wardentools.registry.BlockRegistry;

public class BuddingSculkhystBlock extends AmethystBlock {
    public static final int GROW_CHANCE = 5;
    private static final Direction[] DIRECTIONS = Direction.values();

    public BuddingSculkhystBlock(Properties settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextInt(GROW_CHANCE) != 0) {
            return;
        }
        Direction direction = UPDATE_SHAPE_ORDER[random.nextInt(UPDATE_SHAPE_ORDER.length)];
        BlockPos blockPos = pos.relative(direction);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = null;
        if (BuddingSculkhystBlock.canGrowIn(blockState)) {
            block = BlockRegistry.SMALL_SCULKHYST_BUD.get();
        } else if (blockState.is(BlockRegistry.SMALL_SCULKHYST_BUD.get()) && blockState.getValue(AmethystClusterBlock.FACING) == direction) {
            block = BlockRegistry.MEDIUM_SCULKHYST_BUD.get();
        } else if (blockState.is(BlockRegistry.MEDIUM_SCULKHYST_BUD.get()) && blockState.getValue(AmethystClusterBlock.FACING) == direction) {
            block = BlockRegistry.LARGE_SCULKHYST_BUD.get();
        } else if (blockState.is(BlockRegistry.LARGE_SCULKHYST_BUD.get()) && blockState.getValue(AmethystClusterBlock.FACING) == direction) {
            block = BlockRegistry.SCULKHYST_CLUSTER.get();
        }
        if (block != null) {
            BlockState blockState2 = block.defaultBlockState().setValue(AmethystClusterBlock.FACING, direction).setValue(AmethystClusterBlock.WATERLOGGED, blockState.getFluidState().getType() == Fluids.WATER);
            world.setBlockAndUpdate(blockPos, blockState2);
        }
    }

    public static boolean canGrowIn(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER) && state.getFluidState().getAmount() == 8;
    }
}