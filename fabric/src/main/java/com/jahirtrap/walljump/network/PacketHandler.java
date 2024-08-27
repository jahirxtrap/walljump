package com.jahirtrap.walljump.network;

import com.jahirtrap.walljump.network.message.MessageFallDistance;
import com.jahirtrap.walljump.network.message.MessageServerConfig;
import com.jahirtrap.walljump.network.message.MessageWallJump;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class PacketHandler {
    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(MessageFallDistance.ID, MessageFallDistance::handle);
        ServerPlayNetworking.registerGlobalReceiver(MessageWallJump.ID, MessageWallJump::handle);
    }

    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(MessageServerConfig.ID, MessageServerConfig::handle);
    }
}
