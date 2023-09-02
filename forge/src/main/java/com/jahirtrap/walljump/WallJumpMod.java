package com.jahirtrap.walljump;

import com.jahirtrap.walljump.init.WallJumpEnchantments;
import com.jahirtrap.walljump.init.WallJumpModConfig;
import com.jahirtrap.walljump.proxy.ClientProxy;
import com.jahirtrap.walljump.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WallJumpMod.MODID)
@Mod.EventBusSubscriber(modid = WallJumpMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WallJumpMod {
    public static final String MODID = "walljump";
    public static final CommonProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public WallJumpMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        WallJumpEnchantments.ENCHANTMENTS.register(bus);

        bus.addListener(this::onCommonSetup);
        bus.addListener(ClientProxy::registerKeyMappings);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WallJumpModConfig.COMMON_SPEC);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        PROXY.setupCommon();
    }
}
