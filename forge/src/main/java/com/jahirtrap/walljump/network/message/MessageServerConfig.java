package com.jahirtrap.walljump.network.message;

import com.google.common.collect.Lists;
import com.jahirtrap.walljump.init.ModConfig.BlockListMode;
import com.jahirtrap.walljump.init.ServerConfig;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.network.CustomPayloadEvent;

import java.util.List;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public record MessageServerConfig(byte[] config) implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MODID, "message_server_config");
    public static final Type<MessageServerConfig> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, MessageServerConfig> CODEC = StreamCodec.composite(
            ByteBufCodecs.BYTE_ARRAY,
            MessageServerConfig::config,
            MessageServerConfig::new
    );

    public static void handle(MessageServerConfig message, CustomPayloadEvent.Context context) {
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.wrappedBuffer(message.config));

        ServerConfig.allowReClinging = buffer.readBoolean();
        ServerConfig.onFallDoubleJump = buffer.readBoolean();
        ServerConfig.onFallWallCling = buffer.readBoolean();
        ServerConfig.exhaustionWallJump = buffer.readDouble();
        ServerConfig.minFallDistance = buffer.readDouble();
        ServerConfig.elytraSpeedBoost = buffer.readDouble();
        ServerConfig.sprintSpeedBoost = buffer.readDouble();
        ServerConfig.stepAssist = buffer.readBoolean();
        ServerConfig.useDoubleJump = buffer.readBoolean();
        ServerConfig.useWallJump = buffer.readBoolean();
        ServerConfig.wallJumpHeight = buffer.readDouble();
        ServerConfig.wallSlideDelay = buffer.readInt();
        ServerConfig.stopWallSlideDelay = buffer.readInt();
        ServerConfig.maxWallJumps = buffer.readInt();
        ServerConfig.blockList = readList(buffer);
        ServerConfig.blockListMode = buffer.readEnum(BlockListMode.class);
        ServerConfig.enableEnchantments = buffer.readBoolean();
        ServerConfig.enableWallJump = buffer.readBoolean();
        ServerConfig.enableDoubleJump = buffer.readBoolean();
        ServerConfig.enableSpeedBoost = buffer.readBoolean();
        ServerConfig.speedBoostMultiplier = buffer.readDouble();
    }

    private static List<String> readList(FriendlyByteBuf buffer) {
        int size = buffer.readInt();
        List<String> list = Lists.newArrayListWithCapacity(size);
        for (int i = 0; i < size; i++) list.add(buffer.readUtf());
        return list;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
