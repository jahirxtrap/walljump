package com.jahirtrap.walljump.proxy;

import com.jahirtrap.walljump.init.WallJumpModConfig;
import com.jahirtrap.walljump.logic.*;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.lwjgl.glfw.GLFW;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public class ClientProxy extends CommonProxy {
    private static final Minecraft minecraft = Minecraft.getInstance();

    public static KeyMapping KEY_WALL_JUMP = new KeyMapping(
            "key.walljump.walljump",
            GLFW.GLFW_KEY_LEFT_SHIFT,
            "key.categories.walljump"
    );

    private static FallingSound FALLING_SOUND;

    @Override
    public void setupClient() {
        RegisterKeyMappingsEvent event = new RegisterKeyMappingsEvent(minecraft.options);
        event.register(KEY_WALL_JUMP);
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
    private static class ClientHandler {
        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
            LocalPlayer pl = minecraft.player;

            if (pl == null) return;

            WallJumpLogic.doWallJump(pl);
            DoubleJumpLogic.doDoubleJump(pl);
            SpeedBoostLogic.doSpeedBoost(pl);
            StepAssistLogic.doStepAssist(pl);
        }

        @SubscribeEvent
        public static void onEntityJoin(EntityJoinLevelEvent event) {
            if (event.getEntity() == minecraft.player && WallJumpModConfig.playFallSound) {
                FALLING_SOUND = new FallingSound(minecraft.player);
                minecraft.getSoundManager().play(FALLING_SOUND);
            }
        }
    }
}
