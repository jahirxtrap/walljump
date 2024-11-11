package com.jahirtrap.walljump.init;

import com.jahirtrap.walljump.enchantment.DoubleJumpEnchantment;
import com.jahirtrap.walljump.enchantment.SpeedBoostEnchantment;
import com.jahirtrap.walljump.enchantment.WallJumpEnchantment;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public class ModEnchantments {
    public static final Enchantment WALL_JUMP = ServerConfig.enableEnchantments && ServerConfig.enableWallJump ?register("wall_jump", new WallJumpEnchantment()): null;
    public static final Enchantment DOUBLE_JUMP = ServerConfig.enableEnchantments && ServerConfig.enableWallJump ?register("double_jump", new DoubleJumpEnchantment()):null;
    public static final Enchantment SPEED_BOOST = ServerConfig.enableEnchantments && ServerConfig.enableWallJump ?register("speed_boost", new SpeedBoostEnchantment()):null;

    public static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(BuiltInRegistries.ENCHANTMENT, new ResourceLocation(MODID, name), enchantment);
    }

    public static void init() {
    }
}
