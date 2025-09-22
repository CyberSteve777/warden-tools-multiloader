package net.trique.wardentools.loot;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Set;

public class AddItemModifier extends ConditionalFabricLootModifier {

    private final Item item;
    private final int count;

    public AddItemModifier(LootItemCondition[] conditionsIn, Set<ResourceLocation> tables, Item item, int count) {
        super(conditionsIn,tables);
        this.item = item;
        this.count = count;
    }

    public AddItemModifier(LootItemCondition[] conditionsIn, Set<ResourceLocation> tables, Item item) {
        this(conditionsIn,tables, item, 1);
    }

    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext lootContext, ResourceLocation lootTable) {
        for (LootItemCondition condition : this.conditions) {
            if(!condition.test(lootContext)) {
                return generatedLoot;
            }
        }
        generatedLoot.add(new ItemStack(this.item, this.count));
        return generatedLoot;
    }
}