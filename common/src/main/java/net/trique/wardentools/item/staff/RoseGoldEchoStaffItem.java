    package net.trique.wardentools.item.staff;

    import net.minecraft.core.BlockPos;
    import net.minecraft.server.level.ServerLevel;
    import net.minecraft.sounds.SoundEvents;
    import net.minecraft.util.Mth;
    import net.minecraft.world.damagesource.DamageSource;
    import net.minecraft.world.entity.Entity;
    import net.minecraft.world.entity.LivingEntity;
    import net.minecraft.world.entity.TamableAnimal;
    import net.minecraft.world.entity.ai.attributes.Attributes;
    import net.minecraft.world.item.ItemStack;
    import net.minecraft.world.phys.AABB;
    import net.minecraft.world.phys.Vec3;
    import net.trique.wardentools.registry.ParticleRegistry;

    import java.util.HashSet;
    import java.util.Set;

    public class RoseGoldEchoStaffItem extends EchoStaffItem {


        public RoseGoldEchoStaffItem(Properties settings, int cooldown, int useDuration, int distance, int particleDelta, float damage, double horizontalKnockbackCoefficient, double verticalKnockbackCoefficient) {
            super(settings, cooldown, useDuration, distance, particleDelta, damage, horizontalKnockbackCoefficient, verticalKnockbackCoefficient);
        }

        @Override
        protected void spawnSonicBoom(ItemStack stack, ServerLevel world, LivingEntity user) {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.WARDEN_SONIC_BOOM, user.getSoundSource(), 5.0f, 1.0f);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.TRIAL_SPAWNER_EJECT_ITEM, user.getSoundSource(), 4.0f, 1.0f);
            Vec3 source = user.position().add(0.0, user.getEyeHeight(), 0.0);
            float enhanced_distance = calculateFinalDistance(stack, world, distance);
            Vec3 target = source.add(user.getLookAngle().scale(enhanced_distance));
            Vec3 offsetToTarget = target.subtract(source);
            Vec3 normalized = offsetToTarget.normalize();

            Set<Entity> hit = new HashSet<>();
            for (int particleIndex = 1; particleIndex <= Mth.floor(offsetToTarget.length()) + particleDelta; ++particleIndex) {
                Vec3 particlePos = source.add(normalized.scale(particleIndex));
                world.sendParticles(ParticleRegistry.ROSE_GOLD_SONIC_BOOM.get(), particlePos.x, particlePos.y, particlePos.z, 1, 0.0, 0.0, 0.0, 0.0);

                hit.addAll(world.getEntitiesOfClass(LivingEntity.class, new AABB(new BlockPos((int) particlePos.x(),
                                (int) particlePos.y(), (int) particlePos.z())).inflate(1),
                        it -> !(it.isAlliedTo(user) || (it instanceof TamableAnimal helper && helper.isOwnedBy(user)))));
            }

            hit.remove(user);

            for (Entity hitTarget : hit) {
                DamageSource damageSource = world.damageSources().sonicBoom(user);
                hitTarget.hurt(damageSource, calculateEnchantedDamage(world, stack, hitTarget, damageSource, damage));
                if (hitTarget instanceof LivingEntity living) {
                    double vertical = verticalKnockbackCoefficient * (1.0 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                    double horizontal = horizontalKnockbackCoefficient * (1.0 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                    living.push(normalized.x() * horizontal, normalized.y() * vertical, normalized.z() * horizontal);
                }
            }
        }
    }