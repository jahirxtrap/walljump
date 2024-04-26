package com.jahirtrap.walljump.proxy;

import com.jahirtrap.walljump.WallJumpMod;
import com.jahirtrap.walljump.init.WallJumpModConfig;
import com.jahirtrap.walljump.logic.DoubleJumpLogic;
import com.jahirtrap.walljump.logic.FallingSound;
import com.jahirtrap.walljump.logic.SpeedBoostLogic;
import com.jahirtrap.walljump.logic.WallJumpLogic;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.lwjgl.glfw.GLFW;

public class ClientProxy extends CommonProxy {
    private static final Minecraft minecraft = Minecraft.getInstance();

    public static KeyMapping KEY_WALL_JUMP = new KeyMapping(
            "key.walljump.walljump",
            GLFW.GLFW_KEY_LEFT_SHIFT,
            "key.categories.walljump"
    );

    private static FallingSound FALLING_SOUND;

    public static boolean collidesWithBlock(Level level, AABB box) {
        return !level.noCollision(box);
    }

    @Override
    public void setupClient() {
        RegisterKeyMappingsEvent event = new RegisterKeyMappingsEvent(minecraft.options);
        event.register(KEY_WALL_JUMP);
    }

    @EventBusSubscriber(modid = WallJumpMod.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
    private static class ClientHandler {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {

            LocalPlayer pl = minecraft.player;

            if (event.phase != TickEvent.Phase.END || pl == null) return;

            WallJumpLogic.doWallJump(pl);
            DoubleJumpLogic.doDoubleJump(pl);
            SpeedBoostLogic.doSpeedBoost(pl);

            if (pl.horizontalCollision && WallJumpModConfig.stepAssist && pl.getDeltaMovement().y > -0.2 && pl.getDeltaMovement().y < 0.01) {
                if (!ClientProxy.collidesWithBlock(pl.level(), pl.getBoundingBox().inflate(0.01, -pl.maxUpStep() + 0.02, 0.01))) {
                    pl.setOnGround(true);
                }
            }

            if (pl.isSprinting() && pl.getDeltaMovement().length() > 0.08)
                pl.horizontalCollision = false;

            if (pl.fallDistance > 1.5 && !pl.isFallFlying()) {

                if (WallJumpModConfig.playFallSound && (FALLING_SOUND == null || FALLING_SOUND.isStopped())) {
                    FALLING_SOUND = new FallingSound(pl);
                    minecraft.getSoundManager().play(FALLING_SOUND);
                }
            }
        }

        @SubscribeEvent
        public static void onJoinWorld(EntityJoinLevelEvent event) {
            if (event.getEntity() == minecraft.player && WallJumpModConfig.playFallSound) {
                FALLING_SOUND = new FallingSound(minecraft.player);
                minecraft.getSoundManager().play(FALLING_SOUND);
            }
        }
    }
}
