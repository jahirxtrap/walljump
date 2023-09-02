package com.jahirtrap.walljump.proxy;

import com.jahirtrap.walljump.init.WallJumpModConfig;
import com.jahirtrap.walljump.network.PacketHandler;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommonProxy {
    public void setupCommon() {
        PacketHandler.init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event) {

        float distance = event.getDistance();
        if (distance > 3 && distance <= WallJumpModConfig.COMMON.minFallDistance.get()) {
            event.setDistance(3.0F);
            event.getEntity().playSound(SoundEvents.GENERIC_SMALL_FALL, 0.5F, 1.0F);
        }
    }
}
