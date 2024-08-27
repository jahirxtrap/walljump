package com.jahirtrap.walljump.network.message;

import com.jahirtrap.walljump.init.ServerConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public record MessageWallJump(boolean didWallJump) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "message_wall_jump");

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeBoolean(didWallJump);
    }

    public MessageWallJump(FriendlyByteBuf buffer) {
        this(buffer.readBoolean());
    }

    public static void handle(MessageWallJump message, PlayPayloadContext context) {
        context.workHandler().execute(() -> {
            if (context.player().orElse(null) instanceof ServerPlayer player && message.didWallJump) {
                player.resetFallDistance();
                player.causeFoodExhaustion((float) ServerConfig.exhaustionWallJump);
            }
        });
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
