package com.jahirtrap.walljump.init;

import com.jahirtrap.walljump.enchantment.DoubleJumpEnchantment;
import com.jahirtrap.walljump.enchantment.SpeedBoostEnchantment;
import com.jahirtrap.walljump.enchantment.WallJumpEnchantment;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public class ModEnchantments {
    public static final Enchantment WALL_JUMP = register("wall_jump", new WallJumpEnchantment(), ServerConfig.enableWallJump);
    public static final Enchantment DOUBLE_JUMP = register("double_jump", new DoubleJumpEnchantment(), ServerConfig.enableDoubleJump);
    public static final Enchantment SPEED_BOOST = register("speed_boost", new SpeedBoostEnchantment(), ServerConfig.enableSpeedBoost);

    public static Enchantment register(String name, Enchantment enchantment, boolean enable) {
        return (ServerConfig.enableEnchantments && enable) ? Registry.register(Registry.ENCHANTMENT, new ResourceLocation(MODID, name), enchantment) : null;
    }

    public static void init() {
    }
}
