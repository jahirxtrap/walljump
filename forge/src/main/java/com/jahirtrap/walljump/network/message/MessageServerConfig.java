package com.jahirtrap.walljump.network.message;

import com.google.common.collect.Lists;
import com.jahirtrap.walljump.init.ModConfig.BlockListMode;
import com.jahirtrap.walljump.init.ServerConfig;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public record MessageServerConfig(byte[] config) {
    public static void encode(MessageServerConfig message, FriendlyByteBuf buffer) {
        buffer.writeByteArray(message.config);
    }

    public static MessageServerConfig decode(FriendlyByteBuf buffer) {
        return new MessageServerConfig(buffer.readByteArray());
    }

    public static void handle(MessageServerConfig message, Supplier<NetworkEvent.Context> supplier) {
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
        ServerConfig.blockList = readList(buffer);
        ServerConfig.blockListMode = buffer.readEnum(BlockListMode.class);
        ServerConfig.enableEnchantments = buffer.readBoolean();
        ServerConfig.enableWallJump = buffer.readBoolean();
        ServerConfig.enableDoubleJump = buffer.readBoolean();
        ServerConfig.enableSpeedBoost = buffer.readBoolean();
        ServerConfig.speedBoostMultiplier = buffer.readDouble();
    }

    private static List<String> readList(FriendlyByteBuf buffer) {
        int size = buffer.readInt();
        List<String> list = Lists.newArrayListWithCapacity(size);
        for (int i = 0; i < size; i++) list.add(buffer.readUtf());
        return list;
    }
}
