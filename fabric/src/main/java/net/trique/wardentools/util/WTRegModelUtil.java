package net.trique.wardentools.util;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BowItem;
import net.trique.wardentools.registry.ItemRegistry;

public class WTRegModelUtil {
    public static void registerModModels() {
        registerBow(ItemRegistry.ECHO_SHRIEKER.get());
    }

    private static void registerBow(BowItem bow) {
        ItemProperties.register(bow, ResourceLocation.parse("pull"),
                (stack, world, entity, seed) -> {
                    if (entity == null) {
                        return 0.0f;
                    }
                    if (entity.getUseItem() != stack) {
                        return 0.0f;
                    }
                    return (float) (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0f;
                });
        ItemProperties.register(bow, ResourceLocation.parse("pulling"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem()
                        && entity.getUseItem() == stack ? 1.0f : 0.0f);
    }
}
