package net.trique.wardentools.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

import static net.trique.wardentools.registry.ParticleRegistry.*;

public class WTParticleDescriptionProvider extends ParticleDescriptionProvider {
    protected WTParticleDescriptionProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        sprite(ECHO_PARTICLE.get(), ECHO_PARTICLE.getId());
        sprite(SONIC_WAVE.get(), SONIC_WAVE.getId());
        spriteSet(ENDER_SONIC_BOOM.get(), ENDER_SONIC_BOOM.getId(), 16, false);
        spriteSet(AMETHYST_SONIC_BOOM.get(), AMETHYST_SONIC_BOOM.getId(), 16, false);
        spriteSet(ROSE_GOLD_SONIC_BOOM.get(), ROSE_GOLD_SONIC_BOOM.getId(), 16, false);
    }
}
