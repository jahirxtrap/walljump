package com.jahirtrap.walljump.logic.mixin;

import com.jahirtrap.walljump.init.WallJumpEnchantments;
import com.jahirtrap.walljump.init.WallJumpModConfig;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerSpeedBoostMixin extends AbstractClientPlayer {

    @Shadow
    public abstract boolean isShiftKeyDown();

    public LocalPlayerSpeedBoostMixin(ClientLevel level, GameProfile profile) {
        super(level, profile);
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void miscellaneousTickMovement(CallbackInfo ci) {
        this.doSpeedBoost();
    }

    private void doSpeedBoost() {
        if (!WallJumpModConfig.enableEnchantments || !WallJumpModConfig.enableSpeedBoost)
            return;
        var jumpBoostLevel = 0;
        var jumpBoostEffect = this.getEffect(MobEffects.JUMP);
        if (jumpBoostEffect != null) jumpBoostLevel = jumpBoostEffect.getAmplifier() + 1;
        this.getAbilities().setFlyingSpeed((float) (this.getSpeed() * (this.isSprinting() ? 1 : 1.3) / 5) * (jumpBoostLevel * 0.5F + 1));

        var pos = this.position();
        var look = this.getLookAngle();
        var motion = this.getDeltaMovement();

        if (this.isFallFlying()) {
            if (this.isCrouching()) {
                if (this.getXRot() < 30F)
                    this.setDeltaMovement(motion.subtract(motion.scale(0.05)));
            } else if (this.isSprinting()) {
                float elytraSpeedBoost = (float) WallJumpModConfig.elytraSpeedBoost + (getEquipmentBoost(EquipmentSlot.CHEST) * 0.75F);
                var boost = new Vec3(look.x, look.y + 0.5, look.z).normalize().scale(elytraSpeedBoost);
                if (motion.length() <= boost.length())
                    this.setDeltaMovement(motion.add(boost.scale(0.05)));
                if (boost.length() > 0.5)
                    this.level().addParticle(ParticleTypes.FIREWORK, pos.x, pos.y, pos.z, 0, 0, 0);
            }
        } else if (this.isSprinting()) {
            var sprintSpeedBoost = (float) WallJumpModConfig.sprintSpeedBoost + (getEquipmentBoost(EquipmentSlot.FEET) * 0.375F);
            if (!this.onGround())
                sprintSpeedBoost /= 3.125F;

            var boost = new Vec3(look.x, 0.0, look.z).scale(sprintSpeedBoost * 0.125F);
            this.setDeltaMovement(motion.add(boost));
        }
    }

    private int getEquipmentBoost(EquipmentSlot slot) {
        var stack = this.getItemBySlot(slot);
        if (!stack.isEmpty()) {
            var enchantments = EnchantmentHelper.getEnchantmentsForCrafting(stack);
            Holder<Enchantment> spHolder = this.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(WallJumpEnchantments.SPEED_BOOST);
            return enchantments.getLevel(spHolder);
        }

        return 0;
    }
}
