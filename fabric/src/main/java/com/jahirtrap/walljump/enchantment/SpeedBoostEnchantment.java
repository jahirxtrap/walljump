package com.jahirtrap.walljump.enchantment;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class SpeedBoostEnchantment extends Enchantment {
    public SpeedBoostEnchantment() {
        super(definition(ItemTags.FOOT_ARMOR_ENCHANTABLE, 2, 3, Enchantment.dynamicCost(15, 15), Enchantment.dynamicCost(60, 60), 2, EquipmentSlot.FEET));
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return super.canEnchant(stack) || stack.getItem() instanceof ElytraItem;
    }
}
