package com.jahirtrap.walljump.network.message;

import com.jahirtrap.walljump.init.WallJumpModConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public record MessageWallJump(boolean didWallJump) implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MODID, "message_wall_jump");
    public static final Type<MessageWallJump> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, MessageWallJump> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            MessageWallJump::didWallJump,
            MessageWallJump::new
    );

    public static void handle(MessageWallJump message, ServerPayloadContext context) {
        context.execute(() -> {
            if (context.player() != null && message.didWallJump) {
                context.player().resetFallDistance();
                context.player().causeFoodExhaustion((float) WallJumpModConfig.exhaustionWallJump);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
