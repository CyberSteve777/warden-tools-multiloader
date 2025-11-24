package net.trique.wardentools.event;


import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.trique.wardentools.Constants;
import net.trique.wardentools.util.WardenEchoStaffHelper;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class ServerTickEventHandler {
    @SubscribeEvent
    public static void clientFillRenderPositions(final ServerTickEvent.Pre event) {
    }
}
