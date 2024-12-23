package com.jahirtrap.walljump.network;

import com.jahirtrap.walljump.network.message.MessageFallDistance;
import com.jahirtrap.walljump.network.message.MessageServerConfig;
import com.jahirtrap.walljump.network.message.MessageWallJump;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public final class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    private static int nextId = 0;
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(MODID, "network"))
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .simpleChannel();

    public static void init() {
        INSTANCE.registerMessage(nextId++, MessageFallDistance.class, MessageFallDistance::encode, MessageFallDistance::decode, MessageFallDistance::handle);
        INSTANCE.registerMessage(nextId++, MessageWallJump.class, MessageWallJump::encode, MessageWallJump::decode, MessageWallJump::handle);
        INSTANCE.registerMessage(nextId++, MessageServerConfig.class, MessageServerConfig::encode, MessageServerConfig::decode, MessageServerConfig::handle);
    }

    public static <T> void sendToServer(T message) {
        INSTANCE.sendToServer(message);
    }

    public static <T> void sendToPlayer(ServerPlayer player, T message) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
