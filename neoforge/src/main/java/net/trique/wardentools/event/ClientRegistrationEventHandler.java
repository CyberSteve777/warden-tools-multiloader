package net.trique.wardentools.event;


import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.trique.wardentools.Constants;
import net.trique.wardentools.particle.*;
import net.trique.wardentools.platform.Services;
import net.trique.wardentools.registry.ItemRegistry;
import net.trique.wardentools.registry.ParticleRegistry;

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
        Services.CLIENT_ITEM_PROPERTIES_HELPER.registerCustomBow(ItemRegistry.ECHO_SHRIEKER.get());
    }
}
