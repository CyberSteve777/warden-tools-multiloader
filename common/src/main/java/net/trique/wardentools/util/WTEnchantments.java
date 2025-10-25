package net.trique.wardentools.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.trique.wardentools.Constants;

public class WTEnchantments {
    public static final ResourceKey<Enchantment> ECHO_CONCENTRATION = key("echo_concentration");
    public static final ResourceKey<Enchantment> RESONATION = key("resonation");

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
    }
}
