package com.jahirtrap.walljump.network;

import com.jahirtrap.walljump.WallJumpMod;
import com.jahirtrap.walljump.network.message.MessageFallDistance;
import com.jahirtrap.walljump.network.message.MessageWallJump;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class PayloadHandler {
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(WallJumpMod.MODID);

        registrar.playToServer(MessageFallDistance.TYPE, MessageFallDistance.CODEC, MessageFallDistance::handle);
        registrar.playToServer(MessageWallJump.TYPE, MessageWallJump.CODEC, MessageWallJump::handle);
    }
}
