package net.trique.wardentools.item.staff;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.trique.wardentools.registry.ParticleRegistry;
import net.trique.wardentools.registry.TriggerTypeRegistry;

import java.util.HashSet;
import java.util.Set;

public class EnderEchoStaffItem extends EchoStaffItem {


    public EnderEchoStaffItem(Properties settings, int cooldown, int distance, float damage, float horizontalKnockbackCoefficient, float verticalKnockbackCoefficient) {
        super(settings, cooldown, distance, damage, horizontalKnockbackCoefficient, verticalKnockbackCoefficient);
    }

    @Override
    protected void spawnSonicBoom(ItemStack stack, ServerLevel world, LivingEntity user) {
        world.playSound(null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.WARDEN_SONIC_BOOM, user.getSoundSource(), 5.0f, 1.0f);
        Vec3 source = user.getEyePosition();
        float enhanced_distance = calculateFinalDistance(stack, world, distance);
        Vec3 target = source.add(user.getLookAngle().scale(enhanced_distance));
        Vec3 offsetToTarget = target.subtract(source);
        Vec3 normalized = offsetToTarget.normalize();

        Set<Entity> hit = new HashSet<>();
        for (int i = 1; i <= Mth.floor(offsetToTarget.length()) + 7; ++i) {
            Vec3 pos = source.add(normalized.scale(i));
            world.sendParticles(ParticleRegistry.ENDER_SONIC_BOOM.get(),
                    pos.x, pos.y, pos.z, 1, 0.0, 0.0, 0.0, 0.0);
            hit.addAll(world.getEntities(user,
                    new AABB(BlockPos.containing(pos)).inflate(1),
                    it -> !(it.isAlive() && it.isAlliedTo(user))));
        }
        for (Entity hitTarget : hit) {
            DamageSource damageSource = world.damageSources().sonicBoom(user);
            if (hitTarget instanceof LivingEntity living) {
                hitTarget.hurt(world.damageSources().sonicBoom(user), calculateEnchantedDamage(world, stack, hitTarget, damageSource, damage));
                Vec3 originalPos = living.position();
                for (int j = 0; j < 16; ++j) {
                    double dx = living.getX() + (living.getRandom().nextDouble() - 0.5) * 32.0;
                    double dy = Mth.clamp(
                            living.getY() + (double)(living.getRandom().nextInt(16) - 8),
                            world.getMinBuildHeight(),
                            world.getMinBuildHeight() + world.getLogicalHeight() - 1);
                    double dz = living.getZ() + (living.getRandom().nextDouble() - 0.5) * 32.0;
                    if (living.isPassenger()) {
                        living.stopRiding();
                    }
                    if (living.randomTeleport(dx, dy, dz, true)) {
                        world.gameEvent(GameEvent.TELEPORT, originalPos, Context.of(living));
                        world.playSound(null, dx, dy, dz,
                                SoundEvents.PLAYER_TELEPORT, SoundSource.BLOCKS, 5.0F, 1.0F);
                        world.sendParticles(ParticleTypes.PORTAL, dx, dy, dz, 40, 0.5, 0.5, 0.5, 0.1);
                        living.resetFallDistance();
                        break;
                    }
                }
            }
        }
        if (user instanceof ServerPlayer player) {
            TriggerTypeRegistry.AFFECTED_ENTITIES_TRIGGER.get().trigger(player, stack, hit);
        }
    }
    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack ingredient) {
        return ingredient.is(Items.ENDER_EYE);
    }
}