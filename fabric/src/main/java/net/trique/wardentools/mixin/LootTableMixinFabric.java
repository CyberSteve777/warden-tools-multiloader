package net.trique.wardentools.mixin;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.trique.wardentools.Constants;
import net.trique.wardentools.LootTableDuck;
import net.trique.wardentools.util.WTGlobalLootTableUtils;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Debug(export = true)
@Mixin(LootTable.class)
public abstract class LootTableMixinFabric implements LootTableDuck {
//
//    @Shadow @Final private Optional<ResourceLocation> randomSequence;
//
//    @Shadow protected abstract ObjectArrayList<ItemStack> getRandomItems(LootContext context);

    @Unique
    ResourceLocation id;

    @Override
    public ResourceLocation warden_tools$getId() {
        return id;
    }

    @Override
    public void warden_tools$setId(ResourceLocation resourceLocation) {
        Constants.LOGGER.info("Setting id for LootTable: {}", (LootTable)(Object) this);
        Constants.LOGGER.info("Set ResourceLocation: {}", resourceLocation);
        id = resourceLocation;
    }

    @Inject(method = "getRandomItems(Lnet/minecraft/world/level/storage/loot/LootContext;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;",
    at = @At("RETURN"))
    private void modifyLoot(LootContext context, CallbackInfoReturnable<ObjectArrayList<ItemStack>> cir) {
        //this is where neoforge modifies loot
        Constants.LOGGER.info("ResourceLocation: {}", id);
        WTGlobalLootTableUtils.modifyLoot((LootTable) (Object)this,context,cir.getReturnValue());
    }

//    //todo is there a better way to do this?
//
//    /**
//     * @author Tfar
//     * @reason patch for fabric GLMs
//     */
//    @Overwrite
//    public void getRandomItems(LootParams params, long seed, Consumer<ItemStack> output) {
//        this.getRandomItems((new LootContext.Builder(params)).withOptionalRandomSeed(seed).create(this.randomSequence)).forEach(output);
//    }
}
