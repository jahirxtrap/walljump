package com.jahirtrap.walljump.network.message;

import com.jahirtrap.walljump.WallJumpMod;
import com.jahirtrap.walljump.init.ModConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class MessageWallJump implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(WallJumpMod.MODID, "message_wall_jump");

    public MessageWallJump() {
    }

    public MessageWallJump(FriendlyByteBuf buffer) {
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public void handle(PlayPayloadContext context) {
        var player = context.player().orElse(null);
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.resetFallDistance();
            serverPlayer.causeFoodExhaustion((float) ModConfig.exhaustionWallJump);
        }
    }
}
