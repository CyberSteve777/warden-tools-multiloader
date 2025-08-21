package net.trique.wardentools.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.trique.wardentools.item.custom.ArmorEffectItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Shadow public abstract boolean hurt(DamageSource source, float amount);

    @WrapMethod(method = "damage")
    public boolean damage(DamageSource source, float amount, Operation<Boolean> original) {
        if (source.getEntity() instanceof Warden &&
                !source.type().msgId().equals(DamageTypes.SONIC_BOOM.location().getPath())){
            if(ArmorEffectItem.isCorrectMaterial){
                amount *= 0.4f;
            }
        }

        if(source.is(DamageTypes.SONIC_BOOM)){
            if(ArmorEffectItem.isCorrectMaterial){
                System.out.println("Work");
                amount *= 0.4f;
            }
        }
        return original.call(source,amount);
    }
}