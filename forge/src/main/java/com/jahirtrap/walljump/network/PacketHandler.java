package com.jahirtrap.walljump.network;

import com.jahirtrap.walljump.network.message.IMessage;
import com.jahirtrap.walljump.network.message.MessageFallDistance;
import com.jahirtrap.walljump.network.message.MessageWallJump;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public final class PacketHandler {
    public static final int PROTOCOL_VERSION = 1;
    public static SimpleChannel INSTANCE;
    private static int nextId = 0;

    public static void init() {
        INSTANCE = ChannelBuilder
                .named(ResourceLocation.fromNamespaceAndPath(MODID, "network"))
                .networkProtocolVersion(PROTOCOL_VERSION)
                .clientAcceptedVersions(Channel.VersionTest.exact(PROTOCOL_VERSION))
                .serverAcceptedVersions(Channel.VersionTest.exact(PROTOCOL_VERSION))
                .simpleChannel();
        register(MessageFallDistance.class, new MessageFallDistance());
        register(MessageWallJump.class, new MessageWallJump());
    }

    private static <T> void register(Class<T> clazz, IMessage<T> message) {
        INSTANCE.messageBuilder(clazz, nextId++).decoder(message::decode).encoder(message::encode).consumerNetworkThread(message::handle).add();
    }
}
