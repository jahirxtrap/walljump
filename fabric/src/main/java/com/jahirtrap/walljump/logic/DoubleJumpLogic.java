package com.jahirtrap.walljump.logic;

import com.jahirtrap.walljump.WallJumpMod;
import com.jahirtrap.walljump.init.WallJumpEnchantments;
import com.jahirtrap.walljump.init.WallJumpModConfig;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class DoubleJumpLogic {
    private static int jumpCount = 0;
    private static boolean jumpKey = false;

    public static void doDoubleJump(LocalPlayer pl) {
        if ((!WallJumpModConfig.enableEnchantments || !WallJumpModConfig.enableDoubleJump) && !WallJumpModConfig.useDoubleJump)
            return;
        Vec3 pos = pl.position();
        Vec3 motion = pl.getDeltaMovement();
        if (!WallJumpModConfig.onFallDoubleJump && motion.y < -0.80) return;

        AABB box = new AABB(pos.x, pos.y + (pl.getEyeHeight() * .8), pos.z, pos.x, pos.y + pl.getBbHeight(), pos.z);

        if (pl.onGround() || pl.level().containsAnyLiquid(box) || WallJumpLogic.ticksWallClinged > 0 || pl.isPassenger() || pl.getAbilities().mayfly) {
            jumpCount = getMultiJumps(pl);
        } else if (pl.input.jumping) {
            if (!jumpKey && jumpCount > 0 && motion.y < 0.333 && WallJumpLogic.ticksWallClinged < 1 && pl.getFoodData().getFoodLevel() > 0) {
                pl.jumpFromGround();
                jumpCount--;

                pl.resetFallDistance();
                FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
                passedData.writeFloat(pl.fallDistance);
                ClientPlayNetworking.send(WallJumpMod.FALL_DISTANCE_PACKET_ID, passedData);
            }

            jumpKey = true;
        } else {
            jumpKey = false;
        }
    }

    private static int getMultiJumps(LocalPlayer pl) {
        int jumpCount = 0;
        if (WallJumpModConfig.useDoubleJump) jumpCount += 1;
        if (!WallJumpModConfig.enableEnchantments || !WallJumpModConfig.enableDoubleJump)
            return jumpCount;
        ItemStack stack = pl.getItemBySlot(EquipmentSlot.FEET);
        if (!stack.isEmpty()) {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
            if (enchantments.containsKey(WallJumpEnchantments.DOUBLE_JUMP))
                jumpCount += enchantments.get(WallJumpEnchantments.DOUBLE_JUMP);
        }

        return jumpCount;
    }
}
