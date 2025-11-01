package net.trique.wardentools.datagen;

import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.trique.wardentools.advancements.criterion.AffectedEntitiesTrigger;
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
            AdvancementHolder obtain_echo_ingot = Advancement.Builder.advancement()
                    .parent(find_ancient_city)
                    .display(
                            ItemRegistry.ECHO_INGOT.get(),
                            getTitleKey("obtain_echo_ingot"),
                            getDescriptionKey("obtain_echo_ingot"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_echo_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ECHO_INGOT.get()))
                    .rewards(AdvancementRewards.Builder.experience(50))
                    .save(saver, getLoc("obtain_echo_ingot"), existingFileHelper);
            AdvancementHolder kill_warden = Advancement.Builder.advancement()
                    .parent(find_ancient_city)
                    .display(
                            ItemRegistry.WARDEN_TENDRIL.get(),
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
            AdvancementHolder obtain_echo_locator = Advancement.Builder.advancement()
                    .parent(obtain_echo_ingot)
                    .display(
                            ItemRegistry.ECHO_LOCATOR.get(),
                            getTitleKey("obtain_echo_locator"),
                            getDescriptionKey("obtain_echo_locator"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_echo_locator", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ECHO_LOCATOR.get()))
                    .rewards(AdvancementRewards.Builder.experience(50))
                    .save(saver, getLoc("obtain_echo_locator"), existingFileHelper);
            AdvancementHolder too_may_entities = Advancement.Builder.advancement()
                    .parent(obtain_echo_locator)
                    .display(
                            ItemRegistry.SCULK_ARROW.get(),
                            getTitleKey("too_may_entities"),
                            getDescriptionKey("too_may_entities"),
                            null,
                            AdvancementType.CHALLENGE,
                            true,
                            true,
                            false
                    )
                    .addCriterion("entity_count", AffectedEntitiesTrigger.TriggerInstance.minCount(ItemPredicate.Builder.item().of(ItemRegistry.SCULK_ARROW.get()), 15))
                    .rewards(AdvancementRewards.Builder.experience(50))
                    .save(saver, getLoc("too_may_entities"), existingFileHelper);
            AdvancementHolder obtain_echo_staff = Advancement.Builder.advancement()
                    .parent(obtain_echo_ingot)
                    .display(
                            ItemRegistry.ECHO_STAFF.get(),
                            getTitleKey("obtain_echo_staff"),
                            getDescriptionKey("obtain_echo_staff"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_echo_staff", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ECHO_STAFF.get()))
                    .rewards(AdvancementRewards.Builder.loot(WTLootTables.ECHO_WEAPON_ADVANCEMENT_REWARD).addExperience(50))
                    .save(saver, getLoc("obtain_echo_staff"), existingFileHelper);
            AdvancementHolder upgrade_echo_staff = Advancement.Builder.advancement()
                    .parent(obtain_echo_staff)
                    .display(
                            ItemRegistry.ROSE_GOLD_UPGRADED_ECHO_STAFF.get(),
                            getTitleKey("upgrade_echo_staff"),
                            getDescriptionKey("upgrade_echo_staff"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_upgraded_echo_staff", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(WTItemTags.ECHO_STAFF_UPGRADE)))
                    .rewards(AdvancementRewards.Builder.loot(WTLootTables.ECHO_WEAPON_ADVANCEMENT_REWARD).addExperience(50))
                    .save(saver, getLoc("upgrade_echo_staff"), existingFileHelper);
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
            AdvancementHolder obtain_sculk_shell = Advancement.Builder.advancement()
                    .parent(find_ancient_city)
                    .display(
                            ItemRegistry.SCULK_SHELL.get(),
                            getTitleKey("obtain_sculk_shell"),
                            getDescriptionKey("obtain_sculk_shell"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_sculk_shell", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.SCULK_SHELL.get()))
                    .rewards(AdvancementRewards.Builder.experience(50))
                    .save(saver, getLoc("obtain_sculk_shell"), existingFileHelper);
            AdvancementHolder obtain_warden_ingot = Advancement.Builder.advancement()
                    .parent(obtain_sculk_shell)
                    .display(
                            ItemRegistry.WARDEN_INGOT.get(),
                            getTitleKey("obtain_warden_ingot"),
                            getDescriptionKey("obtain_warden_ingot"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_warden_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.WARDEN_INGOT.get()))
                    .rewards(AdvancementRewards.Builder.experience(75))
                    .save(saver, getLoc("obtain_warden_ingot"), existingFileHelper);
            AdvancementHolder obtain_echo_shrieker = Advancement.Builder.advancement()
                    .parent(obtain_warden_ingot)
                    .display(
                            ItemRegistry.ECHO_SHRIEKER.get(),
                            getTitleKey("obtain_echo_shrieker"),
                            getDescriptionKey("obtain_echo_shrieker"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_echo_shrieker", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ECHO_SHRIEKER.get()))
                    .rewards(AdvancementRewards.Builder.experience(75))
                    .save(saver, getLoc("obtain_echo_shrieker"), existingFileHelper);
            AdvancementHolder obtain_warden_armor_piece = Advancement.Builder.advancement()
                    .parent(obtain_warden_ingot)
                    .display(
                            ItemRegistry.WARDEN_HELMET.get(),
                            getTitleKey("obtain_warden_armor_piece"),
                            getDescriptionKey("obtain_warden_armor_piece"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("warden_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.WARDEN_HELMET.get()))
                    .addCriterion("warden_mask", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.WARDEN_MASK.get()))
                    .addCriterion("warden_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.WARDEN_CHESTPLATE.get()))
                    .addCriterion("warden_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.WARDEN_LEGGINGS.get()))
                    .addCriterion("warden_boots", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.WARDEN_BOOTS.get()))
                    .addCriterion("has_sculk_shell", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.SCULK_SHELL.get()))
                    .rewards(AdvancementRewards.Builder.experience(75))
                    .save(saver, getLoc("obtain_warden_armor_piece"), existingFileHelper);
            AdvancementHolder get_deaf = Advancement.Builder.advancement()
                    .parent(obtain_echo_shrieker)
                    .display(
                            ItemRegistry.ECHO_SHRIEKER.get(),
                            getTitleKey("get_deaf"),
                            getDescriptionKey("get_deaf"),
                            null,
                            AdvancementType.CHALLENGE,
                            true,
                            true,
                            false
                    )
                    .addCriterion("affect_entities", AffectedEntitiesTrigger.TriggerInstance.minCount(ItemPredicate.Builder.item().of(ItemRegistry.ECHO_SHRIEKER.get()), 8))
                    .rewards(AdvancementRewards.Builder.experience(100))
                    .save(saver, getLoc("get_deaf"), existingFileHelper);
        }

        private static Component getTitleKey(String key) {
            return Component.translatable(getTranslationKey("advancement." + key + ".title"));
        }

        private static Component getDescriptionKey(String key) {
            return Component.translatable(getTranslationKey("advancement." + key + ".description"));
        }
    }
}
