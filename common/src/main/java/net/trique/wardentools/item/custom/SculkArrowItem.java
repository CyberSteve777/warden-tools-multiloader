package net.trique.wardentools.item.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.trique.wardentools.entity.WardenEntities;
import net.trique.wardentools.entity.custom.SculkArrowEntity;
import org.jetbrains.annotations.Nullable;

public class SculkArrowItem extends ArrowItem {
    public SculkArrowItem(Properties settings) {
        super(settings);
    }

    @Override
    public AbstractArrow createArrow(Level world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack shotFrom) {
        SculkArrowEntity arrow = new SculkArrowEntity(world, shooter);
        arrow.setBaseDamage(4);
        arrow.setCritArrow(true);
        return arrow;
    }
}