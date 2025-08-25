package net.trique.wardentools.platform;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.trique.wardentools.platform.services.IParticleHelper;

public class FabricParticleHelper implements IParticleHelper {
    @Override
    public SimpleParticleType getSimpleParticle() {
        return FabricParticleTypes.simple();
    }
}
