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
public abstract class LocalPlayerMixin {

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void aiStep(CallbackInfo ci) {
        LocalPlayer player = Minecraft.getInstance().player;

        WallJumpLogic.doWallJump(player);
        DoubleJumpLogic.doDoubleJump(player);
        SpeedBoostLogic.doSpeedBoost(player);
        StepAssistLogic.doStepAssist(player);
    }
}
