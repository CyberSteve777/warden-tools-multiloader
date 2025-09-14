package net.trique.wardentools.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.GameEventTagsProvider;
import net.minecraft.tags.GameEventTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.trique.wardentools.Constants;
import net.trique.wardentools.registry.GameEventRegistry;
import net.trique.wardentools.util.WTGameEventTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class WTGameEventTagProvider extends GameEventTagsProvider {
    public WTGameEventTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(WTGameEventTags.VIBRA_SENSE_CAN_LISTEN)
                .addTag(GameEventTags.WARDEN_CAN_LISTEN)
                .add(GameEventRegistry.ENTITY_SOUND.getResourceKey());
    }
}
