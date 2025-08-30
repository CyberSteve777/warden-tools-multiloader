package net.trique.wardentools.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;


public class AddItemToWardenLootModifier extends LootModifier {
    public static final MapCodec<AddItemToWardenLootModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            LootModifier.codecStart(inst)
                    .and(BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(e -> e.item))
                    .and(Codec.FLOAT.fieldOf("baseChance").forGetter(e -> e.baseChance))
                    .and(Codec.FLOAT.fieldOf("perLevel").forGetter(e ->e.perLevel))
                    .and(Codec.INT.fieldOf("min").forGetter(e -> e.min))
                    .and(Codec.INT.fieldOf("max").forGetter(e ->e.max))
                    .apply(inst, AddItemToWardenLootModifier::new));
    private final Item item;
    private final float baseChance;
    private final float perLevel;
    private final int min;
    private final int max;

    public AddItemToWardenLootModifier(LootItemCondition[] conditionsIn, Item item, float baseChance, float perLevel, int min, int max) {
        super(conditionsIn);
        this.item = item;
        this.baseChance = baseChance;
        this.perLevel = perLevel;
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
        if (lootContext.getParam(LootContextParams.ATTACKING_ENTITY) instanceof LivingEntity entity) {
            var registryLookup = lootContext.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            int level = EnchantmentHelper.getEnchantmentLevel(registryLookup.getOrThrow(Enchantments.LOOTING), entity);
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

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
