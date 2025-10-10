package net.trique.wardentools.particle.echo_particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;

public class EchoParticle extends TextureSheetParticle {
    public EchoParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, SpriteSet spriteSet, double xd, double yd, double zd) {
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
        this.alpha = (-(1 / (float) lifetime) * age + 1);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public record Factory(SpriteSet sprites) implements ParticleProvider<EchoParticleOption> {

        @Override
        public Particle createParticle(EchoParticleOption echoParticleOption, ClientLevel clientLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            float scale = echoParticleOption.scale();
            EchoParticle particle = new EchoParticle(clientLevel, x, y, z, sprites, xSpeed, ySpeed, zSpeed);
            return particle.scale(scale);
        }
    }
}
