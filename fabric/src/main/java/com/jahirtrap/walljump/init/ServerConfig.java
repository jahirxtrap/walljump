package com.jahirtrap.walljump.init;

import com.jahirtrap.walljump.init.ModConfig.BlockListMode;

import java.lang.reflect.Field;
import java.util.List;

public class ServerConfig {
    public static boolean allowReClinging = ModConfig.allowReClinging;
    public static boolean onFallDoubleJump = ModConfig.onFallDoubleJump;
    public static double exhaustionWallJump = ModConfig.exhaustionWallJump;
    public static double minFallDistance = ModConfig.minFallDistance;
    public static double elytraSpeedBoost = ModConfig.elytraSpeedBoost;
    public static double sprintSpeedBoost = ModConfig.sprintSpeedBoost;
    public static boolean stepAssist = ModConfig.stepAssist;
    public static boolean useDoubleJump = ModConfig.useDoubleJump;
    public static boolean useWallJump = ModConfig.useWallJump;
    public static double wallJumpHeight = ModConfig.wallJumpHeight;
    public static int wallSlideDelay = ModConfig.wallSlideDelay;
    public static int stopWallSlideDelay = ModConfig.stopWallSlideDelay;
    public static int maxWallJumps = ModConfig.maxWallJumps;
    public static List<String> blockList = ModConfig.blockList;
    public static BlockListMode blockListMode = ModConfig.blockListMode;
    public static boolean enableEnchantments = ModConfig.enableEnchantments;
    public static boolean enableWallJump = ModConfig.enableWallJump;
    public static boolean enableDoubleJump = ModConfig.enableDoubleJump;
    public static boolean enableSpeedBoost = ModConfig.enableSpeedBoost;
    public static double speedBoostMultiplier = ModConfig.speedBoostMultiplier;

    public static void reset() {
        try {
            Field[] fields = ServerConfig.class.getDeclaredFields();
            for (Field field : fields) {
                Field config = ModConfig.class.getDeclaredField(field.getName());
                field.set(null, config.get(null));
            }
        } catch (Exception ignored) {
        }
    }

    public static void reset(String config) {
        try {
            Field serverField = ServerConfig.class.getDeclaredField(config);
            Field modField = ModConfig.class.getDeclaredField(config);
            serverField.set(null, modField.get(null));
        } catch (Exception ignored) {
        }
    }
}
