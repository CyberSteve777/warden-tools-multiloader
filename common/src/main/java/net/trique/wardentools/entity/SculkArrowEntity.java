package net.trique.wardentools.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.*;
import net.trique.wardentools.Constants;
import net.trique.wardentools.registry.EntityRegistry;
import net.trique.wardentools.registry.ItemRegistry;
import net.trique.wardentools.registry.TriggerTypeRegistry;

import java.util.HashSet;
import java.util.Set;

public class SculkArrowEntity extends Arrow {
    private double base_radius = 4;
    private int life = 0;
    private boolean hit = false;

    public SculkArrowEntity(EntityType<? extends Arrow> entityType, Level world) {
        super(entityType, world);
    }

    public SculkArrowEntity(Level world, LivingEntity owner) {
        super(EntityRegistry.SCULK_ARROW.get(), world);
        this.setOwner(owner);
        if (owner.getMainHandItem().is(ItemRegistry.ECHO_LOCATOR.get())) {
            this.setNoGravity(true);
            double multiplier = 3;
            base_radius *= multiplier;
        }
        this.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
    }

    @Override
    public void tick() {
        super.tick();
        ++life;
        if (life >= 1200 && !hit) discard();
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ItemRegistry.SCULK_ARROW.get());
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ItemRegistry.SCULK_ARROW.get());
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        target.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 100, 0));
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
        this.level().playSound(null, target.blockPosition(),
                SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS,
                1.0F, 1.0F);
    }

    @Override
    protected void onHit(HitResult result) {
        HitResult.Type hitresult$type = result.getType();
        Set<Entity> victims = new HashSet<>();

        if (hitresult$type == HitResult.Type.ENTITY) {
            EntityHitResult entityhitresult = (EntityHitResult) result;
            Entity hit_entity = entityhitresult.getEntity();
            if (hit_entity.getType().is(EntityTypeTags.REDIRECTABLE_PROJECTILE) && hit_entity instanceof Projectile projectile) {
                projectile.deflect(ProjectileDeflection.AIM_DEFLECT, this.getOwner(), this.getOwner(), true);
            } else if (hit_entity instanceof LivingEntity victim) {
                hit = true;
//                Vec3 hit_location = entityhitresult.getLocation();
//                shriek(hit_location.add(0.0, victim.getBbHeight() + 0.3, 0.0));
                victims.addAll(this.level().getEntitiesOfClass(LivingEntity.class, new AABB(new BlockPos((int) victim.getX(),
                        (int) victim.getY(), (int) victim.getZ())).inflate(base_radius)));
                victims.remove(this.getOwner());
                victims.forEach(entity -> {
                    if (entity instanceof LivingEntity living)
                        living.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100));
                });
            }
            this.onHitEntity(entityhitresult);
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, result.getLocation(), GameEvent.Context.of(this, (BlockState) null));
            this.level().playSound(null, hit_entity.getX(), hit_entity.getY(), hit_entity.getZ(), SoundEvents.SCULK_SHRIEKER_SHRIEK, this.getSoundSource(), 2.0f, 0.6f + this.level().getRandom().nextFloat() * 0.4f);

        } else if (hitresult$type == HitResult.Type.BLOCK) {
            hit = true;
            BlockHitResult blockhitresult = (BlockHitResult) result;
            this.onHitBlock(blockhitresult);
//            shriek(blockhitresult.getLocation());
            BlockPos blockpos = blockhitresult.getBlockPos();
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, blockpos, GameEvent.Context.of(this, this.level().getBlockState(blockpos)));
            victims.addAll(this.level().getEntitiesOfClass(LivingEntity.class, new AABB(new BlockPos(blockpos.getX(),
                    blockpos.getY(), blockpos.getZ())).inflate(base_radius)));
            victims.remove(this.getOwner());
            victims.forEach(entity -> {
                if (entity instanceof LivingEntity living)
                    living.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100));
            });
            this.level().playSound(null, blockpos.getX(), blockpos.getY(), blockpos.getZ(), SoundEvents.SCULK_SHRIEKER_SHRIEK, this.getSoundSource(), 2.0f, 0.6f + this.level().getRandom().nextFloat() * 0.4f);


        }
        if (this.getOwner() instanceof ServerPlayer player) {
            TriggerTypeRegistry.AFFECTED_ENTITIES_TRIGGER.get().trigger(player, this.getPickupItem(), victims);
        }
    }

//    protected void shriek(Vec3 pos) {
//        if (this.level() instanceof ServerLevel level) {
//            BlockPos containingPos = BlockPos.containing(pos.x, pos.y, pos.z);
//            for(int i = 0; i < 10; ++i) {
//                level.sendParticles(new ShriekParticleOption(i * 5), pos.x, pos.y, pos.z, 1, 0.0, 0.0, 0.0, 0.0);
//            }
//            BlockState blockState = level.getBlockState(containingPos);
//            boolean flag = blockState.hasProperty(BlockStateProperties.WATERLOGGED) && blockState.getValue(BlockStateProperties.WATERLOGGED);
//            if (!flag) {
//                level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.SCULK_SHRIEKER_SHRIEK, this.getSoundSource(), 2.0f, 0.6f + level.getRandom().nextFloat() * 0.4f);
//            }
//        }
//    }
}