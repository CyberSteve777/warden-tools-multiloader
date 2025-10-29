package net.trique.wardentools.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.trique.wardentools.Constants;
import net.trique.wardentools.registry.EffectRegistry;
import net.trique.wardentools.registry.ItemRegistry;
import net.trique.wardentools.util.WTLootTables;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static net.trique.wardentools.util.ModHelper.getTranslationKey;
import static net.trique.wardentools.util.ModHelper.getLoc;

public class WTAdvancementProvider extends AdvancementProvider {
    public WTAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new WTAdvancementGenerator()));
    }


    private static final class WTAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            AdvancementHolder find_ancient_city = Advancement.Builder.advancement()
                    .display(
                            Blocks.REINFORCED_DEEPSLATE,
                            getTitleKey("find_ancient_city"),
                            getDescriptionKey("find_ancient_city"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("ancient_city", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.inStructure(registries.lookupOrThrow(Registries.STRUCTURE).getOrThrow(BuiltinStructures.ANCIENT_CITY))
                    ))
                    .save(saver, getLoc("find_ancient_city"), existingFileHelper);
            AdvancementHolder obtain_echo_shard = Advancement.Builder.advancement()
                    .parent(find_ancient_city)
                    .display(
                            Items.ECHO_SHARD,
                            getTitleKey("obtain_echo_shard"),
                            getDescriptionKey("obtain_echo_shard"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("echo_shard", InventoryChangeTrigger.TriggerInstance.hasItems(Items.ECHO_SHARD))
                    .save(saver, getLoc("obtain_echo_shard"), existingFileHelper);
            AdvancementHolder kill_warden = Advancement.Builder.advancement()
                    .parent(find_ancient_city)
                    .display(
                            ItemRegistry.WARDEN_SOUL.get(),
                            getTitleKey("kill_warden"),
                            getDescriptionKey("kill_warden"),
                            null,
                            AdvancementType.CHALLENGE,
                            true,
                            true,
                            false
                    )
                    .addCriterion("warden", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(EntityType.WARDEN)))
                    .rewards(AdvancementRewards.Builder.experience(75))
                    .save(saver, getLoc("kill_warden"), existingFileHelper);
            AdvancementHolder obtain_sonic_boom_weapon = Advancement.Builder.advancement()
                    .display(
                            ItemRegistry.ECHO_STAFF.get(),
                            getTitleKey("obtain_sonic_boom_weapon"),
                            getDescriptionKey("obtain_sonic_boom_weapon"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .rewards(AdvancementRewards.Builder.loot(WTLootTables.SONIC_BOOM_WEAPON_ADVANCEMENT_REWARD))
                    .save(saver, getLoc("obtain_sonic_boom_weapon"), existingFileHelper);
            AdvancementHolder like_a_warden = Advancement.Builder.advancement()
                    .display(
                            ItemRegistry.WARDEN_MASK.get(),
                            getTitleKey("like_a_warden"),
                            getDescriptionKey("like_a_warden"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_warden_curse", EffectsChangedTrigger.TriggerInstance.hasEffects(MobEffectsPredicate.Builder.effects().and(EffectRegistry.WARDEN_CURSE)))
//                    .addCriterion("used_sonic_boom_weapon", ItemUsedOnLocationTrigg)
                    .save(saver, getLoc("like_a_warden"), existingFileHelper);
        }

        private static Component getTitleKey(String key) {
            return Component.translatable(getTranslationKey("advancement." + key + ".title"));
        }

        private static Component getDescriptionKey(String key) {
            return Component.translatable(getTranslationKey("advancement." + key + ".description"));
        }
    }
}
