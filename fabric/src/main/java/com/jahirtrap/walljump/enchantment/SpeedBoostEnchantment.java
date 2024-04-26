package com.jahirtrap.walljump.enchantment;

import com.jahirtrap.walljump.init.WallJumpModConfig;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class SpeedBoostEnchantment extends Enchantment {
    public SpeedBoostEnchantment() {
        super(definition(ItemTags.FOOT_ARMOR_ENCHANTABLE, 2, 3, Enchantment.dynamicCost(15, 15), Enchantment.dynamicCost(60, 60), 2, EquipmentSlot.FEET));
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        if (!WallJumpModConfig.enableEnchantments || !WallJumpModConfig.enableSpeedBoost)
            return false;
        return stack.isEnchantable() && (stack.getItem() instanceof ElytraItem || stack.getItem() instanceof ArmorItem);
    }
}
