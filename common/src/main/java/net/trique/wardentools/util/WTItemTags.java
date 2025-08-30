package net.trique.wardentools.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static net.trique.wardentools.util.ModHelper.getLoc;

public class WTItemTags {
    public static final TagKey<Item> SCULKHYST_CLUSTER_MAX_HARVESTABLES =
            create("sculkhyst_cluster_max_harvestables");


    private static TagKey<Item> create(String id) {
        return TagKey.create(Registries.ITEM, getLoc(id));
    }
}
