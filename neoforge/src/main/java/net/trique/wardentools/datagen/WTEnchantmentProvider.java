package net.trique.wardentools.datagen;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.trique.wardentools.Constants;
import net.trique.wardentools.util.WTEnchantments;
import net.trique.wardentools.util.WTItemTags;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class WTEnchantmentProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, WTEnchantmentProvider::bootstrap);

    public WTEnchantmentProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Constants.MOD_ID));
    }


    private static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> holdergetter = context.lookup(Registries.ITEM);
        context.register(WTEnchantments.ECHO_CONCENTRATION, Enchantment.enchantment(
                Enchantment.definition(holdergetter.getOrThrow(WTItemTags.SONIC_BOOM_ITEM_ENCHANTABLE),
                        1,
                        5,
                        Enchantment.dynamicCost(10, 10),
                        Enchantment.dynamicCost(25, 10),
                        8,
                        EquipmentSlotGroup.MAINHAND)
        ).build(WTEnchantments.ECHO_CONCENTRATION.location()));
        context.register(WTEnchantments.RESONATION, Enchantment.enchantment(
                Enchantment.definition(holdergetter.getOrThrow(WTItemTags.SONIC_BOOM_ITEM_ENCHANTABLE),
                        1,
                        3,
                        Enchantment.dynamicCost(10, 10),
                        Enchantment.dynamicCost(25, 10),
                        8,
                        EquipmentSlotGroup.MAINHAND)
        ).build(WTEnchantments.RESONATION.location()));
    }

    @Override
    public String getName() {
        return "WTEnchantments";
    }
}
