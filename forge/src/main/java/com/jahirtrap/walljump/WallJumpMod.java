package com.jahirtrap.walljump;

import com.jahirtrap.configlib.TXFConfig;
import com.jahirtrap.walljump.init.ModConfig;
import com.jahirtrap.walljump.init.ModEnchantments;
import com.jahirtrap.walljump.network.PacketHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WallJumpMod.MODID)
public class WallJumpMod {

    public static final String MODID = "walljump";

    public WallJumpMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        TXFConfig.init(MODID, ModConfig.class);
        ModEnchantments.init(bus);
        PacketHandler.init();
    }
}
