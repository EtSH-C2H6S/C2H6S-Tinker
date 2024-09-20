package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.item.ranged.ModifiableLauncherItem;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;


public class perfectism extends etshmodifieriii {
    public perfectism(){
        MinecraftForge.EVENT_BUS.addListener(this::AttackEvent);
        MinecraftForge.EVENT_BUS.addListener(this::DamageEvent);
    }

    private void AttackEvent(LivingAttackEvent event) {
        if (event.getSource().getEntity() instanceof Player player&&event.getEntity() !=null){
            LivingEntity target =event.getEntity();
            InteractionHand hand =player.getUsedItemHand();
            if (player.getItemInHand(hand).getItem() instanceof ModifiableItem||player.getItemInHand(hand).getItem() instanceof ModifiableLauncherItem){
                ToolStack tool = ToolStack.from(player.getItemInHand(hand));
                if (tool.getModifierLevel(this)>0&&target!=null){
                    CompoundTag nbt =target.getPersistentData();
                    nbt.putFloat("etsh.perfect_damage",event.getAmount()*tool.getModifierLevel(this));
                }
            }
        }
    }
    private void DamageEvent(LivingDamageEvent event) {
        LivingEntity entity =event.getEntity();
        CompoundTag nbt =entity.getPersistentData();
        if (nbt.contains("etsh.perfect_damage")){
            float amount =nbt.getFloat("etsh.perfect_damage");
            nbt.remove("etsh.perfect_damage");
            if (amount<50){
                event.setAmount(10);
            }else {
                int dmgBit =(int) Math.log10(amount);
                int a =(int)( amount/Math.pow(10,dmgBit));
                if (a<=4){
                    event.setAmount((float) Math.pow(10,dmgBit));
                }else {
                    event.setAmount((float) Math.pow(10,dmgBit+1));
                    if (a>=8){
                        event.setAmount((float) Math.pow(10,dmgBit+2));
                    }
                }
            }
        }
    }
}
