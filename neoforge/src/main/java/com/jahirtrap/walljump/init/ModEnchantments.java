package com.jahirtrap.walljump.init;

import com.jahirtrap.walljump.enchantment.DoubleJumpEnchantment;
import com.jahirtrap.walljump.enchantment.SpeedBoostEnchantment;
import com.jahirtrap.walljump.enchantment.WallJumpEnchantment;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Registries.ENCHANTMENT, MODID);

    public static final Supplier<Enchantment> WALL_JUMP = register("wall_jump", WallJumpEnchantment::new, ServerConfig.enableWallJump);
    public static final Supplier<Enchantment> DOUBLE_JUMP = register("double_jump", DoubleJumpEnchantment::new, ServerConfig.enableDoubleJump);
    public static final Supplier<Enchantment> SPEED_BOOST = register("speed_boost", SpeedBoostEnchantment::new, ServerConfig.enableSpeedBoost);

    public static Supplier<Enchantment> register(String name, Supplier<Enchantment> supplier, boolean enable) {
        return (ServerConfig.enableEnchantments && enable) ? ENCHANTMENTS.register(name, supplier) : null;
    }

    public static void init(IEventBus bus) {
        ENCHANTMENTS.register(bus);
    }
}
