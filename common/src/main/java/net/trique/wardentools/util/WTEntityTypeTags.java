package net.trique.wardentools.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import static net.trique.wardentools.util.ModHelper.getLoc;

public class WTEntityTypeTags {
    public static final TagKey<EntityType<?>> SCULK_SCOURGE_DEALS_EXTRA_DAMAGE_TO =
            create("sculk_scourge_deals_extra_damage_to");
    public static final TagKey<EntityType<?>> SCULK_SCOURGE_REDUCES_DAMAGE_FROM =
            create("sculk_scourge_reduces_damage_from");


    private static TagKey<EntityType<?>> create(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, getLoc(id));
    }

}
