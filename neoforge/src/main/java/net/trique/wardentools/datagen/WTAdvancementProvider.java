package net.trique.wardentools.datagen;

import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.trique.wardentools.registry.ItemRegistry;
import net.trique.wardentools.util.WTDamageTypeTags;
import net.trique.wardentools.util.WTItemTags;
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
                            getLoc("textures/gui/advancements/root.png"),
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("ancient_city", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.inStructure(registries.lookupOrThrow(Registries.STRUCTURE).getOrThrow(BuiltinStructures.ANCIENT_CITY))
                    ))
                    .rewards(AdvancementRewards.Builder.experience(50))
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
                    .rewards(AdvancementRewards.Builder.experience(50))
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
                    .rewards(AdvancementRewards.Builder.experience(100))
                    .save(saver, getLoc("kill_warden"), existingFileHelper);
            AdvancementHolder obtain_echo_weapon = Advancement.Builder.advancement()
                    .parent(obtain_echo_shard)
                    .display(
                            ItemRegistry.ECHO_STAFF.get(),
                            getTitleKey("obtain_echo_weapon"),
                            getDescriptionKey("obtain_echo_weapon"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_echo_weapon", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(WTItemTags.ECHO_WEAPON)))
                    .rewards(AdvancementRewards.Builder.loot(WTLootTables.ECHO_WEAPON_ADVANCEMENT_REWARD).addExperience(50))
                    .save(saver, getLoc("obtain_echo_weapon"), existingFileHelper);
            AdvancementHolder obtain_warden_mask = Advancement.Builder.advancement()
                    .parent(kill_warden)
                    .display(
                            ItemRegistry.WARDEN_MASK.get(),
                            getTitleKey("obtain_warden_mask"),
                            getDescriptionKey("obtain_warden_mask"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_warden_mask", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.WARDEN_MASK.get()))
                    .rewards(AdvancementRewards.Builder.experience(50))
                    .save(saver, getLoc("obtain_warden_mask"), existingFileHelper);

            AdvancementHolder like_a_warden = Advancement.Builder.advancement()
                    .parent(obtain_warden_mask)
                    .display(
                            ItemRegistry.WARDEN_CHESTPLATE.get(),
                            getTitleKey("like_a_warden"),
                            getDescriptionKey("like_a_warden"),
                            null,
                            AdvancementType.GOAL,
                            true,
                            true,
                            false
                    )
                    .addCriterion("used_echo_weapon", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntityWithDamage(
                            DamagePredicate.Builder.damageInstance()
                                    .type(
                                            DamageSourcePredicate.Builder.damageType()
                                                    .tag(TagPredicate.is(WTDamageTypeTags.SONIC_BOOM))
                                                    .direct(
                                                            EntityPredicate.Builder.entity()
                                                                    .of(EntityType.PLAYER)
                                                                    .equipment(
                                                                            EntityEquipmentPredicate.Builder.equipment()
                                                                                    .mainhand(ItemPredicate.Builder.item().of(WTItemTags.ECHO_WEAPON))
                                                                                    .head(ItemPredicate.Builder.item().of(ItemRegistry.WARDEN_MASK.get()))
                                                                                    .chest(ItemPredicate.Builder.item().of(ItemRegistry.WARDEN_CHESTPLATE.get()))
                                                                                    .legs(ItemPredicate.Builder.item().of(ItemRegistry.WARDEN_LEGGINGS.get()))
                                                                                    .feet(ItemPredicate.Builder.item().of(ItemRegistry.WARDEN_BOOTS.get()))
                                                                    )
                                                    )
                                    )
                    ))
                    .rewards(AdvancementRewards.Builder.experience(100))
                    .save(saver, getLoc("like_a_warden"), existingFileHelper);

            AdvancementHolder the_warden = Advancement.Builder.advancement()
                    .parent(like_a_warden)
                    .display(
                            Blocks.SCULK_SHRIEKER,
                            getTitleKey("the_warden"),
                            getDescriptionKey("the_warden"),
                            null,
                            AdvancementType.CHALLENGE,
                            true,
                            true,
                            false
                    )
                    .addCriterion("kill_warden", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(EntityType.WARDEN),
                        DamageSourcePredicate.Builder.damageType()
                                .tag(TagPredicate.is(WTDamageTypeTags.SONIC_BOOM))
                                .direct(
                                        EntityPredicate.Builder.entity()
                                                .of(EntityType.PLAYER)
                                                .equipment(
                                                        EntityEquipmentPredicate.Builder.equipment()
                                                                .mainhand(ItemPredicate.Builder.item().of(WTItemTags.ECHO_WEAPON))
                                                                .head(ItemPredicate.Builder.item().of(ItemRegistry.WARDEN_MASK.get()))
                                                                .chest(ItemPredicate.Builder.item().of(ItemRegistry.WARDEN_CHESTPLATE.get()))
                                                                .legs(ItemPredicate.Builder.item().of(ItemRegistry.WARDEN_LEGGINGS.get()))
                                                                .feet(ItemPredicate.Builder.item().of(ItemRegistry.WARDEN_BOOTS.get()))
                                                )
                                )

                    ))
                    .rewards(AdvancementRewards.Builder.experience(100))
                    .save(saver, getLoc("the_warden"), existingFileHelper);
        }

        private static Component getTitleKey(String key) {
            return Component.translatable(getTranslationKey("advancement." + key + ".title"));
        }

        private static Component getDescriptionKey(String key) {
            return Component.translatable(getTranslationKey("advancement." + key + ".description"));
        }
    }
}
