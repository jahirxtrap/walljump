package com.jahirtrap.walljump.network;

import com.jahirtrap.walljump.network.message.MessageFallDistance;
import com.jahirtrap.walljump.network.message.MessageWallJump;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class PayloadHandler {
    public static void init() {
        PayloadTypeRegistry.playC2S().register(MessageFallDistance.TYPE, MessageFallDistance.CODEC);
        PayloadTypeRegistry.playC2S().register(MessageWallJump.TYPE, MessageWallJump.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(MessageFallDistance.TYPE, (payload, context) -> {
            MessageFallDistance.handle(payload, context::player);
        });

        ServerPlayNetworking.registerGlobalReceiver(MessageWallJump.TYPE, (payload, context) -> {
            MessageWallJump.handle(payload, context::player);
        });
    }
}
