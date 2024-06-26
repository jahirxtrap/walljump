package com.jahirtrap.walljump.init.mixin;

import com.jahirtrap.walljump.logic.DoubleJumpLogic;
import com.jahirtrap.walljump.logic.SpeedBoostLogic;
import com.jahirtrap.walljump.logic.StepAssistLogic;
import com.jahirtrap.walljump.logic.WallJumpLogic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    private final Minecraft minecraft = Minecraft.getInstance();

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void tickMovement(CallbackInfo ci) {
        LocalPlayer pl = minecraft.player;

        WallJumpLogic.doWallJump(pl);
        DoubleJumpLogic.doDoubleJump(pl);
        SpeedBoostLogic.doSpeedBoost(pl);
        StepAssistLogic.doStepAssist(pl);
    }
}
