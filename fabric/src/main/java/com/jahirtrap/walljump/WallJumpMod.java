package com.jahirtrap.walljump;

import com.jahirtrap.configlib.TXFConfig;
import com.jahirtrap.walljump.init.WallJumpEnchantments;
import com.jahirtrap.walljump.init.WallJumpModConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;

public class WallJumpMod implements ModInitializer {

    public static final String MODID = "walljump";
    public static final ResourceLocation FALL_DISTANCE_PACKET_ID = new ResourceLocation(MODID, "fall_distance");
    public static final ResourceLocation WALL_JUMP_PACKET_ID = new ResourceLocation(MODID, "wall_jump");

    @Override
    public void onInitialize() {
        TXFConfig.init(MODID, WallJumpModConfig.class);
        WallJumpEnchantments.init();
        initEvents();
    }

    public void initEvents() {
        ServerPlayNetworking.registerGlobalReceiver(FALL_DISTANCE_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            var fallDistance = buf.readFloat();
            server.execute(() -> {
                player.fallDistance = fallDistance;
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(WALL_JUMP_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            var didWallJump = buf.readBoolean();
            server.execute(() -> {
                if (didWallJump) {
                    player.resetFallDistance();
                    player.causeFoodExhaustion((float) WallJumpModConfig.exhaustionWallJump);
                }
            });
        });
    }
}
