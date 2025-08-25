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
    public static final Holder<Potion> SCULK_ADAPTION_POTION = registerPotion("sculk_adaption", () -> new Potion(
            new MobEffectInstance(EffectRegistry.SCULK_ADAPTION, 2400, 0)
    ));

    public static void init() {
        Constants.LOGGER.info("Adding Warden Tools potions to the game...");
    }

    protected static Holder<Potion> registerPotion(String id, Supplier<Potion> supplier) {
        RegistryObject<Potion, Potion> potionRegistryObject = POTION_REGISTRATION_PROVIDER.register(id, supplier);
        return potionRegistryObject.asHolder();
    }
}