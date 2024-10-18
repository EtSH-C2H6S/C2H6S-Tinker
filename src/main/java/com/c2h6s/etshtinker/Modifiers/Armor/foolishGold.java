package com.c2h6s.etshtinker.Modifiers.Armor;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.util.slotUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class foolishGold extends etshmodifieriii {
    public foolishGold(){
        MinecraftForge.EVENT_BUS.addListener(this::onTargetChange);
    }
    private void onTargetChange(LivingChangeTargetEvent event) {
        if (event.getNewTarget() !=null&&event.getEntity() instanceof AbstractPiglin){
            LivingEntity holder = event.getNewTarget();
            int modifierLv =0;
            for(EquipmentSlot slot: slotUtil.ARMOR){
                ItemStack stack = holder.getItemBySlot(slot);
                if (stack.getItem() instanceof IModifiable){
                    ToolStack tool =ToolStack.from(stack);
                    if (tool.getModifierLevel(this)>0){
                        modifierLv+=tool.getModifierLevel(this);
                    }
                }
            }
            if (modifierLv >=2&&event.getNewTarget()!=event.getEntity().getLastHurtByMob()){
                event.setCanceled(true);
            }
        }
    }
}
