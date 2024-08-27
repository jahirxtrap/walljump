package com.jahirtrap.walljump.network.message;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public class MessageFallDistance {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "message_fall_distance");

    public static void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buffer, PacketSender sender) {
        var fallDistance = buffer.readFloat();
        server.execute(() -> player.fallDistance = fallDistance);
    }
}
