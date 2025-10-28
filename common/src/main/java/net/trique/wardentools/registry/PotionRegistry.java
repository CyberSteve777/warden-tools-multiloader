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
        return POTION_REGISTRATION_PROVIDER.getEntries().stream().sorted(
                (potionRegistryObject1, potionRegistryObject2) -> {
                    String id1 = potionRegistryObject1.getId().getPath();
                    String id2 = potionRegistryObject2.getId().getPath();

                    // Extract base names by removing prefixes
                    String base1 = extractBaseName(id1);
                    String base2 = extractBaseName(id2);

                    // First, compare by base name lexicographically
                    int baseCompare = base1.compareTo(base2);
                    if (baseCompare != 0) {
                        return baseCompare;
                    }

                    // If same base name, compare by type order: base -> long -> strong
                    int typeOrder1 = getTypeOrder(id1);
                    int typeOrder2 = getTypeOrder(id2);
                    return Integer.compare(typeOrder1, typeOrder2);
                }
        ).map(RegistryObject::asHolder).toList();
    }

    private static String extractBaseName(String potionId) {
        if (potionId.startsWith("long_")) {
            return potionId.substring(5); // Remove "long_" prefix
        } else if (potionId.startsWith("strong_")) {
            return potionId.substring(7); // Remove "strong_" prefix
        }
        return potionId; // No prefix, return as is
    }

    /**
     * Returns the order priority for potion types:
     * 0 - base (no prefix)
     * 1 - long
     * 2 - strong
     */
    private static int getTypeOrder(String potionId) {
        if (potionId.startsWith("long_")) {
            return 1;
        } else if (potionId.startsWith("strong_")) {
            return 2;
        }
        return 0; // base potion
    }
}