package net.trique.wardentools.registry;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.trique.wardentools.Constants;
import net.trique.wardentools.advancements.criterion.AffectedEntitiesTrigger;
import net.trique.wardentools.registration.RegistrationProvider;
import net.trique.wardentools.registration.RegistryObject;

public class TriggerTypeRegistry {
    private static final RegistrationProvider<CriterionTrigger<?>> CRITERION_TRIGGER_REGISTRATION_PROVIDER = RegistrationProvider.get(
            BuiltInRegistries.TRIGGER_TYPES, Constants.MOD_ID
    );

    public static final RegistryObject<CriterionTrigger<?>, AffectedEntitiesTrigger> AFFECTED_ENTITIES_TRIGGER =
            CRITERION_TRIGGER_REGISTRATION_PROVIDER.register("affected_entities", AffectedEntitiesTrigger::new);

    public static void init() {
        Constants.LOGGER.info("Registering Warden Tools triggers...");
    }
}
