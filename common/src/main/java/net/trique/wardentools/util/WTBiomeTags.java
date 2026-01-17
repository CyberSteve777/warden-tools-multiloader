package net.trique.wardentools.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import static net.trique.wardentools.util.ModHelper.getLoc;

public class WTBiomeTags {
    public static final TagKey<Biome> SCULKHYST_GEODE_CAN_GENERATE_IN =
            create("sculkhyst_geode_can_generate_in");

    public static final TagKey<Biome> WARDEN_CURSE_RECEIVE_BONUS_IN =
            create("warden_curse_receive_bonus_in");

    private static TagKey<Biome> create(String id) {
        return TagKey.create(Registries.BIOME, getLoc(id));
    }
}
