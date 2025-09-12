package net.trique.wardentools.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class AddItemModiferWithRandomAmount extends LootModifier {
    public static final MapCodec<AddItemModiferWithRandomAmount> CODEC = RecordCodecBuilder.mapCodec(inst ->
            LootModifier.codecStart(inst)
                    .and(BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(e -> e.item))
                    .and(Codec.INT.fieldOf("min").forGetter(e -> e.min))
                    .and(Codec.INT.fieldOf("max").forGetter(e ->e.max))
                    .apply(inst, AddItemModiferWithRandomAmount::new));
    private final Item item;
    private final int min;
    private final int max;

    public AddItemModiferWithRandomAmount(LootItemCondition[] conditionsIn, Item item, int min, int max) {
        super(conditionsIn);
        this.item = item;
        this.min = min;
        this.max = max;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext lootContext) {
            for (LootItemCondition condition : this.conditions) {
            if(!condition.test(lootContext)) {
                return generatedLoot;
            }
        }

        int amount = lootContext.getRandom().nextIntBetweenInclusive(this.min, this.max);
        generatedLoot.add(new ItemStack(this.item, amount));
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
