package net.trique.wardentools.item.staff;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.trique.wardentools.item.util.ISonicBoomItem;
import net.trique.wardentools.registry.ItemRegistry;
import net.trique.wardentools.registry.TriggerTypeRegistry;
import net.trique.wardentools.util.WTEnchantmentHelper;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class EchoStaffItem extends Item implements ISonicBoomItem {
    protected int cooldown;
    protected int distance;
    protected float damage;
    protected float horizontalKnockbackCoefficient;
    protected float verticalKnockbackCoefficient;

    public EchoStaffItem(Properties settings, int cooldown, int distance,
                         float damage, float horizontalKnockbackCoefficient,
                         float verticalKnockbackCoefficient) {
        super(settings.attributes(createAttributeModifiers()));
        this.cooldown = cooldown;
        this.distance = distance;
        this.damage = damage;
        this.horizontalKnockbackCoefficient = horizontalKnockbackCoefficient;
        this.verticalKnockbackCoefficient = verticalKnockbackCoefficient;
    }

    public static ItemAttributeModifiers createAttributeModifiers() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 3.0f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, 0f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack ingredient) {
        return ingredient.is(ItemRegistry.ECHO_INGOT.get());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        boolean bl = !findEchoShard(user).isEmpty();
        if (!user.hasInfiniteMaterials() && !bl) {
            return InteractionResultHolder.fail(itemStack);
        } else {
            user.startUsingItem(hand);
            return InteractionResultHolder.consume(itemStack);
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity usr) {
        return 72000;
    }

    @Override
    public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.onUseTick(world, user, stack, remainingUseTicks);

        if (getUseDuration(stack, user) - remainingUseTicks == 1) {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.WARDEN_SONIC_CHARGE, user.getSoundSource(), 3.0f, 1.0f);
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int timeCharged) {
        if (world instanceof ServerLevel serverLevel && user instanceof Player player) {
            ItemStack echoShardStack = findEchoShard(player);
            int tick_progress = this.getUseDuration(stack, user) - timeCharged;
            float progress = getChargePowerForTime(tick_progress);
            if (progress >= 0.5f) {
                spawnSonicBoom(stack, serverLevel, user);
                if (!player.hasInfiniteMaterials()) {
                    echoShardStack.shrink(1);
                    player.getCooldowns().addCooldown(this, WTEnchantmentHelper.getCooldown(serverLevel, stack, cooldown));
                    stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                }
                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    protected ItemStack findEchoShard(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(Items.ECHO_SHARD)) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    protected void spawnSonicBoom(ItemStack stack, ServerLevel world, LivingEntity user) {
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.WARDEN_SONIC_BOOM, user.getSoundSource(), 5.0f, 1.0f);

        Vec3 source = user.getEyePosition();
        float enhanced_distance = calculateFinalDistance(stack, world, distance);
        Vec3 target = source.add(user.getLookAngle().scale(enhanced_distance));
        Vec3 offsetToTarget = target.subtract(source);
        Vec3 normalized = offsetToTarget.normalize();

        Set<Entity> hit = new HashSet<>();
        for (int particleIndex = 1; particleIndex <= Mth.floor(offsetToTarget.length()) + 7; ++particleIndex) {
            Vec3 particlePos = source.add(normalized.scale(particleIndex));
            world.sendParticles(ParticleTypes.SONIC_BOOM, particlePos.x, particlePos.y, particlePos.z, 1, 0.0, 0.0, 0.0, 0.0);

            hit.addAll(world.getEntities(user, new AABB(new BlockPos((int) particlePos.x(),
                            (int) particlePos.y(), (int) particlePos.z())).inflate(1),
                    it -> !(it.isAlive() && it.isAlliedTo(user))));
        }

        for (Entity hitTarget : hit) {
            DamageSource damageSource = world.damageSources().sonicBoom(user);
            hitTarget.hurt(damageSource, calculateEnchantedDamage(world, stack, hitTarget, damageSource, damage));
            if (hitTarget instanceof LivingEntity living) {
                float vertical = WTEnchantmentHelper.modifyKnockback(world, stack, living, damageSource, verticalKnockbackCoefficient * (1.0f - (float) living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));
                float horizontal = WTEnchantmentHelper.modifyKnockback(world, stack, living, damageSource, horizontalKnockbackCoefficient * (1.0f - (float) living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));
                living.push(normalized.x() * horizontal, normalized.y() * vertical, normalized.z() * horizontal);
            }
        }
        if (user instanceof ServerPlayer player) {
            TriggerTypeRegistry.AFFECTED_ENTITIES_TRIGGER.get().trigger(player, stack, hit);
        }

    }
}