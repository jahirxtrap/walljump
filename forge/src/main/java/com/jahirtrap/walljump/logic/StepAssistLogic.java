package com.jahirtrap.walljump.logic;

import com.jahirtrap.walljump.init.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StepAssistLogic {

    private static FallingSound FALLING_SOUND;

    private static boolean collidesWithBlock(Level level, AABB box) {
        return !level.noCollision(box);
    }

    public static void doStepAssist(LocalPlayer pl) {
        if (pl.horizontalCollision && ModConfig.stepAssist && pl.getDeltaMovement().y > -0.2 && pl.getDeltaMovement().y < 0.01) {
            if (!collidesWithBlock(pl.level(), pl.getBoundingBox().inflate(0.01, -pl.maxUpStep() + 0.02, 0.01))) {
                pl.setOnGround(true);
            }
        }

        if (pl.isSprinting() && pl.getDeltaMovement().length() > 0.08)
            pl.horizontalCollision = false;

        if (pl.fallDistance > 1.5 && !pl.isFallFlying()) {
            if (ModConfig.playFallSound && (FALLING_SOUND == null || FALLING_SOUND.isStopped())) {
                FALLING_SOUND = new FallingSound(pl);
                Minecraft.getInstance().getSoundManager().play(FALLING_SOUND);
            }
        }
    }
}
