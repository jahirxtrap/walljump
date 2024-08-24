package com.jahirtrap.walljump.proxy;

import com.jahirtrap.walljump.init.ModConfig;
import com.jahirtrap.walljump.logic.*;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

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
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        LocalPlayer pl = minecraft.player;

        if (event.phase != TickEvent.Phase.END || pl == null) return;

        WallJumpLogic.doWallJump(pl);
        DoubleJumpLogic.doDoubleJump(pl);
        SpeedBoostLogic.doSpeedBoost(pl);
        StepAssistLogic.doStepAssist(pl);
    }

    @SubscribeEvent
    public void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() == minecraft.player && ModConfig.playFallSound) {
            FALLING_SOUND = new FallingSound(minecraft.player);
            minecraft.getSoundManager().play(FALLING_SOUND);
        }
    }
}
