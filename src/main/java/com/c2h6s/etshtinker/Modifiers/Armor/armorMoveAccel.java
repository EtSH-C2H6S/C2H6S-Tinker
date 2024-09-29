package com.c2h6s.etshtinker.Modifiers.Armor;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.util.slotUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class armorMoveAccel extends etshmodifieriii {
    public armorMoveAccel(){
        MinecraftForge.EVENT_BUS.addListener(this::livingAttackEvent);
    }

    private void livingAttackEvent(LivingAttackEvent event) {
        Entity entity =event.getSource().getEntity();
        if (entity instanceof LivingEntity holder){
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
            if (modifierLv <=0){
                return;
            }
            int lv =0;
            if (holder.hasEffect(MobEffects.DIG_SPEED)){
                MobEffectInstance instance = holder.getEffect(MobEffects.DIG_SPEED);
                if (instance!=null){
                    lv+=instance.getAmplifier();
                }
            }
            holder.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED,100,Math.min( lv+1,4*modifierLv),false,false));
        }
    }
}
