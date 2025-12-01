package net.trique.wardentools.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.trique.wardentools.Constants;

public class WTEnchantments {
    public static final ResourceKey<Enchantment> SONIC_BOOST = key("sonic_boost");
    public static final ResourceKey<Enchantment> SONIC_PUNCH = key("sonic_punch");
    public static final ResourceKey<Enchantment> PROPAGATION = key("propagation");
    public static final ResourceKey<Enchantment> RESONANCE = key("resonance");

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
    }
}
