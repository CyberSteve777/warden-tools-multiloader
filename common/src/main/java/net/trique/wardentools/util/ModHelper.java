package net.trique.wardentools.util;

import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.trique.wardentools.Constants;

public class ModHelper {
    public static ResourceLocation getLoc(String key) {
        return new ResourceLocation(Constants.MOD_ID, key);
    }

    public static String getTranslationKey(String key) {
        return Constants.MOD_ID + "." + key;
    }

    public static ItemStack getEnchantedBookStack(Enchantment enchantment, int level) {
        return EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, level));
    }

    public static ItemStack getItemWithPotion(Item baseItem, Holder<Potion> potionHolder) {
        return PotionUtils.setPotion(new ItemStack(baseItem), potionHolder.value());
    }

    public static <B extends FriendlyByteBuf, V extends Enum<V>> StreamCodec<B, V> enumStreamCodec(final Class<V> enumClass) {
        return new StreamCodec<>() {
            @Override
            public V decode(B buf) {
                return buf.readEnum(enumClass);
            }

            @Override
            public void encode(B buf, V value) {
                buf.writeEnum(value);
            }
        };
    }
}
