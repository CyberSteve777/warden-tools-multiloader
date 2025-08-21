package net.trique.wardentools.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.trique.wardentools.WardenTools;
import net.trique.wardentools.entity.custom.SculkArrowEntity;

public class WardenEntities {

    public static final EntityType<SculkArrowEntity> SCULK_ARROW_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(WardenTools.MOD_ID, "sculk_arrow"),
            FabricEntityTypeBuilder.<SculkArrowEntity>create(MobCategory.MISC, SculkArrowEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .trackRangeBlocks(4)
                    .trackedUpdateRate(20)
                    .build()
    );

    public static void registerWardenEntities() {
        WardenTools.LOGGER.info("Register Warden Entities");
    }
}