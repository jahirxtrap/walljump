package com.jahirtrap.walljump.enchantment;

import com.jahirtrap.walljump.init.WallJumpModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;

public class DoubleJumpEnchantment extends Enchantment {
    public DoubleJumpEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinCost(int level) {
        return level * 20;
    }

    @Override
    public int getMaxCost(int level) {
        return level * 60;
    }

    @Override
    protected boolean checkCompatibility(Enchantment enchantment) {
        if (enchantment instanceof ProtectionEnchantment protection)
            return protection.type != ProtectionEnchantment.Type.FALL;
        return this != enchantment;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        if (!WallJumpModConfig.enableEnchantments || !WallJumpModConfig.enableDoubleJump)
            return false;
        return stack.getItem() instanceof ArmorItem && stack.isEnchantable();
    }
}
