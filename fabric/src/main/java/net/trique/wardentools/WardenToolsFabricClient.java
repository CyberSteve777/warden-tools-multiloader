package net.trique.wardentools;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.trique.wardentools.util.WTRegModelUtil;
import net.trique.wardentools.entity.SculkArrowRenderer;
import net.trique.wardentools.networking.packet.AddEntityGlowPacket;
import net.trique.wardentools.particle.*;
import net.trique.wardentools.registry.*;
import net.trique.wardentools.util.vibra_sense.VibraSenseClientHelper;

public class WardenToolsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.SCULKHYST_CLUSTER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.LARGE_SCULKHYST_BUD.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.MEDIUM_SCULKHYST_BUD.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.SMALL_SCULKHYST_BUD.get(), RenderType.cutout());
        WTRegModelUtil.registerModModels();
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.SHRIEK_PARTICLE.get(), ShriekParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.ROSE_GOLD_SONIC_BOOM.get(), RoseGoldSonicBoomParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.AMETHYST_SONIC_BOOM.get(), AmethystSonicBoomParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.ENDER_SONIC_BOOM.get(), EnderSonicBoomParticle.Factory::new);
        EntityRendererRegistry.register(EntityRegistry.SCULK_ARROW.get(), SculkArrowRenderer::new);
        ClientPlayNetworking.registerGlobalReceiver(AddEntityGlowPacket.TYPE, ((payload, context) ->
                VibraSenseClientHelper.addEntity(payload.id(), 100))
        );
    }
}