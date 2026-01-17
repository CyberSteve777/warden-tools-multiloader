package net.trique.wardentools.client;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class WTKeybinds {
    public static final KeyMapping CONSUME_CHARGES = new KeyMapping("key.wardentools.consume_charges",
            GLFW.GLFW_KEY_LEFT_CONTROL, "key.categories.wardentools");
}
