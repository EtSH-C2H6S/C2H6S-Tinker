package com.c2h6s.etshtinker.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class getMainOrOff {
    public static int getMainLevel(LivingEntity entity, Modifier modifier){
        if (entity!=null) {
            ToolStack toolStack = ToolStack.from(entity.getItemBySlot(EquipmentSlot.MAINHAND));
            if (!toolStack.isBroken()) {
                return ModifierUtil.getModifierLevel(entity.getMainHandItem(), modifier.getId());

            }
        }
        return 0;
    }
    public static int getOffLevel(LivingEntity entity, Modifier modifier){
        if (entity!= null) {
            ToolStack toolStack = ToolStack.from(entity.getItemBySlot(EquipmentSlot.OFFHAND));
            if (!toolStack.isBroken()) {
                return ModifierUtil.getModifierLevel(entity.getOffhandItem(), modifier.getId());
            }
        }
        return 0;
    }
}
