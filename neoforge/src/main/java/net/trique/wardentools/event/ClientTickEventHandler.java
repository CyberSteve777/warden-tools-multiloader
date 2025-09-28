package net.trique.wardentools.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.trique.wardentools.Constants;
import net.trique.wardentools.util.warden_curse.WardenCurseClientHelper;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class ClientTickEventHandler {
    @SubscribeEvent
    private static void clientGlowTick(final ClientTickEvent.Pre event) {
        WardenCurseClientHelper.tickClientGlowingEntities();
        WardenCurseClientHelper.tickOutlinedBlocks();
    }

    @SubscribeEvent
    private static void renderBlocks(final RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;
        WardenCurseClientHelper.renderOutlinedBlocks(event.getPoseStack());
    }
}
