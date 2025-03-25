package com.jahirtrap.walljump;

import com.jahirtrap.walljump.init.ModConfig;
import com.jahirtrap.walljump.sound.FallingSoundInstance;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = WallJumpMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class WallJumpClient {
    public static FallingSoundInstance FALLING_SOUND;

    public static KeyMapping KEY_WALL_JUMP = new KeyMapping(
            "key.walljump.walljump",
            GLFW.GLFW_KEY_LEFT_SHIFT,
            "key.categories.walljump"
    );

    @SubscribeEvent
    public static void registerKeyBinding(RegisterKeyMappingsEvent event) {
        event.register(KEY_WALL_JUMP);
    }

    public static void playFallingSound(LocalPlayer player) {
        if (ModConfig.playFallingSound && player.fallDistance > 1.5 && !player.isFallFlying() && (FALLING_SOUND == null || FALLING_SOUND.isStopped())) {
            FALLING_SOUND = new FallingSoundInstance(player);
            Minecraft.getInstance().getSoundManager().play(FALLING_SOUND);
        }
    }
}
