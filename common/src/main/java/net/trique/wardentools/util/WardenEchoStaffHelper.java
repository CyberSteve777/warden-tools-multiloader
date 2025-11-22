package net.trique.wardentools.util;

import java.util.HashMap;

public class WardenEchoStaffHelper {
    private static final HashMap<Integer, Boolean> PRESSED_BUTTON = new HashMap<>();


    public static void addPlayer(int id) {
        PRESSED_BUTTON.put(id, true);
    }

    public static void clearAllPresses() {
        PRESSED_BUTTON.replaceAll((i, v) -> false);
    }

    public static boolean playerPressedButton(int id) {
        return PRESSED_BUTTON.getOrDefault(id, false);
    }
}
