package net.trique.wardentools.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import net.trique.wardentools.effect.WardenEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Warden.class)
public abstract class WardenMixin extends Monster {
    protected WardenMixin(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }


    @Inject(method = "damage", at = @At(value = "HEAD"))
    private void applyExtraDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.getEntity() instanceof LivingEntity attacker) {
            MobEffectInstance effect = attacker.getEffect(WardenEffects.SCULK_ADAPTION);
            if (effect != null) {
                this.invulnerableTime = 0;
                super.hurt(source, amount*3);
            }
        }
    }
}