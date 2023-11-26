package com.jahirtrap.walljump.init;

import com.jahirtrap.walljump.enchantment.DoubleJumpEnchantment;
import com.jahirtrap.walljump.enchantment.SpeedBoostEnchantment;
import com.jahirtrap.walljump.enchantment.WallJumpEnchantment;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public class WallJumpEnchantments {
    public static final Enchantment WALL_JUMP = RegistryObject("wall_jump", new WallJumpEnchantment());
    public static final Enchantment DOUBLE_JUMP = RegistryObject("double_jump", new DoubleJumpEnchantment());
    public static final Enchantment SPEED_BOOST = RegistryObject("speed_boost", new SpeedBoostEnchantment());

    public static void init() {
    }

    public static Enchantment RegistryObject(String name, Enchantment enchantment) {
        return Registry.register(BuiltInRegistries.ENCHANTMENT, new ResourceLocation(MODID, name), enchantment);
    }
}
