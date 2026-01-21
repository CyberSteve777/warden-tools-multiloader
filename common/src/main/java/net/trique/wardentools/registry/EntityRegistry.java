package net.trique.wardentools.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.trique.wardentools.Constants;
import net.trique.wardentools.entity.SculkArrowEntity;
import net.trique.wardentools.registration.RegistrationProvider;
import net.trique.wardentools.registration.RegistryObject;

public class EntityRegistry {
    protected static RegistrationProvider<EntityType<?>> ENTITY_TYPE = RegistrationProvider.get(BuiltInRegistries.ENTITY_TYPE,
            Constants.MOD_ID);


    public static RegistryObject<EntityType<SculkArrowEntity>> SCULK_ARROW = ENTITY_TYPE.register("sculk_arrow", () ->
            EntityType.Builder.<SculkArrowEntity>of(SculkArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("sculk_arrow")
    );


    public static void init() {
        Constants.LOGGER.info("Registering {} Entities...", Constants.MOD_NAME);
    }
}
