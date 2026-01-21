package net.trique.wardentools.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.trique.wardentools.registry.EnchantmentRegistry;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;

public class WTEnchantmentHelper extends EnchantmentHelper {
    public static float getRangeBonus(ServerLevel serverLevel, ItemStack tool, float distance) {
        MutableFloat mutablefloat = new MutableFloat(distance);
        runIterationOnItem(tool, ((enchantmentHolder, level) -> {
            enchantmentHolder.value().modifyUnfilteredValue(EnchantmentRegistry.INCREASE_RANGE.get(),
                    serverLevel.getRandom(), level, mutablefloat);
        }));
        return mutablefloat.floatValue();
    }

    public static int getCooldown(ServerLevel serverLevel, ItemStack tool, int baseCooldown) {
        MutableFloat mutableFloat = new MutableFloat(1);
        runIterationOnItem(tool, ((holder, level) -> holder.value().modifyUnfilteredValue(
                EnchantmentRegistry.REDUCE_COOLDOWN.get(), serverLevel.getRandom(), level, mutableFloat
        )));
        return Mth.floor(baseCooldown * mutableFloat.floatValue());
    }

    public static int getMaxLevelForIncreaseEntityDrop(ItemStack tool, LootContext context) {
        MutableInt mutableInt = new MutableInt();
        TagKey<Enchantment> to_check = TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(
                "c", "increase_entity_drops"));
        runIterationOnItem(tool, (enchantmentHolder, level) -> {
            if (enchantmentHolder.is(to_check)) {
                var conditions = enchantmentHolder.value().effects().get(EnchantmentEffectComponents.EQUIPMENT_DROPS);
                if (conditions != null && conditions.stream().allMatch(
                        condition -> condition.matches(context))) {
                    mutableInt.setValue(Math.max(mutableInt.intValue(), level));
                }
            }
        });
        return mutableInt.intValue();
    }

    public static int getMaxLevelForIncreaseBlockDrop(ItemStack tool, LootContext context) {
        MutableInt mutableInt = new MutableInt();
        TagKey<Enchantment> to_check = TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(
                "c", "increase_block_drops"));
        runIterationOnItem(tool, (enchantmentHolder, level) -> {
            if (enchantmentHolder.is(to_check)) {
                mutableInt.setValue(Math.max(mutableInt.intValue(), level));
            }
        });
        return mutableInt.intValue();
    }
}
