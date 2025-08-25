package net.trique.wardentools.platform;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.trique.wardentools.platform.services.IClientItemPropertiesHelper;

public class NeoForgeClientItemPropertiesHelper implements IClientItemPropertiesHelper {
    @Override
    public void registerCustomBow(Item customBow) {
        ItemProperties.register(customBow, ResourceLocation.parse("pull"),
                (stack, world, entity, seed)->{
                    if (entity == null){
                        return 0.0f;
                    }
                    if(entity.getUseItem() != stack){
                        return 0.0f;
                    }
                    return (float)(stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0f;
                });
        ItemProperties.register(customBow, ResourceLocation.parse("pulling"),
                (stack, world,entity,seed) -> entity != null && entity.isUsingItem()
                        && entity.getUseItem() == stack ? 1.0f : 0.0f);

    }
}
