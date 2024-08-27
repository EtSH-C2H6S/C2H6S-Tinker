package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import mekanism.api.radiation.capability.IRadiationEntity;
import mekanism.common.capabilities.Capabilities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import static com.c2h6s.etshtinker.util.modloaded.*;


public class radiationremoval extends etshmodifieriii{
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (isCorrectSlot&&holder!=null&&Mekenabled){
            LazyOptional<IRadiationEntity> radiationCap = holder.getCapability(Capabilities.RADIATION_ENTITY);
            int a=0;
            while (a<=20*modifier.getLevel()) {
                radiationCap.ifPresent(IRadiationEntity::decay);
                a++;
            }
        }
    }
}
