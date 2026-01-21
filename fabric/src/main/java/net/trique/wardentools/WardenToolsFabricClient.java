package net.trique.wardentools;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.renderer.RenderType;
import net.trique.wardentools.client.WTKeybinds;
import net.trique.wardentools.particle.sonic_wave.SonicWaveParticle;
import net.trique.wardentools.util.ClientFunctions;
import net.trique.wardentools.util.WTRegModelUtil;
import net.trique.wardentools.client.renderer.SculkArrowRenderer;
import net.trique.wardentools.particle.*;
import net.trique.wardentools.particle.echo_particle.EchoParticle;
import net.trique.wardentools.registry.*;
import net.trique.wardentools.util.warden_curse.WardenCurseClientHelper;


public class WardenToolsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.SCULKHYST_CLUSTER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.LARGE_SCULKHYST_BUD.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.MEDIUM_SCULKHYST_BUD.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.SMALL_SCULKHYST_BUD.get(), RenderType.cutout());
        WTRegModelUtil.registerModModels();
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.ECHO_PARTICLE.get(), EchoParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.SONIC_WAVE.get(), SonicWaveParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.ROSE_GOLD_SONIC_BOOM.get(), RoseGoldSonicBoomParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.AMETHYST_SONIC_BOOM.get(), AmethystSonicBoomParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.ENDER_SONIC_BOOM.get(), EnderSonicBoomParticle.Factory::new);
        EntityRendererRegistry.register(EntityRegistry.SCULK_ARROW.get(), SculkArrowRenderer::new);
        ClientTickEvents.START_CLIENT_TICK.register((client) -> {
            WardenCurseClientHelper.tickClientGlowingEntities();
            WardenCurseClientHelper.tickOutlinedBlocks();
            ClientFunctions.handleClientTick();
        });
        WorldRenderEvents.AFTER_TRANSLUCENT.register(worldRenderContext ->
                WardenCurseClientHelper.renderOutlinedBlocks(worldRenderContext.matrixStack()));
        KeyBindingHelper.registerKeyBinding(WTKeybinds.CONSUME_CHARGES);
    }
}