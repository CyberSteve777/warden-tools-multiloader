package net.trique.wardentools.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.trique.wardentools.Constants;
import net.trique.wardentools.util.WTEnchantments;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class WTEnchantmentTagsProvider extends EnchantmentTagsProvider {
    public WTEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(EnchantmentTags.TREASURE)
                .add(WTEnchantments.PROPAGATION)
                .add(WTEnchantments.SONIC_BOOST);
        tag(Tags.Enchantments.WEAPON_DAMAGE_ENHANCEMENTS)
                .add(WTEnchantments.SONIC_BOOST);
    }
}
