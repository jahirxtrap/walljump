package com.jahirtrap.walljump.init;

import com.jahirtrap.walljump.WallJumpMod;
import com.jahirtrap.walljump.enchantment.DoubleJumpEnchantment;
import com.jahirtrap.walljump.enchantment.SpeedBoostEnchantment;
import com.jahirtrap.walljump.enchantment.WallJumpEnchantment;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class WallJumpEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Registries.ENCHANTMENT, WallJumpMod.MODID);
    public static final DeferredHolder<Enchantment, WallJumpEnchantment> WALL_JUMP = ENCHANTMENTS.register("wall_jump", WallJumpEnchantment::new);
    public static final DeferredHolder<Enchantment, DoubleJumpEnchantment> DOUBLE_JUMP = ENCHANTMENTS.register("double_jump", DoubleJumpEnchantment::new);
    public static final DeferredHolder<Enchantment, SpeedBoostEnchantment> SPEED_BOOST = ENCHANTMENTS.register("speed_boost", SpeedBoostEnchantment::new);
}
