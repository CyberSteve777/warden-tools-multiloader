package net.trique.wardentools.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.trique.wardentools.registry.EntityRegistry;
import net.trique.wardentools.registry.ItemRegistry;

public class SculkArrowEntity extends Arrow {
    public SculkArrowEntity(EntityType<? extends Arrow> entityType, Level world) {
        super(entityType, world);
    }


    public SculkArrowEntity(Level world, LivingEntity owner) {
        super(EntityRegistry.SCULK_ARROW.get(), world);
        this.setOwner(owner);
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
}