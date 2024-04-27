package com.jahirtrap.walljump.proxy;

import com.jahirtrap.walljump.WallJumpMod;
import com.jahirtrap.walljump.init.WallJumpModConfig;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;

public class CommonProxy {
    public void setupCommon() {
    }

    public void setupClient() {
    }

    @EventBusSubscriber(modid = WallJumpMod.MODID, bus = EventBusSubscriber.Bus.GAME)
    private static class CommonHandler {
        @SubscribeEvent
        public static void onLivingFall(LivingFallEvent event) {
            float distance = event.getDistance();
            if (distance > 3 && distance <= WallJumpModConfig.minFallDistance) {
                event.setDistance(3.0F);
                event.getEntity().playSound(SoundEvents.GENERIC_SMALL_FALL, 0.5F, 1.0F);
            }
        }
    }
}
