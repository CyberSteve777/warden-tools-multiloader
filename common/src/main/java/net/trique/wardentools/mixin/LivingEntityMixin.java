package net.trique.wardentools.mixin;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.trique.wardentools.effect.WardenEffects;
import net.trique.wardentools.item.WardenArmorMaterials;
import net.trique.wardentools.item.custom.ArmorEffectItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract boolean isMobOrPlayer();

    @Inject(method = "onStatusEffectApplied",at = @At(value = "RETURN"))
    private void overrideEffect(MobEffectInstance effect, Entity source, CallbackInfo ci){
        if(source instanceof Player player){
            if(player.hasEffect(MobEffects.DAMAGE_RESISTANCE) && ArmorEffectItem.getApplied()){
                System.out.println("Work");
                ArmorEffectItem.setApplied(false);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "canHaveStatusEffect", cancellable = true)
    private void canHaveDarkness(MobEffectInstance effect, CallbackInfoReturnable<Boolean> cir){
        if((Object)this instanceof Player player){
            if(ArmorEffectItem.hasCorrectArmorOn(WardenArmorMaterials.WARDEN.value(), player)
                    && effect.getEffect().equals(MobEffects.DARKNESS)){
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void clearDarknessBlindnessIfSculkAdapted(CallbackInfo ci) {
        LivingEntity self = (LivingEntity)(Object)this;

        if (!self.level().isClientSide()) {
            if (self.hasEffect(WardenEffects.SCULK_ADAPTION)) {
                if (self.hasEffect(MobEffects.DARKNESS)) {
                    self.removeEffect(MobEffects.DARKNESS);
                }
                if (self.hasEffect(MobEffects.BLINDNESS)) {
                    self.removeEffect(MobEffects.BLINDNESS);
                }
            }
        }
    }


}