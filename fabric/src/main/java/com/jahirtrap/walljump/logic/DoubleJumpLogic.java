package com.jahirtrap.walljump.logic;

import com.jahirtrap.walljump.init.ModEnchantments;
import com.jahirtrap.walljump.init.ServerConfig;
import com.jahirtrap.walljump.network.message.MessageFallDistance;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class DoubleJumpLogic {
    private static int jumpCount = 0;
    private static boolean jumpKey = false;

    public static void doDoubleJump(LocalPlayer pl) {
        if ((!ServerConfig.enableEnchantments || !ServerConfig.enableDoubleJump) && !ServerConfig.useDoubleJump)
            return;
        Vec3 pos = pl.position();
        Vec3 motion = pl.getDeltaMovement();
        if (!ServerConfig.onFallDoubleJump && motion.y < -0.8) return;

        AABB box = new AABB(pos.x, pos.y + (pl.getEyeHeight() * .8), pos.z, pos.x, pos.y + pl.getBbHeight(), pos.z);

        if (pl.onGround() || pl.level().containsAnyLiquid(box) || WallJumpLogic.ticksWallClinged > 0 || pl.isPassenger() || pl.getAbilities().mayfly) {
            jumpCount = getMultiJumps(pl);
        } else if (pl.input.jumping) {
            if (!jumpKey && jumpCount > 0 && motion.y < 0.333 && WallJumpLogic.ticksWallClinged < 1 && pl.getFoodData().getFoodLevel() > 0) {
                pl.jumpFromGround();
                jumpCount--;

                pl.resetFallDistance();
                ClientPlayNetworking.send(new MessageFallDistance(pl.fallDistance));
            }

            jumpKey = true;
        } else {
            jumpKey = false;
        }
    }

    private static int getMultiJumps(LocalPlayer pl) {
        int jumpCount = 0;
        if (ServerConfig.useDoubleJump) jumpCount += 1;
        if (!ServerConfig.enableEnchantments || !ServerConfig.enableDoubleJump)
            return jumpCount;
        ItemStack stack = pl.getItemBySlot(EquipmentSlot.FEET);
        if (!stack.isEmpty()) {
            ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(stack);
            jumpCount += enchantments.getLevel(ModEnchantments.DOUBLE_JUMP);
        }

        return jumpCount;
    }
}
