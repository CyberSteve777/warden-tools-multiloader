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
import net.trique.wardentools.client.WTKeybinds;
import net.trique.wardentools.networking.packet.C2SKeybindPacket;

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
    public static void handleClientTick() {
        LocalPlayer player = getLocalPlayer();
        if(player != null && player.isAlive()) {//this can run outside the world or when player is dead
            C2SKeybindPacket.sendToServer(KeyAction.CONSUME_CHARGES, WTKeybinds.CONSUME_CHARGES.isDown());
        }
    }
}