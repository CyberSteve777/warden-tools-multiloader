package net.trique.wardentools.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.trique.wardentools.Constants;
import net.trique.wardentools.util.WTEntityTypeTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class WTEntityTypeTagProvider extends EntityTypeTagsProvider {
    public WTEntityTypeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(WTEntityTypeTags.SCULK_BLESS_DEALS_EXTRA_DAMAGE_TO)
                .add(EntityType.WARDEN);
        tag(WTEntityTypeTags.SCULK_BLESS_REDUCES_DAMAGE_FROM)
                .add(EntityType.WARDEN);
    }
}
