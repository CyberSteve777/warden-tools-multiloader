package net.trique.wardentools.particle.echo_particle;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class EchoParticleType extends ParticleType<EchoParticleOption> {

    public EchoParticleType(boolean overrideLimitter) {
        super(overrideLimitter);
    }

    @Override
    public MapCodec<EchoParticleOption> codec() {
        return EchoParticleOption.CODEC;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, EchoParticleOption> streamCodec() {
        return EchoParticleOption.STREAM_CODEC;
    }
}
