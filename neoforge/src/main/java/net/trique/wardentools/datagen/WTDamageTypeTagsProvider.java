package net.trique.wardentools.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.trique.wardentools.Constants;
import net.trique.wardentools.util.WTDamageTypeTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class WTDamageTypeTagsProvider extends DamageTypeTagsProvider {

    public WTDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(WTDamageTypeTags.SONIC_BOOM).add(DamageTypes.SONIC_BOOM);
    }
}
