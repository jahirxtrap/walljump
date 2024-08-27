package com.jahirtrap.walljump.network.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record MessageFallDistance(float fallDistance) {
    public static void encode(MessageFallDistance message, FriendlyByteBuf buffer) {
        buffer.writeFloat(message.fallDistance);
    }

    public static MessageFallDistance decode(FriendlyByteBuf buffer) {
        return new MessageFallDistance(buffer.readFloat());
    }

    public static void handle(MessageFallDistance message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            ServerPlayer player = supplier.get().getSender();
            if (player != null) player.fallDistance = message.fallDistance;
        });
        supplier.get().setPacketHandled(true);
    }
}
