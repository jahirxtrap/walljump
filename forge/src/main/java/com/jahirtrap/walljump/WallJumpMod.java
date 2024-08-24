package com.jahirtrap.walljump;

import com.jahirtrap.configlib.TXFConfig;
import com.jahirtrap.walljump.init.ModConfig;
import com.jahirtrap.walljump.proxy.ClientProxy;
import com.jahirtrap.walljump.proxy.CommonProxy;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WallJumpMod.MODID)
public class WallJumpMod {

    public static final String MODID = "walljump";
    public static final CommonProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public WallJumpMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        TXFConfig.init(MODID, ModConfig.class);
        bus.addListener(this::onCommonSetup);
        bus.addListener(this::onClientSetup);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        PROXY.setupCommon();
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        PROXY.setupClient();
    }
}
