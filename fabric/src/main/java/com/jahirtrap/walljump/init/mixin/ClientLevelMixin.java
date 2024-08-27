package com.jahirtrap.walljump.init.mixin;

import com.jahirtrap.walljump.init.ServerConfig;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin {

    @Inject(method = "disconnect", at = @At("HEAD"))
    public void disconnect(CallbackInfo ci) {
        ServerConfig.reset();
    }
}
