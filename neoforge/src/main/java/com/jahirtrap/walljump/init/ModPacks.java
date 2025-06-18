package com.jahirtrap.walljump.init;

import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.*;
import net.minecraft.world.flag.FeatureFlagSet;
import net.neoforged.fml.ModList;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public class ModPacks {
    public static void init(PackRepository repository) {
        ServerConfig.reset("enableEnchantments");
        enchantments(repository);
    }

    private static void enchantments(PackRepository repository) {
        if (ServerConfig.enableEnchantments) {
            Path path = ModList.get().getModFileById(MODID).getFile().findResource("packs/enchantments");
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.walljump.enchantments.description"), SharedConstants.getCurrentVersion().packVersion(PackType.SERVER_DATA), Optional.empty());

            if (path != null) {
                RepositorySource source = (consumer) -> consumer.accept(new Pack(
                        new PackLocationInfo("walljump/enchantments", Component.translatable("pack.walljump.enchantments.title"), PackSource.BUILT_IN, Optional.empty()),
                        new PathPackResources.PathResourcesSupplier(path),
                        new Pack.Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()),
                        new PackSelectionConfig(true, Pack.Position.TOP, false)
                ));

                repository.sources.add(source);
            }
        }
    }
}
