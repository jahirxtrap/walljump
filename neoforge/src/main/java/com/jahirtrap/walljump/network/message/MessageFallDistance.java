package com.jahirtrap.walljump.network.message;

import com.jahirtrap.walljump.WallJumpMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class MessageFallDistance implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(WallJumpMod.MODID, "message_fall_distance");
    private final float fallDistance;

    public MessageFallDistance(float fallDistance) {
        this.fallDistance = fallDistance;
    }

    public MessageFallDistance(FriendlyByteBuf buffer) {
        fallDistance = buffer.readFloat();
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeFloat(fallDistance);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public void handle(PlayPayloadContext context) {
        context.player().ifPresent(player -> player.fallDistance = fallDistance);
    }
}
