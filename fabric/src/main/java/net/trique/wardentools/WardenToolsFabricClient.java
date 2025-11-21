package net.trique.wardentools;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.client.ConfigScreenFactoryRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.trique.wardentools.client.WTKeybinds;
import net.trique.wardentools.networking.packet.S2CAddBlockOutlinePacket;
import net.trique.wardentools.util.WTRegModelUtil;
import net.trique.wardentools.client.renderer.SculkArrowRenderer;
import net.trique.wardentools.networking.packet.S2CAddEntityGlowPacket;
import net.trique.wardentools.particle.*;
import net.trique.wardentools.particle.echo_particle.EchoParticle;
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
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.ECHO_PARTICLE.get(), EchoParticle.Factory::new);
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
        KeyBindingHelper.registerKeyBinding(WTKeybinds.CONSUME_CHARGES);
        ConfigScreenFactoryRegistry.INSTANCE.register(Constants.MOD_ID, ConfigurationScreen::new);
    }
}