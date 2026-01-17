package net.trique.wardentools.registry;

import me.cybersteve.equiplib.armorset.base.EffectArmorSet;
import net.trique.wardentools.Constants;
import net.trique.wardentools.effect_armor_set.*;


public class WTArmorSets {

    public static EffectArmorSet WARDEN_SET = new WardenSet();
    public static EffectArmorSet SCULKIFIED_SET = new SculkifiedSet();


    public static void init() {
        Constants.LOGGER.info("adding armor sets...");
    }
}
