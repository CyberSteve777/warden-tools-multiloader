package net.trique.wardentools.entity.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.trique.wardentools.WardenTools;

@Environment(EnvType.CLIENT)
public class SculkArrowRenderer extends ArrowRenderer<SculkArrowEntity> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(WardenTools.MOD_ID, "textures/entity/projectiles/sculk_arrow.png");

    public SculkArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTexture(SculkArrowEntity entity) {
        return TEXTURE;
    }
}