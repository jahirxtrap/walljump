package com.jahirtrap.walljump;

import com.jahirtrap.walljump.logic.FallingSound;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class WallJumpClient implements ClientModInitializer {
    public static KeyMapping KEY_WALL_JUMP = new KeyMapping(
            "key.walljump.walljump",
            GLFW.GLFW_KEY_LEFT_SHIFT,
            "key.categories.walljump"
    );

    public static FallingSound FALLING_SOUND;

    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(KEY_WALL_JUMP);
        FALLING_SOUND = new FallingSound(Minecraft.getInstance().player);
    }
}
