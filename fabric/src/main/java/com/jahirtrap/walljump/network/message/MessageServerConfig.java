package com.jahirtrap.walljump.network.message;

import com.google.common.collect.Lists;
import com.jahirtrap.walljump.init.ModConfig.BlockListMode;
import com.jahirtrap.walljump.init.ServerConfig;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public class MessageServerConfig {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "message_server_config");

    public static void handle(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buffer, PacketSender sender) {
        ServerConfig.allowReClinging = buffer.readBoolean();
        ServerConfig.onFallDoubleJump = buffer.readBoolean();
        ServerConfig.onFallWallCling = buffer.readBoolean();
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
