package com.jahirtrap.walljump.network.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public record MessageFallDistance(float fallDistance) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "message_fall_distance");

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeFloat(fallDistance);
    }

    public MessageFallDistance(FriendlyByteBuf buffer) {
        this(buffer.readFloat());
    }

    public static void handle(MessageFallDistance message, PlayPayloadContext context) {
        context.workHandler().execute(() -> {
            if (context.player().orElse(null) instanceof ServerPlayer player)
                player.fallDistance = message.fallDistance;
        });
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
