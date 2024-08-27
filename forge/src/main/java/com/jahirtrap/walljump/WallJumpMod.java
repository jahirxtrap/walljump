package com.jahirtrap.walljump;

import com.jahirtrap.configlib.TXFConfig;
import com.jahirtrap.walljump.init.ModConfig;
import com.jahirtrap.walljump.network.PacketHandler;
import net.minecraftforge.fml.common.Mod;

@Mod(WallJumpMod.MODID)
public class WallJumpMod {

    public static final String MODID = "walljump";

    public WallJumpMod() {
        TXFConfig.init(MODID, ModConfig.class);
        PacketHandler.init();
    }
}
