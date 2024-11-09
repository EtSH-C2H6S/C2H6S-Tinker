package com.c2h6s.etshtinker.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;

import java.util.List;

public class slotUtil {
    public static List<EquipmentSlot> ARMOR =List.of(EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.LEGS,EquipmentSlot.FEET);
    public static List<EquipmentSlot> ALL =List.of(EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.LEGS,EquipmentSlot.FEET,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND);
    public static List<EquipmentSlot> HAND =List.of(EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND);
    public static int getArmorTotalLevel(@NotNull LivingEntity entity, ModifierId modifierId){
        int a =0;
        for (EquipmentSlot slot:ARMOR){
            a+= ModifierUtil.getModifierLevel(entity.getItemBySlot(slot),modifierId);
        }
        return a;
    }
    public static int getAllTotalLevel(@NotNull LivingEntity entity, ModifierId modifierId){
        int a =0;
        for (EquipmentSlot slot:ALL){
            a+= ModifierUtil.getModifierLevel(entity.getItemBySlot(slot),modifierId);
        }
        return a;
    }
    public static int getHandTotalLevel(@NotNull LivingEntity entity, ModifierId modifierId){
        int a =0;
        for (EquipmentSlot slot:HAND){
            a+= ModifierUtil.getModifierLevel(entity.getItemBySlot(slot),modifierId);
        }
        return a;
    }
}
