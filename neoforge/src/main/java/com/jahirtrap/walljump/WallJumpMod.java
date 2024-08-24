package com.jahirtrap.walljump;

import com.jahirtrap.configlib.TXFConfig;
import com.jahirtrap.walljump.init.ModConfig;
import com.jahirtrap.walljump.network.PayloadHandler;
import com.jahirtrap.walljump.proxy.ClientProxy;
import com.jahirtrap.walljump.proxy.CommonProxy;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(WallJumpMod.MODID)
public class WallJumpMod {

    public static final String MODID = "walljump";
    public static final CommonProxy PROXY = (FMLEnvironment.dist == Dist.CLIENT) ? new ClientProxy() : new CommonProxy();

    public WallJumpMod(IEventBus bus) {
        TXFConfig.init(MODID, ModConfig.class);
        bus.addListener(this::onCommonSetup);
        bus.addListener(this::onClientSetup);
        bus.addListener(PayloadHandler::register);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        PROXY.setupCommon();
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        PROXY.setupClient();
    }
}
