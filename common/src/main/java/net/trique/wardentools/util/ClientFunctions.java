package net.trique.wardentools.util;

import me.cybersteve.equiplib.item.armor.base.IEffectArmorItem;
import me.cybersteve.equiplib.util.ArmorSetHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ClientFunctions {

    public static void handleTooltipForArmor(ItemStack stack, List<Component> tooltips, MutableComponent successTooltip, ChatFormatting color, ArmorMaterial material) {
        LocalPlayer player = getLocalPlayer();
        if (player != null) {
            MutableComponent defaultArmorTooltip = Component.translatable("defaultArmorTooltip.description").withStyle(ChatFormatting.GRAY).
                    withStyle(ChatFormatting.ITALIC);
            if (stack.getItem() instanceof IEffectArmorItem armorItem &&
                    ArmorSetHelper.hasFullEffectSetArmorOn(player, armorItem.getEffectArmorSet())) {
                tooltips.add(successTooltip.withStyle(color));
            } else {
                tooltips.add(defaultArmorTooltip);
            }
        }
    }

    public static LocalPlayer getLocalPlayer() {
        return Minecraft.getInstance().player;
    }
}