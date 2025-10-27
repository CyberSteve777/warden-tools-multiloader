package net.trique.wardentools.datagen;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.trique.wardentools.Constants;
import net.trique.wardentools.registration.RegistryObject;


import static net.trique.wardentools.registry.BlockRegistry.*;

public class WTBlockStateProvider extends BlockStateProvider {
    public WTBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(SCULKHYST_BLOCK.get(), cubeAll(BUDDING_SCULKHYST.get()));
        simpleBlockWithItem(BUDDING_SCULKHYST.get(), cubeAll(BUDDING_SCULKHYST.get()));
//        simpleBlockWithItem(ROSE_GOLD_BLOCK.get(), cubeAll(ROSE_GOLD_BLOCK.get()));
//        simpleBlockWithItem(ECHO_BLOCK.get(), cubeAll(ECHO_BLOCK.get()));
//        simpleBlockWithItem(WARDEN_BLOCK.get(), cubeAll(WARDEN_BLOCK.get()));
//        simpleBlockWithItem(BLOCK_OF_AMETHYST_INGOTS.get(), cubeAll(BLOCK_OF_AMETHYST_INGOTS.get()));
        simpleClusterBlockWithItem(SMALL_SCULKHYST_BUD);
        simpleClusterBlockWithItem(MEDIUM_SCULKHYST_BUD);
        simpleClusterBlockWithItem(LARGE_SCULKHYST_BUD);
        simpleClusterBlockWithItem(SCULKHYST_CLUSTER);
    }


    protected <T extends Block> void simpleClusterBlockWithItem(RegistryObject<Block, T> blockRegistryObject) {
        Block block = blockRegistryObject.get();
        ResourceLocation blockId = blockRegistryObject.getId();
        String pathname = blockId.getPath();

        ModelFile model = models().withExistingParent(pathname, mcLoc("block/cross")).renderType("cutout")
                .texture("cross", blockTexture(block));
        // Generate blockstate with variants for each facing direction
        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction facing = state.getValue(BlockStateProperties.FACING);
                    return ConfiguredModel.builder()
                            .modelFile(model)
                            .rotationX(facing == Direction.DOWN ? 180 : facing.getAxis().isHorizontal() ? 90 : 0)
                            .rotationY(facing.getAxis().isVertical() ? 0 : ((int) facing.toYRot() + 180) % 360)
                            .build();
                }, BlockStateProperties.WATERLOGGED);

        itemModels().getBuilder(pathname)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", blockTexture(block))
                .transforms()
                .transform(ItemDisplayContext.HEAD)
                .translation(0, 14, -5)
                .scale(1, 1, 1)
                .end();
    }

}
