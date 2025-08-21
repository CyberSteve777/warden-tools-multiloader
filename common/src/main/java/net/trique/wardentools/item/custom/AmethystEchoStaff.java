package net.trique.wardentools.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.trique.wardentools.item.WardenItems;
import net.trique.wardentools.particle.WardenParticles;

import java.util.HashSet;
import java.util.Set;

public class AmethystEchoStaff extends Item {

    public AmethystEchoStaff(Properties settings) {
        super(settings.attributes(createAttributeModifiers()));
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
        return ingredient.is(WardenItems.SCULK_SHELL);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        user.startUsingItem(hand);
        return super.use(world, user, hand);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity usr) {
        return 15;
    }

    @Override
    public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.onUseTick(world, user, stack, remainingUseTicks);

        if(getUseDuration(stack, user) - remainingUseTicks == 1) {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.WARDEN_SONIC_CHARGE, SoundSource.BLOCKS, 3.0f, 1.0f);
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (!world.isClientSide && user instanceof Player player) {
            if (!player.isCreative()){
                ItemStack echoShardStack = findEchoShard(player);

                if (!echoShardStack.isEmpty()) {
                    spawnSonicBoom(world, user);
                    echoShardStack.shrink(1);
                    player.getCooldowns().addCooldown(this, 60);
                    stack.hurtAndBreak(1, user, EquipmentSlot.MAINHAND);
                }
            }else {
            spawnSonicBoom(world, user);
            }
        }

        return super.finishUsingItem(stack, world, user);
    }

    private ItemStack findEchoShard(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(Items.ECHO_SHARD)) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    private void spawnSonicBoom(Level world, LivingEntity user) {
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.BLOCKS, 2.0f, 1.0f);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.AMETHYST_BLOCK_PLACE, SoundSource.BLOCKS, 4.0f, 1.0f);

        float heightOffset = 1.6f;
        int distance = 20;
        Vec3 target = user.position().add(user.getLookAngle().scale(distance));
        Vec3 source = user.position().add(0.0, heightOffset, 0.0);
        Vec3 offsetToTarget = target.subtract(source);
        Vec3 normalized = offsetToTarget.normalize();

        Set<Entity> hit = new HashSet<>();
        for (int particleIndex = 1; particleIndex < Mth.floor(offsetToTarget.length()) + 7; ++particleIndex) {
            Vec3 particlePos = source.add(normalized.scale(particleIndex));
            ((ServerLevel) world).sendParticles(WardenParticles.AMETHYST_SONIC_BOOM, particlePos.x, particlePos.y, particlePos.z, 1, 0.0, 0.0, 0.0, 0.0);

            hit.addAll(world.getEntitiesOfClass(LivingEntity.class, new AABB(new BlockPos((int) particlePos.x(),
                            (int) particlePos.y(), (int) particlePos.z())).inflate(1),
                    it -> !(it instanceof TamableAnimal helper && helper.isOwnedBy(user))));
        }

        hit.remove(user);

        for (Entity hitTarget : hit) {
            if(hitTarget instanceof LivingEntity living) {
                living.hurt(world.damageSources().sonicBoom(user), 10.0f);
                living.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 60, 0));
                double vertical = 0.6 * (1.0 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                double horizontal = 7.5 * (1.0 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                living.push(normalized.x() * horizontal, normalized.y() * vertical, normalized.z() * horizontal);
            }
        }
    }
}