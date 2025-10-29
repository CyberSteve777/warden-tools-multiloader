package net.trique.wardentools.item.archery;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.trique.wardentools.registry.ItemRegistry;

import java.util.List;
import java.util.function.Predicate;

public class EchoLocatorItem extends BowItem {
    public EchoLocatorItem(Properties properties) {
        super(properties);
    }

    public static ItemAttributeModifiers createAttributeModifiers() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 3.0f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, 0f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        boolean bl = !findSculkArrow(user).isEmpty();
        if (!user.hasInfiniteMaterials() && !bl) {
            return InteractionResultHolder.fail(itemStack);
        } else {
            user.startUsingItem(hand);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.WARDEN_SONIC_CHARGE, SoundSource.BLOCKS, 3.0f, 1.0f);
            return InteractionResultHolder.consume(itemStack);
        }
    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            ItemStack ammo = findSculkArrow(player);

            if (!ammo.isEmpty() || player.isCreative()) {
                int i = this.getUseDuration(stack, entityLiving) - timeLeft;
                float f = getPowerForTime(i);
                if(player.isCreative() & ammo.isEmpty()){
                    ammo = new ItemStack(ItemRegistry.SCULK_ARROW.get());
                }
                if (!((double)f < 0.1)) {
                    List<ItemStack> list = draw(stack, ammo, player);
                    if (level instanceof ServerLevel serverlevel) {
                        if (!list.isEmpty()) {
                            this.shoot(serverlevel, player, player.getUsedItemHand(), stack, list, f * 3.0F, 1.0F, f == 1.0F, (LivingEntity)null);
                        }
                    }
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    protected ItemStack findSculkArrow(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(ItemRegistry.SCULK_ARROW.get())) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return itemStack -> itemStack.is(ItemRegistry.SCULK_ARROW.get());
    }
}
