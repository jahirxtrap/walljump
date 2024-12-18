package com.jahirtrap.walljump.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> WALL_JUMP = create("wall_jump");
    public static final ResourceKey<Enchantment> DOUBLE_JUMP = create("double_jump");
    public static final ResourceKey<Enchantment> SPEED_BOOST = create("speed_boost");

    public static ResourceKey<Enchantment> create(String name) {
        return (ServerConfig.enableEnchantments) ? ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(MODID, name)) : null;
    }
}
