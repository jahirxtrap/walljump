package com.jahirtrap.walljump;

import com.jahirtrap.keybindfix.KeybindFixer;
import com.jahirtrap.walljump.init.ModConfig;
import com.jahirtrap.walljump.network.PacketHandler;
import com.jahirtrap.walljump.sound.FallingSoundInstance;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class WallJumpClient implements ClientModInitializer {
    public static FallingSoundInstance FALLING_SOUND;

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

    public static void playFallingSound(LocalPlayer player) {
        if (ModConfig.playFallingSound && player.getDeltaMovement().y < -0.5 && !player.isFallFlying() && (FALLING_SOUND == null || FALLING_SOUND.isStopped())) {
            FALLING_SOUND = new FallingSoundInstance(player);
            Minecraft.getInstance().getSoundManager().play(FALLING_SOUND);
        }
    }
}
