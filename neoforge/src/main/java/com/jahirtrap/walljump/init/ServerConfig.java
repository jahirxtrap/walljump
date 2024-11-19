package com.jahirtrap.walljump.init;

import java.lang.reflect.Field;

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
}