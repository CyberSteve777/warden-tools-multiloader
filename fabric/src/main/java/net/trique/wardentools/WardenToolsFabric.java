package net.trique.wardentools;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.neoforged.fml.config.ModConfig;
import net.trique.wardentools.config.WTConfigClient;
import net.trique.wardentools.config.WTConfigServer;
import net.trique.wardentools.networking.packet.PacketHandler;
import net.trique.wardentools.networking.packet.S2CAddBlockOutlinePacket;
import net.trique.wardentools.networking.packet.S2CAddEntityGlowPacket;
import net.trique.wardentools.util.WTGlobalLootTableModifierUtils;
import net.trique.wardentools.util.WTPotionRecipeHelper;
import net.trique.wardentools.worldgen.WardenWorldGeneration;

public class WardenToolsFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
//        Constants.LOGGER.info("Hello Fabric world!");
        WardenToolsCommon.init();
        WTPotionRecipeHelper.addPotionRecipes();
        WTGlobalLootTableModifierUtils.addModifiers();
//        WTLootTableModifiers.modifyLootTables();
        WardenWorldGeneration.generateWardenWorldGen();
        PacketHandler.registerPackets();
        NeoForgeConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.SERVER, WTConfigServer.SPEC);
        NeoForgeConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.CLIENT, WTConfigClient.SPEC);
    }
}
