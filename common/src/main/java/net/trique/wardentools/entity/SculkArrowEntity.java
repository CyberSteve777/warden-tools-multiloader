package net.trique.wardentools.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.trique.wardentools.registry.EntityRegistry;
import net.trique.wardentools.registry.ItemRegistry;

import java.util.HashSet;
import java.util.Set;

public class SculkArrowEntity extends Arrow {
    private double base_radius = 4;

    public SculkArrowEntity(EntityType<? extends Arrow> entityType, Level world) {
        super(entityType, world);
    }

    public SculkArrowEntity(Level world, LivingEntity owner) {
        super(EntityRegistry.SCULK_ARROW.get(), world);
        this.setOwner(owner);
        if (owner.getMainHandItem().is(ItemRegistry.SCULKIFIED_BOW.get())){
            this.setNoGravity(true);
            double multiplier = 3;
            base_radius *=  multiplier;
        }
        this.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
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
        Set<LivingEntity> hit = new HashSet<>();


        if (hitresult$type == HitResult.Type.ENTITY) {
            EntityHitResult entityhitresult = (EntityHitResult)result;
            Entity entity = entityhitresult.getEntity();
            if (entity.getType().is(EntityTypeTags.REDIRECTABLE_PROJECTILE) && entity instanceof Projectile) {
                Projectile projectile = (Projectile)entity;
                projectile.deflect(ProjectileDeflection.AIM_DEFLECT, this.getOwner(), this.getOwner(), true);
            }else if(entity instanceof LivingEntity victim){
                hit.addAll(this.level().getEntitiesOfClass(LivingEntity.class, new AABB(new BlockPos((int) victim.getX(),
                                (int) victim.getY(), (int) victim.getZ())).inflate(base_radius)));
                hit.remove((LivingEntity) this.getOwner());
                hit.forEach(living -> living.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100)));
            }
            this.onHitEntity(entityhitresult);
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, result.getLocation(), GameEvent.Context.of(this, (BlockState)null));
        } else if (hitresult$type == HitResult.Type.BLOCK) {
            BlockHitResult blockhitresult = (BlockHitResult)result;
            this.onHitBlock(blockhitresult);
            BlockPos blockpos = blockhitresult.getBlockPos();
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, blockpos, GameEvent.Context.of(this, this.level().getBlockState(blockpos)));
            hit.addAll(this.level().getEntitiesOfClass(LivingEntity.class, new AABB(new BlockPos(blockpos.getX(),
                    blockpos.getY(), blockpos.getZ())).inflate(base_radius)));
            hit.remove((LivingEntity) this.getOwner());
            hit.forEach(living -> living.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100)));
        }
    }
}