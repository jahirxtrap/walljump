package com.jahirtrap.walljump.proxy;

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
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
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

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KEY_WALL_JUMP);
    }

    public static boolean collidesWithBlock(Level level, AABB box) {
        return !level.noCollision(box);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {

        LocalPlayer pl = minecraft.player;

        if (event.phase != TickEvent.Phase.END || pl == null) return;

        WallJumpLogic.doWallJump(pl);
        DoubleJumpLogic.doDoubleJump(pl);
        SpeedBoostLogic.doSpeedBoost(pl);

        if (pl.horizontalCollision && WallJumpModConfig.COMMON.stepAssist.get() && pl.getDeltaMovement().y > -0.2 && pl.getDeltaMovement().y < 0.01) {
            if (!ClientProxy.collidesWithBlock(pl.level, pl.getBoundingBox().inflate(0.01, -pl.maxUpStep + 0.02, 0.01))) {
                pl.setOnGround(true);
            }
        }

        if (pl.sprintTime > 0 && pl.getDeltaMovement().length() > 0.08)
            pl.horizontalCollision = false;

        if (pl.fallDistance > 1.5 && !pl.isFallFlying()) {

            if (WallJumpModConfig.COMMON.playFallSound.get() && (FALLING_SOUND == null || FALLING_SOUND.isStopped())) {
                FALLING_SOUND = new FallingSound(pl);
                minecraft.getSoundManager().play(FALLING_SOUND);
            }
        }
    }

    @SubscribeEvent
    public void onJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() == minecraft.player && WallJumpModConfig.COMMON.playFallSound.get()) {
            FALLING_SOUND = new FallingSound(minecraft.player);
            minecraft.getSoundManager().play(FALLING_SOUND);
        }
    }
}
