package net.trique.wardentools.particle.echo_particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.trique.wardentools.registry.ParticleRegistry;

public record EchoParticleOption(float scale) implements ParticleOptions {
    public static final MapCodec<EchoParticleOption> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.FLOAT.fieldOf("scale").forGetter((opt) -> opt.scale)).apply(instance, EchoParticleOption::new));

    public static final StreamCodec<ByteBuf, EchoParticleOption> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.FLOAT, echoParticleOption -> echoParticleOption.scale, EchoParticleOption::new);

    @Override
    public ParticleType<EchoParticleOption> getType() {
        return ParticleRegistry.ECHO_PARTICLE.get();
    }
}
