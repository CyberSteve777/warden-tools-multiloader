package net.trique.wardentools.util;

import net.minecraft.resources.ResourceLocation;
import net.trique.wardentools.Constants;

public class ModHelper {
    public static ResourceLocation getLoc(String key) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, key);
    }
}
