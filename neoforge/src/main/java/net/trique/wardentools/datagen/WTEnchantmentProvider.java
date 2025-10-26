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
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.MultiplyValue;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.trique.wardentools.Constants;
import net.trique.wardentools.registry.EnchantmentEffectComponentRegistry;
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
        context.register(WTEnchantments.SONIC_BOOST, Enchantment.enchantment(
                Enchantment.definition(holdergetter.getOrThrow(WTItemTags.SONIC_BOOM_ITEM_ENCHANTABLE),
                        1,
                        5,
                        Enchantment.dynamicCost(10, 10),
                        Enchantment.dynamicCost(25, 10),
                        8,
                        EquipmentSlotGroup.MAINHAND)
        ).withEffect(
                EnchantmentEffectComponents.DAMAGE, new MultiplyValue(LevelBasedValue.perLevel(1.05f, 0.05f))
        ).build(WTEnchantments.SONIC_BOOST.location()));
        context.register(WTEnchantments.PROPAGATION, Enchantment.enchantment(
                Enchantment.definition(holdergetter.getOrThrow(WTItemTags.SONIC_BOOM_ITEM_ENCHANTABLE),
                        1,
                        3,
                        Enchantment.dynamicCost(10, 10),
                        Enchantment.dynamicCost(25, 10),
                        8,
                        EquipmentSlotGroup.MAINHAND)
        ).withEffect(
                EnchantmentEffectComponentRegistry.INCREASE_RANGE,
                new AddValue(LevelBasedValue.perLevel(5f))
        ).build(WTEnchantments.PROPAGATION.location()));
    }

    @Override
    public String getName() {
        return "WTEnchantments";
    }
}
