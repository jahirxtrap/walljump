package com.jahirtrap.walljump.enchantment;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class WallJumpEnchantment extends Enchantment {
    public WallJumpEnchantment() {
        super(definition(ItemTags.FOOT_ARMOR_ENCHANTABLE, 1, 1, Enchantment.dynamicCost(15, 15), Enchantment.dynamicCost(60, 60), 1, EquipmentSlot.FEET));
    }
}
