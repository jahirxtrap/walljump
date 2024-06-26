package com.jahirtrap.walljump.enchantment;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;

public class DoubleJumpEnchantment extends Enchantment {
    public DoubleJumpEnchantment() {
        super(definition(ItemTags.FOOT_ARMOR_ENCHANTABLE, 2, 2, Enchantment.dynamicCost(20, 20), Enchantment.dynamicCost(60, 60), 2, EquipmentSlot.FEET));
    }

    @Override
    protected boolean checkCompatibility(Enchantment enchantment) {
        if (enchantment instanceof ProtectionEnchantment protection)
            return protection.type != ProtectionEnchantment.Type.FALL;
        return this != enchantment;
    }
}
