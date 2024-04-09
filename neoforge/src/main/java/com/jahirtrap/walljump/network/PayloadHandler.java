package com.jahirtrap.walljump.network;

import com.jahirtrap.walljump.WallJumpMod;
import com.jahirtrap.walljump.network.message.MessageFallDistance;
import com.jahirtrap.walljump.network.message.MessageWallJump;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

public class PayloadHandler {
    public static void register(RegisterPayloadHandlerEvent event) {
        IPayloadRegistrar registrar = event.registrar(WallJumpMod.MODID);

        registrar.play(MessageFallDistance.ID, MessageFallDistance::new, MessageFallDistance::handle);
        registrar.play(MessageWallJump.ID, MessageWallJump::new, MessageWallJump::handle);
    }
}
