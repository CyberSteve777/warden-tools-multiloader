package net.trique.wardentools.item.custom;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.trique.wardentools.item.WardenArmorMaterials;

public class ArmorEffectItem extends ArmorItem {
    private static final int effectDuration = MobEffectInstance.INFINITE_DURATION;
    private static final int amplifier = 0;
    private static boolean appliedByArmor;
    public static boolean isCorrectMaterial = false;
    private final Holder<MobEffect> effect;

    public ArmorEffectItem(Holder<ArmorMaterial> material, Type type, Properties settings, Holder<MobEffect> effect) {
        super(material, type, settings);
        this.effect = effect;
    }

    public static boolean getApplied(){
        return appliedByArmor;
    }
    public static void setApplied(boolean apl){
        appliedByArmor = apl;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (!world.isClientSide) {
            if (entity instanceof Player player) {
                if (hasFullSuitOfArmorOn(player)) {
                    isCorrectMaterial = hasCorrectArmorOn(material.value(),player);
                    evaluateArmorEffects(player);
                }else{
                    isCorrectMaterial = hasCorrectArmorOn(material.value(),player);
                    evaluateArmorEffects(player);
                }
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);

        return !helmet.isEmpty() && !chestplate.isEmpty() && !leggings.isEmpty() && !boots.isEmpty();
    }

    private void evaluateArmorEffects(Player player) {
        if (isCorrectMaterial) {
            addStatusEffect(player);
            appliedByArmor = true;
            player.removeEffect(MobEffects.DARKNESS);
        }else {
            if(player.hasEffect(this.effect) && appliedByArmor){
                if((player.getActiveEffectsMap().get(this.effect).getDuration() == MobEffectInstance.INFINITE_DURATION)){
                    player.removeEffect(this.effect);
                    appliedByArmor = false;
                    player.forceAddEffect(new MobEffectInstance(this.effect,300,0,false,false,false),player.damageSources().generic().getDirectEntity());
                }
            }
        }
    }

    public static boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chestplate = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();

        // Handle any non-armor items in armor slots
        if (!(boots instanceof ArmorItem)
                || !(leggings instanceof ArmorItem)
                || !(chestplate instanceof ArmorItem)
                || !(helmet instanceof ArmorItem)) {
            isCorrectMaterial = false;
            return false;
        } else if(!material.equals(WardenArmorMaterials.WARDEN.value())){
            isCorrectMaterial = false;
            return false;
        }
        else{
            return ((ArmorItem)helmet).getMaterial().value() == material
                    && ((ArmorItem)chestplate).getMaterial().value() == material
                    && ((ArmorItem)leggings).getMaterial().value() == material
                    && ((ArmorItem)boots).getMaterial().value() == material;
        }
    }

    private void addStatusEffect(Player player) {
        if (!player.hasEffect(this.effect) ||
                !(player.getActiveEffectsMap().get(this.effect).getDuration() == MobEffectInstance.INFINITE_DURATION)) {
            player.forceAddEffect(new MobEffectInstance(this.effect, effectDuration, amplifier, false, false, false),
                    player.damageSources().generic().getDirectEntity());

        }
    }
}
