package com.jahirtrap.walljump.init.mixin;

import com.jahirtrap.walljump.init.ModConfig;
import com.jahirtrap.walljump.network.message.MessageServerConfig;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin {

    @Inject(method = "placeNewPlayer", at = @At("TAIL"))
    public void placeNewPlayer(Connection connection, ServerPlayer player, CallbackInfo ci) {
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());

        buffer.writeBoolean(ModConfig.allowReClinging);
        buffer.writeBoolean(ModConfig.onFallDoubleJump);
        buffer.writeDouble(ModConfig.exhaustionWallJump);
        buffer.writeDouble(ModConfig.minFallDistance);
        buffer.writeDouble(ModConfig.elytraSpeedBoost);
        buffer.writeDouble(ModConfig.sprintSpeedBoost);
        buffer.writeBoolean(ModConfig.stepAssist);
        buffer.writeBoolean(ModConfig.useDoubleJump);
        buffer.writeBoolean(ModConfig.useWallJump);
        buffer.writeDouble(ModConfig.wallJumpHeight);
        buffer.writeInt(ModConfig.wallSlideDelay);
        buffer.writeInt(ModConfig.stopWallSlideDelay);
        buffer.writeInt(ModConfig.maxWallJumps);
        buffer.writeBoolean(ModConfig.enableEnchantments);
        buffer.writeBoolean(ModConfig.enableWallJump);
        buffer.writeBoolean(ModConfig.enableDoubleJump);
        buffer.writeBoolean(ModConfig.enableSpeedBoost);
        buffer.writeDouble(ModConfig.speedBoostMultiplier);

        ServerPlayNetworking.send(player, MessageServerConfig.ID, buffer);
    }
}
