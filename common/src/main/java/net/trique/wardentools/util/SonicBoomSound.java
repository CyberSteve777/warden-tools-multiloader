package net.trique.wardentools.util;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.trique.wardentools.WardenTools;

public class SonicBoomSound {
    public static Holder<SoundEvent> SONIC_BOOM_SOUND;

    public static void SonicBoomSound(){
        SONIC_BOOM_SOUND = Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, ResourceLocation.fromNamespaceAndPath(WardenTools.MOD_ID,"wt_sonic_boom"),
                SoundEvent.createVariableRangeEvent(SoundEvents.WARDEN_SONIC_BOOM.getLocation()));
    }
}
