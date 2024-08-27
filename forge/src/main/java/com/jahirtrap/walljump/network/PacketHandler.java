package com.jahirtrap.walljump.network;

import com.jahirtrap.walljump.network.message.MessageFallDistance;
import com.jahirtrap.walljump.network.message.MessageServerConfig;
import com.jahirtrap.walljump.network.message.MessageWallJump;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public final class PacketHandler {
    public static final int PROTOCOL_VERSION = 1;
    private static int nextId = 0;
    public static final SimpleChannel INSTANCE = ChannelBuilder
            .named(new ResourceLocation(MODID, "network"))
            .networkProtocolVersion(PROTOCOL_VERSION)
            .clientAcceptedVersions(Channel.VersionTest.exact(PROTOCOL_VERSION))
            .serverAcceptedVersions(Channel.VersionTest.exact(PROTOCOL_VERSION))
            .simpleChannel();

    public static void init() {
        INSTANCE.messageBuilder(MessageFallDistance.class, nextId++).codec(MessageFallDistance.CODEC).consumerNetworkThread(MessageFallDistance::handle).add();
        INSTANCE.messageBuilder(MessageWallJump.class, nextId++).codec(MessageWallJump.CODEC).consumerNetworkThread(MessageWallJump::handle).add();
        INSTANCE.messageBuilder(MessageServerConfig.class, nextId++).codec(MessageServerConfig.CODEC).consumerNetworkThread(MessageServerConfig::handle).add();
    }

    public static <T> void sendToServer(T message) {
        INSTANCE.send(message, PacketDistributor.SERVER.noArg());
    }

    public static <T> void sendToPlayer(ServerPlayer player, T message) {
        INSTANCE.send(message, PacketDistributor.PLAYER.with(player));
    }
}
