package com.jahirtrap.walljump.network.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class MessageFallDistance implements IMessage<MessageFallDistance> {
    private float fallDistance;

    public MessageFallDistance() {
    }

    public MessageFallDistance(float fallDistance) {
        this.fallDistance = fallDistance;
    }

    public MessageFallDistance decode(FriendlyByteBuf buffer) {
        return new MessageFallDistance(buffer.readFloat());
    }

    public void encode(MessageFallDistance message, FriendlyByteBuf buffer) {
        buffer.writeFloat(message.fallDistance);
    }

    public void handle(MessageFallDistance message, CustomPayloadEvent.Context context) {
        context.enqueueWork(() ->
                context.getSender().fallDistance = message.fallDistance);
        context.setPacketHandled(true);
    }
}
