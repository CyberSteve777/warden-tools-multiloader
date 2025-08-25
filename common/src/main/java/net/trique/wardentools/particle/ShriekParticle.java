package net.trique.wardentools.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class ShriekParticle extends TextureSheetParticle {
    public ShriekParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, SpriteSet spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);

        this.friction = 0f;
        this.x = xd;
        this.y = yd;
        this.z = zd;
        this.quadSize *= 2f;
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

    public void setScale(){
        this.quadSize = 2;
    }

    public record Factory(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z,
                                           double dx, double dy, double dz) {
                return new ShriekParticle(level, x, y, z, this.sprites, dx, dy, dz);
            }
        }
}
