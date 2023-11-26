package com.jahirtrap.walljump.init;

import com.jahirtrap.walljump.util.configlib.TXFConfig;

public class WallJumpModConfig extends TXFConfig {
    @Entry(name = "Allow Re Clinging")
    public static boolean allowReClinging = true;
    @Entry(name = "Auto Rotation")
    public static boolean autoRotation = false;
    @Entry(name = "Enable Enchantments")
    public static boolean enableEnchantments = true;
    @Entry(name = "Exhaustion Wall Jump", min = 0.0, max = 5.0)
    public static double exhaustionWallJump = 0.8;
    @Entry(name = "Min Fall Distance", min = 3.0, max = 256)
    public static double minFallDistance = 3.0;
    @Entry(name = "Elytra Speed Boost", min = 0.0, max = 5.0)
    public static double elytraSpeedBoost = 0.0;
    @Entry(name = "Sprint Speed Boost", min = 0.0, max = 5.0)
    public static double sprintSpeedBoost = 0.0;
    @Entry(name = "Step Assist")
    public static boolean stepAssist = true;
    @Entry(name = "Use Double Jump")
    public static boolean useDoubleJump = false;
    @Entry(name = "Use Wall Jump")
    public static boolean useWallJump = true;
    @Entry(name = "Wall Jump Height", min = 0.0, max = 1.0)
    public static double wallJumpHeight = 0.55;
    @Entry(name = "Wall Slide Delay", min = 0, max = Integer.MAX_VALUE)
    public static int wallSlideDelay = 15;
    @Entry(name = "Play Fall Sound")
    public static boolean playFallSound = true;
}
