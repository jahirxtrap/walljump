package com.jahirtrap.walljump;

import com.jahirtrap.walljump.init.WallJumpModConfig;
import com.jahirtrap.walljump.network.PayloadHandler;
import com.jahirtrap.walljump.proxy.ClientProxy;
import com.jahirtrap.walljump.proxy.CommonProxy;
import com.jahirtrap.walljump.util.configlib.TXFConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(WallJumpMod.MODID)
public class WallJumpMod {

    public static final String MODID = "walljump";
    public static final CommonProxy PROXY = (FMLEnvironment.dist == Dist.CLIENT) ? new ClientProxy() : new CommonProxy();

    public WallJumpMod(IEventBus bus) {
        TXFConfig.init(MODID, WallJumpModConfig.class);
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () ->
                (client, parent) -> TXFConfig.getScreen(parent, MODID));

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
