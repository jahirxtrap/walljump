package com.jahirtrap.walljump.network.message;

import com.jahirtrap.walljump.init.ModConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class MessageWallJump implements IMessage<MessageWallJump> {
    public void encode(MessageWallJump message, FriendlyByteBuf buffer) {
    }

    public MessageWallJump decode(FriendlyByteBuf buffer) {
        return new MessageWallJump();
    }

    public void handle(MessageWallJump message, CustomPayloadEvent.Context context) {
        context.enqueueWork(() ->
        {
            ServerPlayer player = context.getSender();
            if (player != null) {
                player.resetFallDistance();
                player.causeFoodExhaustion((float) ModConfig.exhaustionWallJump);
            }
        });
        context.setPacketHandled(true);
    }
}
