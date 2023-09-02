package com.jahirtrap.walljump.enchantment;

import com.jahirtrap.walljump.init.WallJumpModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class WallJumpEnchantment extends Enchantment {
    public WallJumpEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinCost(int level) {
        return 20;
    }

    @Override
    public int getMaxCost(int level) {
        return 60;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (WallJumpModConfig.COMMON.useWallJump.get() || !WallJumpModConfig.COMMON.enableEnchantments.get())
            return false;
        return stack.canApplyAtEnchantingTable(this);
    }
}
