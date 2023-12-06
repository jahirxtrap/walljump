package com.jahirtrap.walljump.enchantment;

import com.jahirtrap.walljump.init.WallJumpModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SpeedBoostEnchantment extends Enchantment {
    public SpeedBoostEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int level) {
        return level * 15;
    }

    @Override
    public int getMaxCost(int level) {
        return level * 60;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return super.canEnchant(stack) || stack.getItem() instanceof ElytraItem;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (!WallJumpModConfig.enableEnchantments || !WallJumpModConfig.enableSpeedBoost)
            return false;
        return stack.canApplyAtEnchantingTable(this);
    }
}
