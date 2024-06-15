package com.jahirtrap.walljump.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public class WallJumpEnchantments {
    public static final ResourceKey<Enchantment> WALL_JUMP = register("wall_jump");
    public static final ResourceKey<Enchantment> DOUBLE_JUMP = register("double_jump");
    public static final ResourceKey<Enchantment> SPEED_BOOST = register("speed_boost");

    public static ResourceKey<Enchantment> register(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(MODID, name));
    }
}
