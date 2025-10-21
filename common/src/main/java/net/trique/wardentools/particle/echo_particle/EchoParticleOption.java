package net.trique.wardentools.particle.echo_particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.trique.wardentools.registry.ParticleRegistry;
import org.joml.Vector3f;

public record EchoParticleOption(float scale, float rotX,float rotY) implements ParticleOptions {
    public static final MapCodec<EchoParticleOption> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.FLOAT.fieldOf("scale").forGetter(EchoParticleOption::scale),
                    Codec.FLOAT.fieldOf("rotX").forGetter(EchoParticleOption::rotX),
            Codec.FLOAT.fieldOf("rotY").forGetter(EchoParticleOption::rotY)
    ).apply(instance, EchoParticleOption::new));

    public static final StreamCodec<ByteBuf, EchoParticleOption> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, EchoParticleOption::scale,
            ByteBufCodecs.FLOAT, EchoParticleOption::rotX,
            ByteBufCodecs.FLOAT, EchoParticleOption::rotY,
            EchoParticleOption::new);

    @Override
    public ParticleType<EchoParticleOption> getType() {
        return ParticleRegistry.ECHO_PARTICLE.get();
    }
}
