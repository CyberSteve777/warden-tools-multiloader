package net.trique.wardentools.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.trique.wardentools.Constants;
import net.trique.wardentools.registration.RegistrationProvider;
import net.trique.wardentools.registration.RegistryObject;

import java.util.function.Supplier;

public class PotionRegistry {

    protected static final RegistrationProvider<Potion> POTION_REGISTRATION_PROVIDER = RegistrationProvider.get(
            BuiltInRegistries.POTION, Constants.MOD_ID
    );
    public static final Holder<Potion> SCULK_ADAPTION = registerPotion("sculk_adaption", () -> new Potion(
            new MobEffectInstance(EffectRegistry.SCULK_ADAPTION, 1800)
    ));
    public static final Holder<Potion> LONG_SCULK_ADAPTION = registerPotion("long_sculk_adaption", () -> new Potion(
            new MobEffectInstance(EffectRegistry.SCULK_ADAPTION, 4800)
    ));
    public static final Holder<Potion> SCULK_SCOURGE = registerPotion("sculk_scourge", () -> new Potion(
            new MobEffectInstance(EffectRegistry.SCULK_SCOURGE, 1800)
    ));
    public static final Holder<Potion> LONG_SCULK_SCOURGE = registerPotion("long_sculk_scourge", () -> new Potion(
            new MobEffectInstance(EffectRegistry.SCULK_SCOURGE, 4800)
    ));
    public static final Holder<Potion> STRONG_SCULK_SCOURGE = registerPotion("strong_sculk_scourge", () -> new Potion(
            new MobEffectInstance(EffectRegistry.SCULK_SCOURGE, 1800, 3)
    ));
    public static final Holder<Potion> WARDEN = registerPotion("warden", () -> new Potion(
            new MobEffectInstance(EffectRegistry.WARDEN_CURSE, 1800)
    ));
    public static final Holder<Potion> LONG_WARDEN = registerPotion("long_warden", () -> new Potion(
            new MobEffectInstance(EffectRegistry.WARDEN_CURSE, 4800)
    ));
    public static final Holder<Potion> STRONG_WARDEN = registerPotion("strong_warden", () -> new Potion(
            new MobEffectInstance(EffectRegistry.WARDEN_CURSE, 1800, 3)
    ));

    public static void init() {
        Constants.LOGGER.info("Adding Warden Tools potions to the game...");
    }

    protected static Holder<Potion> registerPotion(String id, Supplier<Potion> supplier) {
        RegistryObject<Potion, Potion> potionRegistryObject = POTION_REGISTRATION_PROVIDER.register(id, supplier);
        return potionRegistryObject.asHolder();
    }
    
    public static Iterable<Holder<Potion>> getEntries() {
        return POTION_REGISTRATION_PROVIDER.getEntries().stream().map(RegistryObject::asHolder).toList();
    }
}