package com.jahirtrap.walljump;

import com.jahirtrap.walljump.init.ModConfig;
import com.jahirtrap.walljump.network.PacketHandler;
import com.jahirtrap.walljump.sound.FallingSoundInstance;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyMapping.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

@Environment(EnvType.CLIENT)
public class WallJumpClient implements ClientModInitializer {
    public static FallingSoundInstance FALLING_SOUND;

    public static Category KEY_CATEGORY_WALL_JUMP = Category.register(Identifier.fromNamespaceAndPath(MODID, "wall_jump"));

    public static KeyMapping KEY_WALL_JUMP = new KeyMapping(
            "key.walljump.walljump",
            GLFW.GLFW_KEY_LEFT_SHIFT,
            KEY_CATEGORY_WALL_JUMP
    );

    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(KEY_WALL_JUMP);
        PacketHandler.initClient();
    }

    public static void playFallingSound(LocalPlayer player) {
        if (ModConfig.playFallingSound && player.fallDistance > 1.5 && !player.isFallFlying() && (FALLING_SOUND == null || FALLING_SOUND.isStopped())) {
            FALLING_SOUND = new FallingSoundInstance(player);
            Minecraft.getInstance().getSoundManager().play(FALLING_SOUND);
        }
    }
}
