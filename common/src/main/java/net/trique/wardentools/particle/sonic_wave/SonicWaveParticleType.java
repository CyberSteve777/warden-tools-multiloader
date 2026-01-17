package net.trique.wardentools.particle.sonic_wave;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class SonicWaveParticleType extends ParticleType<SonicWaveParticleOption> {
    public SonicWaveParticleType(boolean overrideLimitter) {
        super(overrideLimitter);
    }

    @Override
    public MapCodec<SonicWaveParticleOption> codec() {
        return SonicWaveParticleOption.CODEC;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, SonicWaveParticleOption> streamCodec() {
        return SonicWaveParticleOption.STREAM_CODEC;
    }
}
