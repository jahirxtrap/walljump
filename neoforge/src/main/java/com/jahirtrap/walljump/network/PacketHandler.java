package com.jahirtrap.walljump.network;

import com.jahirtrap.walljump.network.message.MessageFallDistance;
import com.jahirtrap.walljump.network.message.MessageServerConfig;
import com.jahirtrap.walljump.network.message.MessageWallJump;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public class PacketHandler {
    private static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID);

        registrar.playToServer(MessageFallDistance.TYPE, MessageFallDistance.CODEC, MessageFallDistance::handle);
        registrar.playToServer(MessageWallJump.TYPE, MessageWallJump.CODEC, MessageWallJump::handle);
        registrar.playToClient(MessageServerConfig.TYPE, MessageServerConfig.CODEC, MessageServerConfig::handle);
    }

    public static void init(IEventBus bus) {
        bus.addListener(PacketHandler::register);
    }
}
