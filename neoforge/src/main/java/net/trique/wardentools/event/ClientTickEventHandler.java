package net.trique.wardentools.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.trique.wardentools.Constants;
import net.trique.wardentools.util.vibra_sense.VibraSenseClientHelper;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class ClientTickEventHandler {
    @SubscribeEvent
    private static void clientGlowTick(ClientTickEvent.Pre event) {
        VibraSenseClientHelper.tickClientGlowingEntities();
    }
}
