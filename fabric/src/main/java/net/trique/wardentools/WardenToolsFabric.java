package net.trique.wardentools;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.trique.wardentools.particle.AmethystSonicBoomParticle;
import net.trique.wardentools.particle.EnderSonicBoomParticle;
import net.trique.wardentools.particle.RoseGoldSonicBoomParticle;
import net.trique.wardentools.particle.ShriekParticle;
import net.trique.wardentools.registry.ParticleRegistry;

public class WardenToolsFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOGGER.info("Hello Fabric world!");
        WardenToolsCommon.init();
    }
}
