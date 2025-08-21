package net.trique.wardentools;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.trique.wardentools.block.WardenBlocks;
import net.trique.wardentools.data.WardenModelPredicateProvider;
import net.trique.wardentools.entity.WardenEntities;
import net.trique.wardentools.entity.custom.SculkArrowRenderer;
import net.trique.wardentools.particle.WardenParticles;
import net.trique.wardentools.particle.custom.AmethystSonicBoomParticle;
import net.trique.wardentools.particle.custom.RoseGoldSonicBoomParticle;
import net.trique.wardentools.particle.custom.ShriekParticle;

public class WardenToolsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(WardenBlocks.SCULKHYST_CLUSTER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WardenBlocks.LARGE_SCULKHYST_BUD, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WardenBlocks.MEDIUM_SCULKHYST_BUD, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WardenBlocks.SMALL_SCULKHYST_BUD, RenderType.cutout());
        WardenModelPredicateProvider.regModModels();
        ParticleFactoryRegistry.getInstance().register(WardenParticles.SHRIEK_PARTICLE, ShriekParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(WardenParticles.ROSE_GOLD_SONIC_BOOM, RoseGoldSonicBoomParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(WardenParticles.AMETHYST_SONIC_BOOM, AmethystSonicBoomParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(WardenParticles.ENDER_SONIC_BOOM, AmethystSonicBoomParticle.Factory::new);
        EntityRendererRegistry.register(WardenEntities.SCULK_ARROW_ENTITY, SculkArrowRenderer::new);
    }
}