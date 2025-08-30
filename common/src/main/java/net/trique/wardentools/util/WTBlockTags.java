package net.trique.wardentools.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static net.trique.wardentools.util.ModHelper.getLoc;

public class WTBlockTags {
    public static final TagKey<Block> INCORRECT_FOR_SCULKHYST_TOOLS =
            create("incorrect_for_sculkhyst_tools");
    public static final TagKey<Block> INCORRECT_FOR_WARDEN_TOOLS =
            create("incorrect_for_warden_tools");

    private static TagKey<Block> create(String id) {
        return TagKey.create(Registries.BLOCK, getLoc(id));
    }
}
