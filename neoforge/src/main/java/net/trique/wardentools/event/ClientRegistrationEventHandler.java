package net.trique.wardentools.event;


import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.trique.wardentools.Constants;
import net.trique.wardentools.client.renderer.SculkArrowRenderer;
import net.trique.wardentools.particle.*;
import net.trique.wardentools.registry.EntityRegistry;
import net.trique.wardentools.registry.ItemRegistry;
import net.trique.wardentools.registry.ParticleRegistry;
import net.trique.wardentools.particle.ShriekParticle.ShriekParticle;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class ClientRegistrationEventHandler {
    @SubscribeEvent
    public static void registerParticleProviders(final RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleRegistry.SHRIEK_PARTICLE.get(), ShriekParticle.Factory::new);
        event.registerSpriteSet(ParticleRegistry.ROSE_GOLD_SONIC_BOOM.get(), RoseGoldSonicBoomParticle.Factory::new);
        event.registerSpriteSet(ParticleRegistry.AMETHYST_SONIC_BOOM.get(), AmethystSonicBoomParticle.Factory::new);
        event.registerSpriteSet(ParticleRegistry.ENDER_SONIC_BOOM.get(), EnderSonicBoomParticle.Factory::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ItemProperties.register(ItemRegistry.ECHO_SHRIEKER.get(), ResourceLocation.parse("pull"),
                (stack, world, entity, seed) -> {
                    if (entity == null) {
                        return 0.0f;
                    }
                    if (entity.getUseItem() != stack) {
                        return 0.0f;
                    }
                    return (float) (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0f;
                });
        ItemProperties.register(ItemRegistry.ECHO_SHRIEKER.get(), ResourceLocation.parse("pulling"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem()
                        && entity.getUseItem() == stack ? 1.0f : 0.0f);
        EntityRenderers.register(EntityRegistry.SCULK_ARROW.get(), SculkArrowRenderer::new);
    }
}
