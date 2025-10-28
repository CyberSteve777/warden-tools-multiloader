package net.trique.wardentools.loot;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.trique.wardentools.util.WTEnchantmentHelper;

import java.util.Set;


public class AddItemToWardenLootModifier extends ConditionalFabricLootModifier {

    private final Item item;
    private final float baseChance;
    private final float perLevel;
    private final int min;
    private final int max;

    public AddItemToWardenLootModifier(LootItemCondition[] conditionsIn, Set<ResourceLocation> tables, Item item, float baseChance, float perLevel, int min, int max) {
        super(conditionsIn,tables);
        this.item = item;
        this.baseChance = baseChance;
        this.perLevel = perLevel;
        this.min = min;
        this.max = max;
    }

    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext lootContext, ResourceLocation lootTable) {
        if (!checkConditions(lootContext, lootTable)) {
            return generatedLoot;
        }
        if (lootContext.getParamOrNull(LootContextParams.ATTACKING_ENTITY) instanceof LivingEntity entity) {
            int level = WTEnchantmentHelper.getMaxLevelForIncreaseEntityDrop(entity.getMainHandItem(), lootContext);
            float lootingMultiplier = baseChance + perLevel * level;
            for (int count = max; count >= min; count--) {
                float countMultiplier = 1.0f / count;
                float chance = lootingMultiplier * countMultiplier;
                if (lootContext.getRandom().nextFloat() < chance) {
                    generatedLoot.add(new ItemStack(item, count));
                    return generatedLoot;
                }
            }
        }
        return generatedLoot;
    }
}
