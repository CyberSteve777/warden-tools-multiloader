package net.trique.wardentools.particle.sonic_wave;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import net.trique.wardentools.Constants;
import org.joml.Quaternionf;

public class SonicWaveParticle extends TextureSheetParticle {
    private int delay;

    protected SonicWaveParticle(ClientLevel level, double x, double y, double z, int delay) {
        super(level, x, y, z, 0.0, 0.0, 0.0);
        this.delay = delay;
        this.quadSize = 0.85F;
        this.lifetime = 7;
        this.gravity = 0.0F;
        this.xd = 0.0;
        this.yd = 0.0;
        this.zd = 0.0;
    }

    @Override
    public float getQuadSize(float scaleFactor) {
        return this.quadSize * Mth.clamp( 0.15F * (this.age + scaleFactor) / this.lifetime, 0.0F, 1.0F);
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        if (this.delay <= 0) {
            this.alpha = 1.0F - Mth.clamp((this.age + partialTicks) / this.lifetime, 0.0F, 1.0F);
            Quaternionf quaternionf = new Quaternionf();
            quaternionf.rotateYXZ(0.0f, (float) Math.PI / 2, (float) Math.PI / 2);
            this.renderRotatedQuad(buffer, renderInfo, quaternionf, partialTicks);
            quaternionf.rotateY((float) -Math.PI);
            this.renderRotatedQuad(buffer, renderInfo, quaternionf, partialTicks);
        }
    }

    @Override
    public int getLightColor(float partialTick) {
        return 240;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        if (this.delay > 0) {
            this.delay--;
        } else {
            super.tick();
        }
    }

    public record Factory(SpriteSet sprite) implements ParticleProvider<SonicWaveParticleOption> {
        @Override
        public Particle createParticle(SonicWaveParticleOption option, ClientLevel clientLevel,
                                       double x,
                                       double y,
                                       double z,
                                       double xSpeed,
                                       double ySpeed,
                                       double zSpeed) {
            SonicWaveParticle particle = new SonicWaveParticle(clientLevel, x, y, z, option.delay());
            float scale = option.radius() * 8f;
            particle.pickSprite(this.sprite);
            particle.setAlpha(1.0F);
            particle.scale(scale);
            return particle;
        }
    }
}
