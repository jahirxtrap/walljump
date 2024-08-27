package com.jahirtrap.walljump.network.message;

import com.jahirtrap.walljump.init.ServerConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public record MessageWallJump(boolean didWallJump) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "message_wall_jump");
    public static final Type<MessageWallJump> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, MessageWallJump> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            MessageWallJump::didWallJump,
            MessageWallJump::new
    );

    public static void handle(MessageWallJump message, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player && message.didWallJump) {
                player.resetFallDistance();
                player.causeFoodExhaustion((float) ServerConfig.exhaustionWallJump);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
