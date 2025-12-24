package com.jahirtrap.walljump;

import com.jahirtrap.walljump.init.ModConfig;
import com.jahirtrap.walljump.sound.FallingSoundInstance;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyMapping.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

@Mod.EventBusSubscriber(modid = WallJumpMod.MODID, value = Dist.CLIENT)
public class WallJumpClient {
    public static FallingSoundInstance FALLING_SOUND;

    public static Category KEY_CATEGORY_WALL_JUMP = Category.register(Identifier.fromNamespaceAndPath(MODID, "wall_jump"));

    public static KeyMapping KEY_WALL_JUMP = new KeyMapping(
            "key.walljump.walljump",
            GLFW.GLFW_KEY_LEFT_SHIFT,
            KEY_CATEGORY_WALL_JUMP
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
