package net.trique.wardentools.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.trique.wardentools.WardenTools;

public class WardenParticles {
    public static final SimpleParticleType SHRIEK_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType ROSE_GOLD_SONIC_BOOM = FabricParticleTypes.simple();
    public static final SimpleParticleType AMETHYST_SONIC_BOOM = FabricParticleTypes.simple();
    public static final SimpleParticleType ENDER_SONIC_BOOM = FabricParticleTypes.simple();

    public static void regParticles(){
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(WardenTools.MOD_ID, "shriek_particle"), SHRIEK_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(WardenTools.MOD_ID, "rose_gold_sonic_boom"), ROSE_GOLD_SONIC_BOOM);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(WardenTools.MOD_ID, "amethyst_sonic_boom"), AMETHYST_SONIC_BOOM);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(WardenTools.MOD_ID, "ender_sonic_boom"), ENDER_SONIC_BOOM);
    }
}
