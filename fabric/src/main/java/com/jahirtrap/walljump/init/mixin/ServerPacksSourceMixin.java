package com.jahirtrap.walljump.init.mixin;

import com.jahirtrap.walljump.init.ModPacks;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.world.level.validation.DirectoryValidator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;

@Mixin(ServerPacksSource.class)
public abstract class ServerPacksSourceMixin {

    @Inject(method = "createPackRepository(Ljava/nio/file/Path;Lnet/minecraft/world/level/validation/DirectoryValidator;)Lnet/minecraft/server/packs/repository/PackRepository;", at = @At("RETURN"))
    private static void createPackRepository(Path folder, DirectoryValidator validator, CallbackInfoReturnable<PackRepository> cir) {
        ModPacks.init(cir.getReturnValue());
    }

    @Inject(method = "createVanillaTrustedRepository", at = @At("RETURN"))
    private static void createVanillaTrustedRepository(CallbackInfoReturnable<PackRepository> cir) {
        ModPacks.init(cir.getReturnValue());
    }
}
