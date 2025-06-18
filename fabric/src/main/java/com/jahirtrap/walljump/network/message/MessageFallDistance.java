package com.jahirtrap.walljump.network.message;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public record MessageFallDistance(float fallDistance) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "message_fall_distance");
    public static final Type<MessageFallDistance> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, MessageFallDistance> CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            MessageFallDistance::fallDistance,
            MessageFallDistance::new
    );

    public static void handle(MessageFallDistance message, ServerPlayNetworking.Context context) {
        context.player().getServer().execute(() -> context.player().fallDistance = message.fallDistance);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
