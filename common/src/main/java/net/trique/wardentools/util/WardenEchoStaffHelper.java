package net.trique.wardentools.util;

import net.minecraft.world.entity.player.Player;
import net.trique.wardentools.attachments.CommonDataAttachments;
import net.trique.wardentools.platform.Services;

import java.util.HashMap;

public class WardenEchoStaffHelper {
    private static final HashMap<Integer, Boolean> PRESSED_BUTTON = new HashMap<>();


    public static void addPlayer(int id) {
        PRESSED_BUTTON.put(id, true);
    }

    public static void clearAllPresses() {
        PRESSED_BUTTON.replaceAll((i, v) -> false);
    }

    public static boolean playerPressedButton(Player player, KeyAction action) {
        return Services.PLATFORM.getOrCreateAttachedValue(player, CommonDataAttachments.ACTIVE_KEYBINDS).contains(action);
    }
}
