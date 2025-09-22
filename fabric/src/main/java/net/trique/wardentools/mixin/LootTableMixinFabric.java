package net.trique.wardentools.mixin;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.trique.wardentools.LootTableDuck;
import net.trique.wardentools.util.WTLootTableModifiersFabric;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LootTable.class)
public class LootTableMixinFabric implements LootTableDuck {

    @Unique
    ResourceLocation id;

    @Override
    public ResourceLocation warden_tools$getId() {
        return id;
    }

    @Override
    public void warden_tools$setId(ResourceLocation resourceLocation) {
        id = resourceLocation;
    }

    @Inject(method = "getRandomItems(Lnet/minecraft/world/level/storage/loot/LootContext;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;",
    at = @At("RETURN"))
    private void modifyLoot(LootContext context, CallbackInfoReturnable<ObjectArrayList<ItemStack>> cir) {
        //this is where neoforge modifies loot
        WTLootTableModifiersFabric.modifyLoot((LootTable) (Object)this,context,cir.getReturnValue());
    }
}
