package net.trique.wardentools.item.staff;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.trique.wardentools.registry.ParticleRegistry;

import java.util.HashSet;
import java.util.Set;

public class EnderEchoStaff extends EchoStaff {


    public EnderEchoStaff(Properties settings, int cooldown, int useDuration, int distance, int particleDelta, float damage, double horizontalKnockbackCoefficient, double verticalKnockbackCoefficient) {
        super(settings, cooldown, useDuration, distance, particleDelta, damage, horizontalKnockbackCoefficient, verticalKnockbackCoefficient);
    }

    @Override
    protected void spawnSonicBoom(Level level, LivingEntity user) {
        level.playSound(null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.WARDEN_SONIC_BOOM, SoundSource.BLOCKS, 5.0f, 1.0f);

        float heightOffset = 1.6f;
        Vec3 target = user.position().add(user.getLookAngle().scale(distance));
        Vec3 source = user.position().add(0.0, heightOffset, 0.0);
        Vec3 offsetToTarget = target.subtract(source);
        Vec3 normalized = offsetToTarget.normalize();

        Set<Entity> hit = new HashSet<>();
        for (int i = 1; i < Mth.floor(offsetToTarget.length()) + particleDelta; ++i) {
            Vec3 pos = source.add(normalized.scale(i));
            if (level instanceof ServerLevel serverWorld) {
                serverWorld.sendParticles(ParticleRegistry.ENDER_SONIC_BOOM.get(),
                        pos.x, pos.y, pos.z, 1, 0.0, 0.0, 0.0, 0.0);
            }
            hit.addAll(level.getEntitiesOfClass(LivingEntity.class,
                    new AABB(BlockPos.containing(pos)).inflate(1),
                    it -> !(it.isAlliedTo(user) || (it instanceof TamableAnimal helper && helper.isOwnedBy(user)))));
        }

        hit.remove(user);

        for (Entity entity : hit) {
            if (entity instanceof LivingEntity living) {
                living.hurt(level.damageSources().sonicBoom(user), damage);
                Vec3 originalPos = living.position();
                for (int j = 0; j < 16; ++j) {
                    double dx = living.getX() + (living.getRandom().nextDouble() - 0.5) * 32.0;
                    double dy = Mth.clamp(
                            living.getY() + (double)(living.getRandom().nextInt(16) - 8),
                            level.getMinBuildHeight(),
                            level.getMinBuildHeight() + ((ServerLevel) level).getLogicalHeight() - 1);
                    double dz = living.getZ() + (living.getRandom().nextDouble() - 0.5) * 32.0;

                    if (living.isPassenger()) {
                        living.stopRiding();
                    }

                    if (living.randomTeleport(dx, dy, dz, true)) {
                        level.gameEvent(GameEvent.TELEPORT, originalPos, Context.of(living));

                        level.playSound(null, dx, dy, dz,
                                SoundEvents.PLAYER_TELEPORT, SoundSource.BLOCKS, 5.0F, 1.0F);

                        if (level instanceof ServerLevel serverWorld) {
                            serverWorld.sendParticles(ParticleTypes.PORTAL, dx, dy, dz, 40, 0.5, 0.5, 0.5, 0.1);
                        }

                        living.resetFallDistance();
                        break;
                    }
                }
            }
        }
    }
}