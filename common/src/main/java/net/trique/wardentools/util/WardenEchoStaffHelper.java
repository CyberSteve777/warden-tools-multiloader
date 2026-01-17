package net.trique.wardentools.util;

import net.minecraft.world.entity.player.Player;
import net.trique.wardentools.attachments.CommonDataAttachments;
import net.trique.wardentools.platform.Services;

public class WardenEchoStaffHelper {
    public static boolean playerPressedButton(Player player, KeyAction action) {
        return Services.PLATFORM.getOrCreateAttachedValue(player, CommonDataAttachments.ACTIVE_KEYBINDS).contains(action);
    }
}
