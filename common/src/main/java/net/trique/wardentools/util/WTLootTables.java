package net.trique.wardentools.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import static net.trique.wardentools.util.ModHelper.getLoc;

public class WTLootTables {
    public static final ResourceKey<LootTable> ECHO_WEAPON_ADVANCEMENT_REWARD =
            create("echo_weapon_advancement_reward");


    private static ResourceKey<LootTable> create(String id) {
        return ResourceKey.create(Registries.LOOT_TABLE, getLoc(id));
    }

}
