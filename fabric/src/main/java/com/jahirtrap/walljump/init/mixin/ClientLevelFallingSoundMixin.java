package com.jahirtrap.walljump.init.mixin;

import com.jahirtrap.walljump.WallJumpClient;
import com.jahirtrap.walljump.logic.FallingSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public abstract class ClientLevelFallingSoundMixin {

    @Inject(method = "addPlayer", at = @At(value = "TAIL"))
    private void addPlayerFallingSound(int id, AbstractClientPlayer player, CallbackInfo ci) {
        if (player == Minecraft.getInstance().player) {
            WallJumpClient.FALLING_SOUND = new FallingSound(Minecraft.getInstance().player);
            Minecraft.getInstance().getSoundManager().play(WallJumpClient.FALLING_SOUND);
        }
    }
}
