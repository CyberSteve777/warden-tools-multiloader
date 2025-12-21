package net.trique.wardentools.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class AddEnchantedBookToPoolModifier extends LootModifier {
    public static final MapCodec<AddEnchantedBookToPoolModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            LootModifier.codecStart(inst)
                    .and(Enchantment.CODEC.fieldOf("enchantment").forGetter(e -> e.enchantment))
                    .and(Codec.INT.fieldOf("minLevel").forGetter(e -> e.minLevel))
                    .and(Codec.INT.fieldOf("maxLevel").forGetter(e -> e.maxLevel))
                    .apply(inst, AddEnchantedBookToPoolModifier::new));
    private final Holder<Enchantment> enchantment;
    private final int minLevel;
    private final int maxLevel;

    public AddEnchantedBookToPoolModifier(LootItemCondition[] conditionsIn, Holder<Enchantment> enchantment, int minLevel, int maxLevel) {
        super(conditionsIn);
        this.enchantment = enchantment;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext lootContext) {
        for (LootItemCondition condition : this.conditions) {
            if (!condition.test(lootContext)) {
                return generatedLoot;
            }
        }
        ItemStack bookStack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, lootContext.getRandom().nextIntBetweenInclusive(minLevel, maxLevel)));
        generatedLoot.add(bookStack);
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
