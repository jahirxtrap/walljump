package com.jahirtrap.walljump.init;

import com.jahirtrap.walljump.enchantment.DoubleJumpEnchantment;
import com.jahirtrap.walljump.enchantment.SpeedBoostEnchantment;
import com.jahirtrap.walljump.enchantment.WallJumpEnchantment;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Registries.ENCHANTMENT, MODID);

    public static final RegistryObject<Enchantment> WALL_JUMP =   ServerConfig.enableEnchantments && ServerConfig.enableWallJump ?ENCHANTMENTS.register("wall_jump", WallJumpEnchantment::new):null;
    public static final RegistryObject<Enchantment> DOUBLE_JUMP = ServerConfig.enableEnchantments && ServerConfig.enableDoubleJump ?ENCHANTMENTS.register("double_jump", DoubleJumpEnchantment::new):null;
    public static final RegistryObject<Enchantment> SPEED_BOOST = ServerConfig.enableEnchantments && ServerConfig.enableSpeedBoost ?ENCHANTMENTS.register("speed_boost", SpeedBoostEnchantment::new):null;

    public static void init(IEventBus bus) {
      if (!ServerConfig.enableEnchantments || !(ServerConfig.enableWallJump || ServerConfig.enableDoubleJump || ServerConfig.enableSpeedBoost)) return;
        ENCHANTMENTS.register(bus);
    }
}
