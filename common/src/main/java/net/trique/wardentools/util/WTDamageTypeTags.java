package net.trique.wardentools.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

import static net.trique.wardentools.util.ModHelper.getLoc;

public class WTDamageTypeTags {
    public static final TagKey<DamageType> SONIC_BOOM =
            create("sonic_boom");


    private static TagKey<DamageType> create(String id) {
        return TagKey.create(Registries.DAMAGE_TYPE, getLoc(id));
    }

}
