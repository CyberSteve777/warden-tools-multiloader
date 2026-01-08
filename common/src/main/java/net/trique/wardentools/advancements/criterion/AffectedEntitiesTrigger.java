package net.trique.wardentools.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.trique.wardentools.registry.TriggerTypeRegistry;

import java.util.Optional;
import java.util.Set;

public class AffectedEntitiesTrigger extends SimpleCriterionTrigger<AffectedEntitiesTrigger.TriggerInstance> {

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public <T extends Entity> void trigger(ServerPlayer player, ItemStack handheld, Set<T> entities) {
        this.trigger(player, triggerInstance -> triggerInstance.matches(handheld, entities.size()));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, ItemPredicate weapon, MinMaxBounds.Ints count) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        ItemPredicate.CODEC.fieldOf("weapon").forGetter(TriggerInstance::weapon),
                        MinMaxBounds.Ints.CODEC.optionalFieldOf("count", MinMaxBounds.Ints.ANY).forGetter(TriggerInstance::count)
                ).apply(instance, TriggerInstance::new)
        );

        public TriggerInstance(ItemPredicate item, Optional<ContextAwarePredicate> player) {
            this(player, item, MinMaxBounds.Ints.ANY);
        }

        public static Criterion<TriggerInstance> create(ItemPredicate.Builder item, MinMaxBounds.Ints count) {
            return TriggerTypeRegistry.AFFECTED_ENTITIES_TRIGGER.get().createCriterion(new TriggerInstance(Optional.empty(), item.build(), count));
        }

        public static Criterion<TriggerInstance> exactCount(ItemPredicate.Builder item, int count) {
            return create(item, MinMaxBounds.Ints.exactly(count));
        }

        public static Criterion<TriggerInstance> range(ItemPredicate.Builder item, int min, int max) {
            return create(item, MinMaxBounds.Ints.between(min, max));
        }

        public static Criterion<TriggerInstance> minCount(ItemPredicate.Builder item, int min) {
            return create(item, MinMaxBounds.Ints.atLeast(min));
        }

        public static Criterion<TriggerInstance> maxCount(ItemPredicate.Builder item, int max) {
            return create(item, MinMaxBounds.Ints.atMost(max));
        }

        public boolean matches(ItemStack stack, int entityCount) {
            return this.weapon.test(stack) && this.count.matches(entityCount);
        }

    }
}
