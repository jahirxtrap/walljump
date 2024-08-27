package com.jahirtrap.walljump.network.message;

import com.jahirtrap.walljump.init.ServerConfig;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

public record MessageServerConfig(byte[] config) {
    public static void encode(MessageServerConfig message, FriendlyByteBuf buffer) {
        buffer.writeByteArray(message.config);
    }

    public static MessageServerConfig decode(FriendlyByteBuf buffer) {
        return new MessageServerConfig(buffer.readByteArray());
    }

    public static void handle(MessageServerConfig message, CustomPayloadEvent.Context context) {
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.wrappedBuffer(message.config));

        ServerConfig.allowReClinging = buffer.readBoolean();
        ServerConfig.onFallDoubleJump = buffer.readBoolean();
        ServerConfig.exhaustionWallJump = buffer.readDouble();
        ServerConfig.minFallDistance = buffer.readDouble();
        ServerConfig.elytraSpeedBoost = buffer.readDouble();
        ServerConfig.sprintSpeedBoost = buffer.readDouble();
        ServerConfig.stepAssist = buffer.readBoolean();
        ServerConfig.useDoubleJump = buffer.readBoolean();
        ServerConfig.useWallJump = buffer.readBoolean();
        ServerConfig.wallJumpHeight = buffer.readDouble();
        ServerConfig.wallSlideDelay = buffer.readInt();
        ServerConfig.stopWallSlideDelay = buffer.readInt();
        ServerConfig.maxWallJumps = buffer.readInt();
        ServerConfig.enableEnchantments = buffer.readBoolean();
        ServerConfig.enableWallJump = buffer.readBoolean();
        ServerConfig.enableDoubleJump = buffer.readBoolean();
        ServerConfig.enableSpeedBoost = buffer.readBoolean();
        ServerConfig.speedBoostMultiplier = buffer.readDouble();
    }
}
