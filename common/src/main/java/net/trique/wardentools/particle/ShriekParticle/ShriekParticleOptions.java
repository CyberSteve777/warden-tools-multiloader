package net.trique.wardentools.particle.ShriekParticle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.trique.wardentools.registry.ParticleRegistry;

public class ShriekParticleOptions implements ParticleOptions {
    private float scale;
    // Read and write information, typically for use in commands
    // Since there is no information in this type, this will be an empty string
    public static final MapCodec<ShriekParticleOptions> CODEC = RecordCodecBuilder.mapCodec( instance -> instance.group(Codec.FLOAT.fieldOf("scale").forGetter((opt)->opt.scale)).apply(instance, ShriekParticleOptions::new));

    // Read and write information to the network buffer.
    public static final StreamCodec<ByteBuf, ShriekParticleOptions> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.FLOAT, shriekParticleOptions -> shriekParticleOptions.scale, ShriekParticleOptions::new);

    // Does not need any parameters, but may define any fields necessary for the particle to work.
    public ShriekParticleOptions(float scale) {
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }

    @Override
    public ParticleType<ShriekParticleOptions> getType() {
        return ParticleRegistry.SHRIEK_PARTICLE.get();
    }
}
