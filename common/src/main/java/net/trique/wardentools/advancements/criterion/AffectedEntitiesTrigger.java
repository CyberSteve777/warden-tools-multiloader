package net.trique.wardentools.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.trique.wardentools.registry.TriggerTypeRegistry;

import java.util.Optional;
import java.util.Set;

public class AffectedEntitiesTrigger extends SimpleCriterionTrigger<AffectedEntitiesTrigger.TriggerInstance> {

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, Set<Entity> entities) {
        this.trigger(player, triggerInstance -> triggerInstance.matches(entities.size()));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, MinMaxBounds.Ints count) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        MinMaxBounds.Ints.CODEC.optionalFieldOf("count", MinMaxBounds.Ints.ANY).forGetter(TriggerInstance::count)
                ).apply(instance, TriggerInstance::new)
        );

        public TriggerInstance(Optional<ContextAwarePredicate> player) {
            this(player, MinMaxBounds.Ints.ANY);
        }

        public static Criterion<TriggerInstance> create(MinMaxBounds.Ints count) {
            return TriggerTypeRegistry.AFFECTED_ENTITIES_TRIGGER.get().createCriterion(new TriggerInstance(Optional.empty(), count));
        }

        public static Criterion<TriggerInstance> exactCount(int count) {
            return create(MinMaxBounds.Ints.exactly(count));
        }

        public static Criterion<TriggerInstance> range(int min, int max) {
            return create(MinMaxBounds.Ints.between(min, max));
        }

        public static Criterion<TriggerInstance> minCount(int min) {
            return create(MinMaxBounds.Ints.atLeast(min));
        }

        public static Criterion<TriggerInstance> maxCount(int max) {
            return create(MinMaxBounds.Ints.atMost(max));
        }

        public boolean matches(int entityCount) {
            return this.count.matches(entityCount);
        }

    }
}
