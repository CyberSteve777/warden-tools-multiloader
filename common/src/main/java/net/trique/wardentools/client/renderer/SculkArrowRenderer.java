package net.trique.wardentools.client.renderer;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.trique.wardentools.Constants;
import net.trique.wardentools.entity.SculkArrowEntity;


public class SculkArrowRenderer extends ArrowRenderer<SculkArrowEntity> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/entity/projectiles/sculk_arrow.png");

    public SculkArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(SculkArrowEntity sculkArrowEntity) {
        return TEXTURE;
    }
}