package com.jahirtrap.walljump.network;

import com.jahirtrap.walljump.network.message.MessageFallDistance;
import com.jahirtrap.walljump.network.message.MessageServerConfig;
import com.jahirtrap.walljump.network.message.MessageWallJump;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public class PacketHandler {
    private static void register(RegisterPayloadHandlerEvent event) {
        IPayloadRegistrar registrar = event.registrar(MODID);

        registrar.play(MessageFallDistance.ID, MessageFallDistance::new, MessageFallDistance::handle);
        registrar.play(MessageWallJump.ID, MessageWallJump::new, MessageWallJump::handle);
        registrar.play(MessageServerConfig.ID, MessageServerConfig::new, MessageServerConfig::handle);
    }

    public static void init(IEventBus bus) {
        bus.addListener(PacketHandler::register);
    }

    public static void sendToServer(CustomPacketPayload message) {
        PacketDistributor.SERVER.noArg().send(message);
    }

    public static void sendToPlayer(ServerPlayer player, CustomPacketPayload message) {
        PacketDistributor.PLAYER.with(player).send(message);
    }
}
