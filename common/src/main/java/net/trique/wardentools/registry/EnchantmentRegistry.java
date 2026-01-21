package net.trique.wardentools.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.trique.wardentools.Constants;
import net.trique.wardentools.enchantment.PropagationEnchantment;
import net.trique.wardentools.enchantment.ResonanceEnchantment;
import net.trique.wardentools.enchantment.SonicBoostEnchantment;
import net.trique.wardentools.enchantment.SonicPunchEnchantment;
import net.trique.wardentools.registration.RegistrationProvider;
import net.trique.wardentools.registration.RegistryObject;


public class EnchantmentRegistry {
    private static final RegistrationProvider<Enchantment> ENCHANTMENT_PROVIDER = RegistrationProvider.get(
            BuiltInRegistries.ENCHANTMENT, Constants.MOD_ID
    );

    public static final RegistryObject<SonicBoostEnchantment> SONIC_BOOST = ENCHANTMENT_PROVIDER.register("sonic_boost",
            () -> new SonicBoostEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));

    public static final RegistryObject<SonicPunchEnchantment> SONIC_PUNCH = ENCHANTMENT_PROVIDER.register("sonic_punch",
            () -> new SonicPunchEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));

    public static final RegistryObject<PropagationEnchantment> PROPAGATION = ENCHANTMENT_PROVIDER.register("propagation",
            () -> new PropagationEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));

    public static final RegistryObject<ResonanceEnchantment> RESONANCE = ENCHANTMENT_PROVIDER.register("resonance",
            () -> new ResonanceEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));


    public static void init() {
        Constants.LOGGER.info("Registering warden enchantments...");
    }
}
