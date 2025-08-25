package net.trique.wardentools;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.trique.wardentools.data.WardenModelPredicateProvider;
import net.trique.wardentools.entity.SculkArrowRenderer;
import net.trique.wardentools.particle.AmethystSonicBoomParticle;
import net.trique.wardentools.particle.RoseGoldSonicBoomParticle;
import net.trique.wardentools.particle.ShriekParticle;
import net.trique.wardentools.registry.BlockRegistry;
import net.trique.wardentools.registry.EntityRegistry;
import net.trique.wardentools.registry.ParticleRegistry;

public class WardenToolsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.SCULKHYST_CLUSTER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.LARGE_SCULKHYST_BUD.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.MEDIUM_SCULKHYST_BUD.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.SMALL_SCULKHYST_BUD.get(), RenderType.cutout());
        WardenModelPredicateProvider.regModModels();
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.SHRIEK_PARTICLE.get(), ShriekParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.ROSE_GOLD_SONIC_BOOM.get(), RoseGoldSonicBoomParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.AMETHYST_SONIC_BOOM.get(), AmethystSonicBoomParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.ENDER_SONIC_BOOM.get(), AmethystSonicBoomParticle.Factory::new);
        EntityRendererRegistry.register(EntityRegistry.SCULK_ARROW.get(), SculkArrowRenderer::new);
    }
}