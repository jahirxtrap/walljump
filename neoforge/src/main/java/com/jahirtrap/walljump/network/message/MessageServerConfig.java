package com.jahirtrap.walljump.network.message;

import com.jahirtrap.walljump.init.ServerConfig;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public record MessageServerConfig(byte[] config) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "message_server_config");
    public static final Type<MessageServerConfig> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, MessageServerConfig> CODEC = StreamCodec.composite(
            ByteBufCodecs.BYTE_ARRAY,
            MessageServerConfig::config,
            MessageServerConfig::new
    );

    public static void handle(MessageServerConfig message, IPayloadContext context) {
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

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
