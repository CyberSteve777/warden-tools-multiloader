package net.trique.wardentools;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.client.ConfigScreenFactoryRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.trique.wardentools.networking.packet.AddBlockOutlinePacket;
import net.trique.wardentools.util.WTRegModelUtil;
import net.trique.wardentools.entity.SculkArrowRenderer;
import net.trique.wardentools.networking.packet.AddEntityGlowPacket;
import net.trique.wardentools.particle.*;
import net.trique.wardentools.registry.*;
import net.trique.wardentools.util.warden_curse.WardenCurseClientHelper;

import static net.trique.wardentools.config.WTConfigClient.CONFIG;

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
        ClientTickEvents.START_CLIENT_TICK.register((client) -> {
            WardenCurseClientHelper.tickClientGlowingEntities();
            WardenCurseClientHelper.tickOutlinedBlocks();
        });
        WorldRenderEvents.AFTER_TRANSLUCENT.register(worldRenderContext ->
                WardenCurseClientHelper.renderOutlinedBlocks(worldRenderContext.matrixStack()));
        ClientPlayNetworking.registerGlobalReceiver(AddEntityGlowPacket.TYPE, ((payload, context) ->
                WardenCurseClientHelper.addEntity(payload.id(), payload.ticks()))
        );
        ClientPlayNetworking.registerGlobalReceiver(AddBlockOutlinePacket.TYPE, ((payload, context) ->  {
            if (CONFIG.outline_pos.get()) WardenCurseClientHelper.addBlockPos(payload.pos(), payload.ticks());
        }));
        ConfigScreenFactoryRegistry.INSTANCE.register(Constants.MOD_ID, ConfigurationScreen::new);
    }
}