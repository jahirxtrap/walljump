package com.jahirtrap.walljump;

import com.jahirtrap.configlib.TXFConfig;
import com.jahirtrap.walljump.init.WallJumpEnchantments;
import com.jahirtrap.walljump.init.WallJumpModConfig;
import com.jahirtrap.walljump.network.PayloadHandler;
import net.fabricmc.api.ModInitializer;

public class WallJumpMod implements ModInitializer {

    public static final String MODID = "walljump";

    @Override
    public void onInitialize() {
        TXFConfig.init(MODID, WallJumpModConfig.class);
        WallJumpEnchantments.init();
        PayloadHandler.init();
    }
}
