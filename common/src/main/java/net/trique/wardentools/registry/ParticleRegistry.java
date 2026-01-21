package net.trique.wardentools.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.trique.wardentools.Constants;
import net.trique.wardentools.particle.echo_particle.EchoParticleType;
import net.trique.wardentools.particle.sonic_wave.SonicWaveParticleType;
import net.trique.wardentools.platform.Services;
import net.trique.wardentools.registration.RegistrationProvider;
import net.trique.wardentools.registration.RegistryObject;

public class ParticleRegistry {
    protected static final RegistrationProvider<ParticleType<?>> PARTICLE_TYPES = RegistrationProvider.get(BuiltInRegistries.PARTICLE_TYPE, Constants.MOD_ID);

    public static final RegistryObject<EchoParticleType> ECHO_PARTICLE = PARTICLE_TYPES.register("echo_particle", () -> new EchoParticleType(false));
    public static final RegistryObject<SonicWaveParticleType> SONIC_WAVE = PARTICLE_TYPES.register("sonic_wave", () -> new SonicWaveParticleType(false));
    public static final RegistryObject<SimpleParticleType> ROSE_GOLD_SONIC_BOOM = PARTICLE_TYPES.register("rose_gold_sonic_boom", Services.PLATFORM::getSimpleParticle);
    public static final RegistryObject<SimpleParticleType> AMETHYST_SONIC_BOOM = PARTICLE_TYPES.register("amethyst_sonic_boom", Services.PLATFORM::getSimpleParticle);
    public static final RegistryObject<SimpleParticleType> ENDER_SONIC_BOOM = PARTICLE_TYPES.register("ender_sonic_boom", Services.PLATFORM::getSimpleParticle);
    public static void init() {
        Constants.LOGGER.info("Registering particles for Warden Tools...");
    }
}
