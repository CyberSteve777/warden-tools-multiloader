package net.trique.wardentools.loot;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Set;

public class AddItemModifierWithRandomAmount extends ConditionalFabricLootModifier {
    private final Item item;
    private final int min;
    private final int max;

    public AddItemModifierWithRandomAmount(LootItemCondition[] conditionsIn, Set<ResourceLocation> tables, Item item, int min, int max) {
        super(conditionsIn,tables);
        this.item = item;
        this.min = min;
        this.max = max;
    }

    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext lootContext, ResourceLocation lootTable) {
        if (!checkConditions(lootContext, lootTable)) {
            return generatedLoot;
        }
        for (LootItemCondition condition : this.conditions) {
            if(!condition.test(lootContext)) {
                return generatedLoot;
            }
        }

        int amount = lootContext.getRandom().nextIntBetweenInclusive(this.min, this.max);
        generatedLoot.add(new ItemStack(this.item, amount));
        return generatedLoot;
    }
}
