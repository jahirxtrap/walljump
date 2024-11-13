package com.jahirtrap.walljump.logic;

import com.jahirtrap.walljump.init.ServerConfig;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StepAssistLogic {
    private static boolean collidesWithBlock(Level level, AABB box) {
        return !level.noCollision(box);
    }

    public static void doStepAssist(LocalPlayer pl) {
        if (pl.horizontalCollision && ServerConfig.stepAssist && pl.getDeltaMovement().y > -0.2 && pl.getDeltaMovement().y < 0.01) {
            if (!collidesWithBlock(pl.getLevel(), pl.getBoundingBox().inflate(0.01, -pl.maxUpStep() + 0.02, 0.01))) {
                pl.setOnGround(true);
            }
        }

        if (pl.isSprinting() && pl.getDeltaMovement().length() > 0.08) pl.horizontalCollision = false;
    }
}
