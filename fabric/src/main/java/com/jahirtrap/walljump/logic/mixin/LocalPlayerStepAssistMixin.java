package com.jahirtrap.walljump.logic.mixin;

import com.jahirtrap.walljump.WallJumpClient;
import com.jahirtrap.walljump.init.WallJumpModConfig;
import com.jahirtrap.walljump.logic.FallingSound;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerStepAssistMixin extends AbstractClientPlayer {

    public LocalPlayerStepAssistMixin(ClientLevel level, GameProfile profile, ProfilePublicKey profilePublicKey) {
        super(level, profile, profilePublicKey);
    }

    private boolean doesNotCollide(AABB box) {
        return this.level.noCollision(this, box) && !this.level.containsAnyLiquid(box);
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void miscellaneousTickMovement(CallbackInfo ci) {

        if (this.horizontalCollision && WallJumpModConfig.stepAssist && this.getDeltaMovement().y > -0.2 && this.getDeltaMovement().y < 0.01) {
            if (this.doesNotCollide(this.getBoundingBox().inflate(0.01, -this.maxUpStep + 0.02, 0.01))) {
                this.onGround = true;
            }
        }

        if (this.fallDistance > 1.5 && !this.isFallFlying()) {
            if (WallJumpModConfig.playFallSound && (WallJumpClient.FALLING_SOUND == null || WallJumpClient.FALLING_SOUND.isStopped())) {
                WallJumpClient.FALLING_SOUND = new FallingSound((LocalPlayer) (Object) this);
                Minecraft.getInstance().getSoundManager().play(WallJumpClient.FALLING_SOUND);
            }
        }
    }
}
