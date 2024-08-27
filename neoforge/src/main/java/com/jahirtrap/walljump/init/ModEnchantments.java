package com.jahirtrap.walljump.init;

import com.jahirtrap.walljump.enchantment.DoubleJumpEnchantment;
import com.jahirtrap.walljump.enchantment.SpeedBoostEnchantment;
import com.jahirtrap.walljump.enchantment.WallJumpEnchantment;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Registries.ENCHANTMENT, MODID);

    public static final DeferredHolder<Enchantment, WallJumpEnchantment> WALL_JUMP = ENCHANTMENTS.register("wall_jump", WallJumpEnchantment::new);
    public static final DeferredHolder<Enchantment, DoubleJumpEnchantment> DOUBLE_JUMP = ENCHANTMENTS.register("double_jump", DoubleJumpEnchantment::new);
    public static final DeferredHolder<Enchantment, SpeedBoostEnchantment> SPEED_BOOST = ENCHANTMENTS.register("speed_boost", SpeedBoostEnchantment::new);

    public static void init(IEventBus bus) {
        ENCHANTMENTS.register(bus);
    }
}
