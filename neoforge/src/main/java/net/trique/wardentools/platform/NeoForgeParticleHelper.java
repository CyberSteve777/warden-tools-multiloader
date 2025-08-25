package net.trique.wardentools.platform;

import net.minecraft.core.particles.SimpleParticleType;
import net.trique.wardentools.platform.services.IParticleHelper;

public class NeoForgeParticleHelper implements IParticleHelper {
    @Override
    public SimpleParticleType getSimpleParticle() {
        return new SimpleParticleType(false);
    }
}
