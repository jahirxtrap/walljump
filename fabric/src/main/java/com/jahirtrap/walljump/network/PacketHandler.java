package com.jahirtrap.walljump.network;

import com.jahirtrap.walljump.network.message.MessageFallDistance;
import com.jahirtrap.walljump.network.message.MessageServerConfig;
import com.jahirtrap.walljump.network.message.MessageWallJump;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class PacketHandler {
    public static void init() {
        PayloadTypeRegistry.playC2S().register(MessageFallDistance.TYPE, MessageFallDistance.CODEC);
        PayloadTypeRegistry.playC2S().register(MessageWallJump.TYPE, MessageWallJump.CODEC);
        PayloadTypeRegistry.playS2C().register(MessageServerConfig.TYPE, MessageServerConfig.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(MessageFallDistance.TYPE, MessageFallDistance::handle);
        ServerPlayNetworking.registerGlobalReceiver(MessageWallJump.TYPE, MessageWallJump::handle);
    }

    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(MessageServerConfig.TYPE, MessageServerConfig::handle);
    }
}
