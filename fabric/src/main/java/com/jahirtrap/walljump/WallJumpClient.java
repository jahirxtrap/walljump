package com.jahirtrap.walljump;

import com.jahirtrap.keybindfix.KeybindFixer;
import com.jahirtrap.walljump.network.PacketHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class WallJumpClient implements ClientModInitializer {
    public static KeyMapping KEY_WALL_JUMP = new KeyMapping(
            "key.walljump.walljump",
            GLFW.GLFW_KEY_LEFT_SHIFT,
            "key.categories.walljump"
    );

    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(KEY_WALL_JUMP);
        KeybindFixer.putKey(KEY_WALL_JUMP);
        PacketHandler.initClient();
    }
}
