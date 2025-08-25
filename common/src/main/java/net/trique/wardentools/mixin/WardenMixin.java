package net.trique.wardentools.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import net.trique.wardentools.registry.EffectRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Warden.class)
public abstract class WardenMixin extends Monster {
    protected WardenMixin(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }


    @WrapOperation(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Monster;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean applyExtraDamage(Warden instance, DamageSource source, float amount, Operation<Boolean> original) {
        if (source.getEntity() instanceof LivingEntity attacker && attacker.hasEffect(EffectRegistry.SCULK_ADAPTION)) {
            return original.call(instance, source, amount * 4);
        }
        return original.call(instance, source, amount);
    }
}