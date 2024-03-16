package com.jahirtrap.walljump.logic.mixin;

import com.jahirtrap.walljump.WallJumpMod;
import com.jahirtrap.walljump.init.WallJumpEnchantments;
import com.jahirtrap.walljump.init.WallJumpModConfig;
import com.jahirtrap.walljump.util.LocalPlayerWallJump;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerDoubleJumpMixin extends AbstractClientPlayer implements LocalPlayerWallJump {

    @Shadow
    public abstract boolean isHandsBusy();

    @Shadow
    public Input input;

    private int jumpCount = 0;
    private boolean jumpKey = false;

    public LocalPlayerDoubleJumpMixin(ClientLevel level, GameProfile profile) {
        super(level, profile);
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void doubleJumpTickMovement(CallbackInfo ci) {
        this.doDoubleJump();
    }

    private void doDoubleJump() {
        var pos = this.position();
        var motion = this.getDeltaMovement();
        if (!WallJumpModConfig.onFallDoubleJump && motion.y < -0.80) return;

        var box = new AABB(pos.x(), pos.y() + this.getEyeHeight(this.getPose()) * 0.8, pos.z(), pos.x(), pos.y() + this.getBbHeight(), pos.z());

        if (this.onGround() || this.level().containsAnyLiquid(box) || this.ticksWallClinged > 0 || this.isHandsBusy() || this.getAbilities().mayfly) {
            this.jumpCount = this.getMultiJumps();
        } else if (this.input.jumping) {
            if (!this.jumpKey && this.jumpCount > 0 && motion.y() < 0.333 && this.ticksWallClinged < 1 && this.getFoodData().getFoodLevel() > 0) {
                this.jumpFromGround();
                this.jumpCount--;

                this.resetFallDistance();
                var passedData = new FriendlyByteBuf(Unpooled.buffer());
                passedData.writeFloat(this.fallDistance);
                ClientPlayNetworking.send(WallJumpMod.FALL_DISTANCE_PACKET_ID, passedData);
            }

            this.jumpKey = true;
        } else {
            this.jumpKey = false;
        }
    }

    private int getMultiJumps() {
        var jumpCount = 0;
        if (WallJumpModConfig.useDoubleJump) jumpCount += 1;

        var stack = this.getItemBySlot(EquipmentSlot.FEET);
        if (!stack.isEmpty()) {
            var enchantments = EnchantmentHelper.getEnchantments(stack);
            if (enchantments.containsKey(WallJumpEnchantments.DOUBLE_JUMP))
                jumpCount += enchantments.get(WallJumpEnchantments.DOUBLE_JUMP);
        }

        return jumpCount;
    }
}
