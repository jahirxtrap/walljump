package com.jahirtrap.walljump.logic;

import com.jahirtrap.walljump.init.WallJumpEnchantments;
import com.jahirtrap.walljump.init.WallJumpModConfig;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpeedBoostLogic {
    public static void doSpeedBoost(LocalPlayer pl) {
        int jumpBoostLevel = 0;
        MobEffectInstance jumpBoostEffect = pl.getEffect(MobEffects.JUMP);
        if (jumpBoostEffect != null) jumpBoostLevel = jumpBoostEffect.getAmplifier() + 1;
        pl.flyDist = (float) (pl.getSpeed() * (pl.isSprinting() ? 1 : 1.3) / 5) * (jumpBoostLevel * 0.5f + 1);

        Vec3 pos = pl.position();
        Vec3 look = pl.getLookAngle();
        Vec3 motion = pl.getDeltaMovement();

        if (pl.isFallFlying()) {
            if (pl.isCrouching()) {
                if (pl.getXRot() < 30f)
                    pl.setDeltaMovement(motion.subtract(motion.scale(0.05)));
            } else if (pl.isSprinting()) {
                float elytraSpeedBoost = (float) (WallJumpModConfig.elytraSpeedBoost + (getEquipmentBoost(pl, EquipmentSlot.CHEST) * 0.5f));
                Vec3 boost = new Vec3(look.x, look.y, look.z).normalize().scale(elytraSpeedBoost);
                if (motion.length() <= boost.length())
                    pl.setDeltaMovement(motion.add(boost.scale(0.05)));
                if (boost.length() > 0.5)
                    pl.level().addParticle(ParticleTypes.FIREWORK, pos.x, pos.y, pos.z, 0, 0, 0);
            }
        } else if (pl.isSprinting()) {
            float sprintSpeedBoost = (float) (WallJumpModConfig.sprintSpeedBoost + (getEquipmentBoost(pl, EquipmentSlot.FEET) * 0.25f));
            if (!pl.onGround()) sprintSpeedBoost /= 3.125F;

            Vec3 boost = new Vec3(look.x, 0.0, look.z).scale(sprintSpeedBoost * 0.125F);
            pl.setDeltaMovement(motion.add(boost));
        }
    }

    private static int getEquipmentBoost(LocalPlayer pl, EquipmentSlot slot) {
        ItemStack stack = pl.getItemBySlot(slot);
        if (!stack.isEmpty()) {
            ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(stack);
            return enchantments.getLevel(WallJumpEnchantments.SPEED_BOOST.get());
        }

        return 0;
    }
}
