package com.jahirtrap.walljump.logic.mixin;

import com.jahirtrap.walljump.util.KeyMappingFixer;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(KeyMapping.class)
public abstract class KeyMappingMixin {

    @Final
    @Shadow
    private static Map<String, KeyMapping> ALL;

    @Shadow
    private InputConstants.Key key;

    @Inject(method = "click", at = @At(value = "HEAD"), cancellable = true)
    private static void clickFixed(InputConstants.Key key, CallbackInfo ci) {
        KeyMappingFixer.onKeyDown(key);
        ci.cancel();
    }

    @Inject(method = "set", at = @At(value = "HEAD"), cancellable = true)
    private static void setFixed(InputConstants.Key key, boolean bl, CallbackInfo ci) {
        KeyMappingFixer.setKeyDown(key, bl);
        ci.cancel();
    }

    @Inject(method = "setAll", at = @At(value = "TAIL"))
    private static void setAllToMultiMap(CallbackInfo ci) {
        KeyMappingFixer.clearMap();
        for (KeyMapping keyMapping : ALL.values()) {
            KeyMappingFixer.putKey(((KeyMappingAccessor) keyMapping).getKey(), keyMapping);
        }
    }

    @Inject(method = "<init>(Ljava/lang/String;Lcom/mojang/blaze3d/platform/InputConstants$Type;ILjava/lang/String;)V", at = @At(value = "TAIL"))
    private void putToMultiMap(String translationKey, InputConstants.Type type, int code, String category, CallbackInfo ci) {
        KeyMappingFixer.putKey(key, (KeyMapping) (Object) this);
    }
}
