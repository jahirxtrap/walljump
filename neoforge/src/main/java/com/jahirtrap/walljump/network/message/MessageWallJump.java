package com.jahirtrap.walljump.network.message;

import com.jahirtrap.walljump.init.ModConfig;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public record MessageWallJump(boolean didWallJump) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "message_wall_jump");
    public static final Type<MessageWallJump> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, MessageWallJump> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            MessageWallJump::didWallJump,
            MessageWallJump::new
    );

    public void handle(IPayloadContext context) {
        if (context.player() instanceof ServerPlayer player && didWallJump) {
            player.resetFallDistance();
            player.causeFoodExhaustion((float) ModConfig.exhaustionWallJump);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
