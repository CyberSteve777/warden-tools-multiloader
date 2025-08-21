package net.trique.wardentools.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class EnderSonicBoomParticle extends HugeExplosionParticle {
    protected EnderSonicBoomParticle(ClientLevel world, double x, double y, double z, double velocity, SpriteSet spriteProvider) {
        super(world, x, y, z, velocity, spriteProvider);
        this.lifetime = 16;
        this.quadSize = 1.5f;
        this.setSpriteFromAge(spriteProvider);
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z,
                                       double vx, double vy, double vz) {
            return new EnderSonicBoomParticle(world, x, y, z, vx, spriteProvider);
        }
    }
}