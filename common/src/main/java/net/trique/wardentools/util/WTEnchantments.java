package net.trique.wardentools.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.trique.wardentools.Constants;

public enum WTEnchantments {
    SONIC_BOOST(getKey("sonic_boost"), 1, 5),
    SONIC_PUNCH(getKey("sonic_punch"), 1, 2),
    PROPAGATION(getKey("propagation"), 1, 3),
    RESONANCE(getKey("resonance"), 1, 3);

    public final ResourceKey<Enchantment> key;
    public final int minLevel;
    public final int maxLevel;


    WTEnchantments(final ResourceKey<Enchantment> key, int minLevel, int maxLevel) {
        this.key = key;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    private static ResourceKey<Enchantment> getKey(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
    }
}
