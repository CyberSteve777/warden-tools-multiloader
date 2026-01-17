package net.trique.wardentools.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.trique.wardentools.Constants;
import net.trique.wardentools.effect.*;
import net.trique.wardentools.registration.RegistrationProvider;
import net.trique.wardentools.registration.RegistryObject;

import java.util.function.Supplier;

public class EffectRegistry {
    protected static final RegistrationProvider<MobEffect> EFFECTS = RegistrationProvider.get(BuiltInRegistries.MOB_EFFECT, Constants.MOD_ID);

    public static Holder<MobEffect> SCULK_ADAPTION;
    public static Holder<MobEffect> WARDEN_CURSE;
    public static Holder<MobEffect> SCULK_SCOURGE;

    private static <T extends MobEffect> Holder<MobEffect> registerEffect(String name, Supplier<T> effectSupplier) {
        RegistryObject<MobEffect, T> effectObject = EFFECTS.register(name, effectSupplier);
        return effectObject.asHolder();
    }


    public static void init() {
        Constants.LOGGER.info("Registering Effects for {}...", Constants.MOD_NAME);
    }

    static {
        SCULK_ADAPTION = registerEffect("sculk_adaption", () -> new SculkAdaptionEffect(MobEffectCategory.BENEFICIAL, 0x009295));
        SCULK_SCOURGE = registerEffect("sculk_scourge", () -> new SculkScourgeEffect(MobEffectCategory.BENEFICIAL, 0x0A5060));
        WARDEN_CURSE = registerEffect("warden_curse", () -> new WardenCurseEffect(MobEffectCategory.NEUTRAL, 0x034150));
    }
}