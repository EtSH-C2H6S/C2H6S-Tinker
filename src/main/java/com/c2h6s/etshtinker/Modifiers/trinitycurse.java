package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import com.c2h6s.etshtinker.util.slotUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class trinitycurse extends EtshModifieriii {
    public trinitycurse(){
        MinecraftForge.EVENT_BUS.addListener(this::LivingHurt);
    }

    private void LivingHurt(LivingHurtEvent event) {
        LivingEntity entity =event.getEntity();
        if (entity!=null) {
            for (EquipmentSlot slot : slotUtil.ALL) {
                ItemStack stack = entity.getItemBySlot(slot);
                if (stack.getItem() instanceof IModifiable){
                    ToolStack tool =ToolStack.from(stack);
                    if (tool.getModifierLevel(this)>0){
                        event.setAmount(event.getAmount()*1.5F);
                        return;
                    }
                }
            }
        }
    }

    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (modifier.getLevel()>0&&holder!=null&&isCorrectSlot&&!tool.hasTag(TinkerTags.Items.ARMOR)) {
            Vec3 v =holder.getDeltaMovement();
            if (v.y>0){
                holder.setDeltaMovement(v.x,v.y*0.75,v.z);
            }
            if (holder.invulnerableTime > 0) {
                holder.invulnerableTime-=modifier.getLevel();
            }
        }
    }

}
