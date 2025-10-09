package net.trique.wardentools.particle.ShriekParticle;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class ShriekParticleType extends ParticleType<ShriekParticleOptions> {

    public ShriekParticleType(boolean overrideLimitter) {
        super(overrideLimitter);
    }

    @Override
    public MapCodec<ShriekParticleOptions> codec() {
        return ShriekParticleOptions.CODEC;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, ShriekParticleOptions> streamCodec() {
        return ShriekParticleOptions.STREAM_CODEC;
    }
}
