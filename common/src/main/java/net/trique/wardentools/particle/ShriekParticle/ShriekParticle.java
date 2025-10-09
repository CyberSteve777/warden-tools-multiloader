package net.trique.wardentools.particle.ShriekParticle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import org.jetbrains.annotations.Nullable;

public class ShriekParticle extends TextureSheetParticle {
    public ShriekParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, SpriteSet spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);

        this.friction = 0f;
        this.x = xd;
        this.y = yd;
        this.z = zd;
        this.quadSize *= 1f;
        this.lifetime = 20;
        this.setSpriteFromAge(spriteSet);


        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;
    }
    @Override
    public void tick() {
        super.tick();
        fadeOut();
    }

    private void fadeOut() {
        this.alpha = (-(1/(float)lifetime) * age + 1);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void setScale(float scale){
        this.scale(scale);
    }

    public record Factory(SpriteSet sprites) implements ParticleProvider<ShriekParticleOptions> {

        @Override
        public @Nullable Particle createParticle(ShriekParticleOptions shriekParticleOptions, ClientLevel clientLevel, double v, double v1, double v2, double v3, double v4, double v5) {
            float scale = shriekParticleOptions.getScale();
            ShriekParticle particle = new ShriekParticle(clientLevel,v,v1,v2,sprites,v3, v4,v5);
            particle.setScale(scale);
            return particle;
        }
    }
}
