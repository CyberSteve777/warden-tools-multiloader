package net.trique.wardentools.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.trique.wardentools.Constants;
import net.trique.wardentools.particle.ShriekParticle.ShriekParticleType;
import net.trique.wardentools.platform.Services;
import net.trique.wardentools.registration.RegistrationProvider;
import net.trique.wardentools.registration.RegistryObject;

public class ParticleRegistry {
    protected static final RegistrationProvider<ParticleType<?>> PARTICLE_TYPES = RegistrationProvider.get(Registries.PARTICLE_TYPE, Constants.MOD_ID);

    public static final RegistryObject<ParticleType<?>, ShriekParticleType> SHRIEK_PARTICLE = PARTICLE_TYPES.register("shriek_particle", ()-> new ShriekParticleType(false));
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> ROSE_GOLD_SONIC_BOOM = PARTICLE_TYPES.register("rose_gold_sonic_boom", Services.PARTICLE_HELPER::getSimpleParticle);
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> AMETHYST_SONIC_BOOM = PARTICLE_TYPES.register("amethyst_sonic_boom", Services.PARTICLE_HELPER::getSimpleParticle);
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> ENDER_SONIC_BOOM = PARTICLE_TYPES.register("ender_sonic_boom", Services.PARTICLE_HELPER::getSimpleParticle);
    public static void init() {
        Constants.LOGGER.info("Registering particles for Warden Tools...");
    }
}
