package net.trique.wardentools;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.trique.wardentools.config.WTConfigClient;
import net.trique.wardentools.loot.ModLootModifiers;
import net.trique.wardentools.networking.packet.AddGlowPacket;
import net.trique.wardentools.platform.Services;
import net.trique.wardentools.util.warden_curse.WardenCurseClientHelper;

import static net.trique.wardentools.config.WTConfigClient.CLIENT_CONFIG;

@Mod(Constants.MOD_ID)
public class WardenToolsNeoForge {

    public WardenToolsNeoForge(IEventBus eventBus) {

        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        Constants.LOGGER.info("Hello NeoForge world!");
        WardenToolsCommon.init();
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.CLIENT, WTConfigClient.CLIENT_SPEC);
        eventBus.addListener(this::setupPackets);
        ModLootModifiers.register(eventBus);
        if (Services.PLATFORM.isClient()) {
            ModLoadingContext.get().getActiveContainer().registerExtensionPoint(IConfigScreenFactory.class,
                    ConfigurationScreen::new);
        }
    }

    private void setupPackets(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(Constants.MOD_ID).versioned("1").optional();
        registrar.playToClient(AddGlowPacket.TYPE, AddGlowPacket.CODEC, (message, context) -> context.enqueueWork(() -> {
            WardenCurseClientHelper.addEntity(message.id(), 100);
            if (CLIENT_CONFIG.outline_pos.get()) WardenCurseClientHelper.addBlockPos(message.pos(), 100);
        }));
    }
}