package net.trique.wardentools.item.material;

import com.google.common.base.Suppliers;
import java.util.function.Supplier;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.trique.wardentools.registry.ItemRegistry;
import net.trique.wardentools.util.WTBlockTags;

public enum WardenToolMaterials implements Tier {
    SCULKIFIED(3, 1796, 8.5f, 3.5f, 12, () -> Ingredient.of(ItemRegistry.ECHO_INGOT.get())),
    WARDEN(4, 3001, 11.0f, 6.0f, 21, () -> Ingredient.of(ItemRegistry.WARDEN_INGOT.get()));

    private final int level;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    WardenToolMaterials(int level, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.level = level;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = Suppliers.memoize(repairIngredient::get);
    }

    @Override
    public int getUses() {
        return this.itemDurability;
    }

    @Override
    public float getSpeed() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    @Override
    public int getLevel() {
        return this.level;
    }
    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}