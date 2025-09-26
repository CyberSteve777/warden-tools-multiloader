package net.trique.wardentools.mixin;

import com.mojang.serialization.DynamicOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.trique.wardentools.util.WTLootTableModifiersFabric;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LootDataType.class)
@Debug(export = true)
public class LootDataTypeMixin {
    @Inject(method = "deserialize",at = @At("RETURN"))
    private void addLootTableID(ResourceLocation resourceLocation, DynamicOps<Object> ops, Object value, CallbackInfoReturnable<Optional<Object>> cir) {
        WTLootTableModifiersFabric.injectID(cir.getReturnValue(),resourceLocation);
    }
}
