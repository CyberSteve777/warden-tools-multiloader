    package net.trique.wardentools.item.staff;

    import net.minecraft.core.BlockPos;
    import net.minecraft.server.level.ServerLevel;
    import net.minecraft.server.level.ServerPlayer;
    import net.minecraft.sounds.SoundEvents;
    import net.minecraft.util.Mth;
    import net.minecraft.world.damagesource.DamageSource;
    import net.minecraft.world.entity.Entity;
    import net.minecraft.world.entity.LivingEntity;
    import net.minecraft.world.entity.ai.attributes.Attributes;
    import net.minecraft.world.item.ItemStack;
    import net.minecraft.world.phys.AABB;
    import net.minecraft.world.phys.Vec3;
    import net.trique.wardentools.registry.ItemRegistry;
    import net.trique.wardentools.registry.ParticleRegistry;
    import net.trique.wardentools.registry.TriggerTypeRegistry;
    import net.trique.wardentools.util.WTEnchantmentHelper;

    import java.util.HashSet;
    import java.util.Set;

    public class RoseGoldEchoStaffItem extends EchoStaffItem {


        public RoseGoldEchoStaffItem(Properties settings, int cooldown, float distance, float damage, float horizontalKnockbackCoefficient, float verticalKnockbackCoefficient) {
            super(settings, cooldown, distance, damage, horizontalKnockbackCoefficient, verticalKnockbackCoefficient);
        }

        @Override
        protected void spawnSonicBoom(ItemStack stack, ServerLevel world, LivingEntity user) {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.WARDEN_SONIC_BOOM, user.getSoundSource(), 5.0f, 1.0f);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.COPPER_STEP, user.getSoundSource(), 4.0f, 1.0f);
            Vec3 source = user.getEyePosition();
            float enhanced_distance = calculateFinalDistance(stack, world, distance);
            Vec3 target = source.add(user.getLookAngle().scale(enhanced_distance));
            Vec3 offsetToTarget = target.subtract(source);
            Vec3 normalized = offsetToTarget.normalize();

            Set<Entity> hit = new HashSet<>();
            for (int particleIndex = 1; particleIndex <= Mth.floor(offsetToTarget.length()) + 7; ++particleIndex) {
                Vec3 particlePos = source.add(normalized.scale(particleIndex));
                world.sendParticles(ParticleRegistry.ROSE_GOLD_SONIC_BOOM.get(), particlePos.x, particlePos.y, particlePos.z, 1, 0.0, 0.0, 0.0, 0.0);

                hit.addAll(world.getEntities(user, new AABB(new BlockPos((int) particlePos.x(),
                                (int) particlePos.y(), (int) particlePos.z())).inflate(1),
                        it -> !(it.isAlive() && it.isAlliedTo(user))));
            }
            for (Entity hitTarget : hit) {
                DamageSource damageSource = world.damageSources().sonicBoom(user);
                if (hitTarget instanceof LivingEntity living) {
                    hitTarget.hurt(damageSource, calculateEnchantedDamage(world, stack, hitTarget, damageSource, damage));
                    float vertical = WTEnchantmentHelper.modifyKnockback(world, stack, living, damageSource, verticalKnockbackCoefficient) * (1.0f - (float) living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                    float horizontal = WTEnchantmentHelper.modifyKnockback(world, stack, living, damageSource, horizontalKnockbackCoefficient) * (1.0f - (float) living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                    living.push(normalized.x() * horizontal, normalized.y() * vertical, normalized.z() * horizontal);
                }
            }
            if (user instanceof ServerPlayer player) {
                TriggerTypeRegistry.AFFECTED_ENTITIES_TRIGGER.get().trigger(player, stack, hit);
            }
        }

        @Override
        public boolean isValidRepairItem(ItemStack stack, ItemStack ingredient) {
            return ingredient.is(ItemRegistry.ROSE_GOLD_INGOT.get());
        }
        @Override
        public int getEnchantmentValue() {
            return 20;
        }
    }