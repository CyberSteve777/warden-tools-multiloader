package net.trique.wardentools;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.trique.wardentools.loot.ModLootModifiers;
import net.trique.wardentools.networking.packet.AddEntityGlowPacket;
import net.trique.wardentools.util.echolocate.EchoLocateClientHelper;

@Mod(Constants.MOD_ID)
public class WardenToolsNeoForge {

    public WardenToolsNeoForge(IEventBus eventBus) {

        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        Constants.LOGGER.info("Hello NeoForge world!");
        WardenToolsCommon.init();
        eventBus.addListener(this::setupPackets);
        ModLootModifiers.register(eventBus);
    }

    private void setupPackets(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(Constants.MOD_ID).versioned("1").optional();
        registrar.playToClient(AddEntityGlowPacket.TYPE, AddEntityGlowPacket.CODEC, (message, context) -> {
            context.enqueueWork(() -> EchoLocateClientHelper.addEntity(message.id(), 100));
        });
    }
}