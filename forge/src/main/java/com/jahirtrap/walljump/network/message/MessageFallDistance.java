package com.jahirtrap.walljump.network.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public record MessageFallDistance(float fallDistance) implements CustomPacketPayload {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(MODID, "message_fall_distance");
    public static final Type<MessageFallDistance> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, MessageFallDistance> CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            MessageFallDistance::fallDistance,
            MessageFallDistance::new
    );

    public static void handle(MessageFallDistance message, CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            if (context.getSender() instanceof ServerPlayer player) player.fallDistance = message.fallDistance;
        });
        context.setPacketHandled(true);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
