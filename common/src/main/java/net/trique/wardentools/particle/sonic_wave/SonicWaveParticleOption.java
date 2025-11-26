package net.trique.wardentools.particle.sonic_wave;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.trique.wardentools.registry.ParticleRegistry;

public record SonicWaveParticleOption(int delay) implements ParticleOptions {
    public static final MapCodec<SonicWaveParticleOption> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(Codec.INT.fieldOf("delay").forGetter(SonicWaveParticleOption::delay)).apply(instance, SonicWaveParticleOption::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, SonicWaveParticleOption> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, instance -> instance.delay, SonicWaveParticleOption::new
    );

    @Override
    public ParticleType<?> getType() {
        return ParticleRegistry.SONIC_WAVE.get();
    }
}
