package net.trique.wardentools.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.trique.wardentools.Constants;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class WTEnchantmentProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, WTEnchantmentProvider::bootstrap);

    public WTEnchantmentProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Constants.MOD_ID));
    }


    private static void bootstrap(BootstrapContext<Enchantment> context) {

    }

    @Override
    public String getName() {
        return "WTEnchantments";
    }
}
