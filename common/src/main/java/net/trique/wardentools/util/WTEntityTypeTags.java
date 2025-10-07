package net.trique.wardentools.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import static net.trique.wardentools.util.ModHelper.getLoc;

public class WTEntityTypeTags {
    public static final TagKey<EntityType<?>> SCULK_ADAPTATION_DEALS_EXTRA_DAMAGE_TO =
            create("sculk_adaptation_deals_damage_to");


    private static TagKey<EntityType<?>> create(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, getLoc(id));
    }

}
