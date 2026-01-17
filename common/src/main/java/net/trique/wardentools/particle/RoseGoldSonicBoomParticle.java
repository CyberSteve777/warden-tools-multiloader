package net.trique.wardentools.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class RoseGoldSonicBoomParticle extends HugeExplosionParticle {
    protected RoseGoldSonicBoomParticle(ClientLevel world, double x, double y, double z, double velocity, SpriteSet spriteProvider) {
        super(world, x, y, z, velocity, spriteProvider);
        this.lifetime = 16;
        this.quadSize = 1.5f;
        this.setSpriteFromAge(spriteProvider);
    }

    public record Factory(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {

        @Override
            public Particle createParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z,
                                           double vx, double vy, double vz) {
                return new RoseGoldSonicBoomParticle(world, x, y, z, vx, spriteProvider);
            }
        }
}