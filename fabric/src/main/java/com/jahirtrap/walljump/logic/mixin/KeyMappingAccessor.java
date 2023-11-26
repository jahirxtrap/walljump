package com.jahirtrap.walljump.logic.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyMapping.class)
public interface KeyMappingAccessor {
    @Accessor(value = "key")
    InputConstants.Key getKey();

    @Accessor(value = "clickCount")
    int getClickCount();

    @Accessor(value = "clickCount")
    void setClickCount(int value);
}
