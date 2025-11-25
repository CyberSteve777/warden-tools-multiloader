package net.trique.wardentools.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.trique.wardentools.Constants;
import net.trique.wardentools.particle.echo_particle.EchoParticleType;
import net.trique.wardentools.platform.Services;
import net.trique.wardentools.registration.RegistrationProvider;
import net.trique.wardentools.registration.RegistryObject;

public class ParticleRegistry {
    protected static final RegistrationProvider<ParticleType<?>> PARTICLE_TYPES = RegistrationProvider.get(Registries.PARTICLE_TYPE, Constants.MOD_ID);

    public static final RegistryObject<ParticleType<?>, EchoParticleType> ECHO_PARTICLE = PARTICLE_TYPES.register("echo_particle", () -> new EchoParticleType(false));
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> ROSE_GOLD_SONIC_BOOM = PARTICLE_TYPES.register("rose_gold_sonic_boom", Services.PLATFORM::getSimpleParticle);
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> AMETHYST_SONIC_BOOM = PARTICLE_TYPES.register("amethyst_sonic_boom", Services.PLATFORM::getSimpleParticle);
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> ENDER_SONIC_BOOM = PARTICLE_TYPES.register("ender_sonic_boom", Services.PLATFORM::getSimpleParticle);
    public static void init() {
        Constants.LOGGER.info("Registering particles for Warden Tools...");
    }
}
