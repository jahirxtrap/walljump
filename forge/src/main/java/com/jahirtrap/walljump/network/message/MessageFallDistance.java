package com.jahirtrap.walljump.network.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;

public record MessageFallDistance(float fallDistance) {
    public static void encode(MessageFallDistance message, FriendlyByteBuf buffer) {
        buffer.writeFloat(message.fallDistance);
    }

    public static MessageFallDistance decode(FriendlyByteBuf buffer) {
        return new MessageFallDistance(buffer.readFloat());
    }

    public static void handle(MessageFallDistance message, CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) player.fallDistance = message.fallDistance;
        });
        context.setPacketHandled(true);
    }
}
