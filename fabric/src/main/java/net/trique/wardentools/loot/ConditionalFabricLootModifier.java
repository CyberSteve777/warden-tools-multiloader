package net.trique.wardentools.loot;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Set;

public abstract class ConditionalFabricLootModifier implements FabricLootModifier {

    private final LootItemCondition[] conditions;
    private final Set<ResourceLocation> allowedTables;

    protected ConditionalFabricLootModifier(LootItemCondition[] conditions, Set<ResourceLocation> allowedTables) {
        this.conditions = conditions;
        this.allowedTables = allowedTables;
    }

    protected boolean checkConditions(LootContext context, ResourceLocation resourceLocation) {
        if (resourceLocation == null || !allowedTables.contains(resourceLocation)) return false;
        for (LootItemCondition condition : this.conditions) {
            if (!condition.test(context)) {
                return false;
            }
        }
        return true;
    }
}
