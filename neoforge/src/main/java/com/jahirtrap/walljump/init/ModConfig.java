package com.jahirtrap.walljump.init;

import com.google.common.collect.Lists;
import com.jahirtrap.configlib.TXFConfig;

import java.util.List;

public class ModConfig extends TXFConfig {
    public static final String GENERAL = "general", ENCHANTMENTS = "enchantments";

    @Entry(category = GENERAL, name = "Allow Re Clinging")
    public static boolean allowReClinging = true;
    @Entry(category = GENERAL, name = "Auto Rotation")
    public static boolean autoRotation = false;
    @Entry(category = GENERAL, name = "On Fall Double Jump")
    public static boolean onFallDoubleJump = true;
    @Entry(category = GENERAL, name = "On Fall Wall Cling")
    public static boolean onFallWallCling = true;
    @Entry(category = GENERAL, name = "Exhaustion Wall Jump", min = 0.0, max = 5.0)
    public static double exhaustionWallJump = 0.8;
    @Entry(category = GENERAL, name = "Min Fall Distance", min = 3.0, max = 256)
    public static double minFallDistance = 3.0;
    @Entry(category = GENERAL, name = "Elytra Speed Boost", min = 0.0, max = 5.0)
    public static double elytraSpeedBoost = 0.0;
    @Entry(category = GENERAL, name = "Sprint Speed Boost", min = 0.0, max = 5.0)
    public static double sprintSpeedBoost = 0.0;
    @Entry(category = GENERAL, name = "Step Assist")
    public static boolean stepAssist = true;
    @Entry(category = GENERAL, name = "Use Double Jump")
    public static boolean useDoubleJump = false;
    @Entry(category = GENERAL, name = "Use Wall Jump")
    public static boolean useWallJump = true;
    @Entry(category = GENERAL, name = "Wall Jump Height", min = 0.0, max = 1.0)
    public static double wallJumpHeight = 0.55;
    @Entry(category = GENERAL, name = "Wall Slide Delay", min = 0, max = Integer.MAX_VALUE)
    public static int wallSlideDelay = 15;
    @Entry(category = GENERAL, name = "Stop Wall Slide Delay", min = 0, max = Integer.MAX_VALUE)
    public static int stopWallSlideDelay = 72000;
    @Entry(category = GENERAL, name = "Max Wall Jumps", min = 0, max = Integer.MAX_VALUE)
    public static int maxWallJumps = 72000;
    @Entry(category = GENERAL, name = "Play Falling Sound")
    public static boolean playFallingSound = true;
    @Entry(category = GENERAL, name = "Block List", idMode = 1)
    public static List<String> blockList = Lists.newArrayList();
    @Entry(category = GENERAL, name = "Block List Mode")
    public static BlockListMode blockListMode = BlockListMode.BLACKLIST;

    @Entry(category = ENCHANTMENTS, name = "Enable Enchantments", itemDisplay = "minecraft:enchanted_book")
    public static boolean enableEnchantments = true;
    @Entry(category = ENCHANTMENTS, name = "Enable Wall Jump Enchantment", itemDisplay = "minecraft:enchanted_book")
    public static boolean enableWallJump = true;
    @Entry(category = ENCHANTMENTS, name = "Enable Double Jump Enchantment", itemDisplay = "minecraft:enchanted_book")
    public static boolean enableDoubleJump = true;
    @Entry(category = ENCHANTMENTS, name = "Enable Speed Boost Enchantment", itemDisplay = "minecraft:enchanted_book")
    public static boolean enableSpeedBoost = true;
    @Entry(category = ENCHANTMENTS, name = "Speed Boost Enchantment Multiplier", min = 0.25, max = 1, precision = 4, isSlider = true, itemDisplay = "minecraft:enchanted_book")
    public static double speedBoostMultiplier = 0.5;

    public enum BlockListMode {DISABLED, BLACKLIST, WHITELIST}
}
