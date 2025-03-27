package com.jahirtrap.walljump.init.mixin;

import com.jahirtrap.walljump.init.ServerConfig;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Shadow
    public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @ModifyArg(method = "causeFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;causeFallDamage(DFLnet/minecraft/world/damagesource/DamageSource;)Z"), index = 0)
    private double causeFallDamage(double value) {
        if (value > 3 && value <= ServerConfig.minFallDistance) {
            playSound(SoundEvents.GENERIC_SMALL_FALL, 0.5f, 1f);
            return 3;
        }

        return value;
    }
}
