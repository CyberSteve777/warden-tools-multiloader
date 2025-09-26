package net.trique.wardentools;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.trique.wardentools.networking.packet.AddEntityGlowPacket;
import net.trique.wardentools.util.WTLootTableModifiersFabric;
import net.trique.wardentools.worldgen.WardenWorldGeneration;

public class WardenToolsFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOGGER.info("Hello Fabric world!");
        WardenToolsCommon.init();
        WTLootTableModifiersFabric.addModifiers();
        //WTLootTableModifiersFabric.modifyLootTables();
        WardenWorldGeneration.generateWardenWorldGen();
        PayloadTypeRegistry.playS2C().register(AddEntityGlowPacket.TYPE, AddEntityGlowPacket.CODEC);
    }
}
