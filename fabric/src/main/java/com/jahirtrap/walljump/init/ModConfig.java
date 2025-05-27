package com.jahirtrap.walljump.init;

import com.google.common.collect.Lists;
import com.jahirtrap.configlib.TXFConfig;

import java.util.List;

public class ModConfig extends TXFConfig {
    @Entry(name = "Allow Re Clinging")
    public static boolean allowReClinging = true;
    @Entry(name = "Auto Rotation")
    public static boolean autoRotation = false;
    @Entry(name = "On Fall Double Jump")
    public static boolean onFallDoubleJump = true;
    @Entry(name = "On Fall Wall Cling")
    public static boolean onFallWallCling = true;
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
    @Entry(name = "Stop Wall Slide Delay", min = 0, max = Integer.MAX_VALUE)
    public static int stopWallSlideDelay = 72000;
    @Entry(name = "Max Wall Jumps", min = 0, max = Integer.MAX_VALUE)
    public static int maxWallJumps = 72000;
    @Entry(name = "Play Falling Sound")
    public static boolean playFallingSound = true;
    @Entry(name = "Block List", idMode = 1)
    public static List<String> blockList = Lists.newArrayList();
    @Entry(name = "Block List Mode")
    public static BlockListMode blockListMode = BlockListMode.BLACKLIST;
    @Comment(centered = true)
    public static Comment enchantments;
    @Entry(name = "Enable Enchantments", itemDisplay = "minecraft:enchanted_book")
    public static boolean enableEnchantments = true;
    @Entry(name = "Enable Wall Jump Enchantment", itemDisplay = "minecraft:enchanted_book")
    public static boolean enableWallJump = true;
    @Entry(name = "Enable Double Jump Enchantment", itemDisplay = "minecraft:enchanted_book")
    public static boolean enableDoubleJump = true;
    @Entry(name = "Enable Speed Boost Enchantment", itemDisplay = "minecraft:enchanted_book")
    public static boolean enableSpeedBoost = true;
    @Entry(name = "Speed Boost Enchantment Multiplier", min = 0.25, max = 1, precision = 4, isSlider = true, itemDisplay = "minecraft:enchanted_book")
    public static double speedBoostMultiplier = 0.5;

    public enum BlockListMode {DISABLED, BLACKLIST, WHITELIST}
}
