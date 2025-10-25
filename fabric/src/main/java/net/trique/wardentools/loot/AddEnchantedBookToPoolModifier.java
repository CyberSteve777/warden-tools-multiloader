package net.trique.wardentools.loot;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Set;

public class AddEnchantedBookToPoolModifier extends ConditionalFabricLootModifier {

    private final ResourceKey<Enchantment> enchantment;
    private final int minLevel;
    private final int maxLevel;

    public AddEnchantedBookToPoolModifier(LootItemCondition[] conditionsIn, Set<ResourceLocation> tables, ResourceKey<Enchantment> enchantment, int minLevel, int maxLevel) {
        super(conditionsIn, tables);
        this.enchantment = enchantment;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context, ResourceLocation lootTable) {
        if (!checkConditions(context, lootTable)) {
            return generatedLoot;
        }
        var enchantment = context.getLevel().registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(this
                .enchantment);
        ItemStack bookStack = new ItemStack(Items.ENCHANTED_BOOK);
        bookStack.enchant(enchantment, context.getRandom().nextIntBetweenInclusive(minLevel, maxLevel));
        generatedLoot.add(bookStack);
        return generatedLoot;
    }
}
