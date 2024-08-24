//package com.c2h6s.etshtinker.Modifiers;
//
//import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.damagesource.DamageSource;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.entity.living.LivingAttackEvent;
//import net.minecraftforge.event.entity.living.LivingHurtEvent;
//import net.minecraftforge.event.entity.player.CriticalHitEvent;
//import net.minecraftforge.eventbus.api.Event;
//import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
//import slimeknights.tconstruct.library.tools.item.ModifiableItem;
//import slimeknights.tconstruct.library.tools.nbt.ToolStack;
//
//public class ultraviolet extends etshmodifieriii {
//    public ultraviolet(){
//        //MinecraftForge.EVENT_BUS.addListener(this::CriticalHitEvent);
//        MinecraftForge.EVENT_BUS.addListener(this::livingattackevent);
//    }
//
//    private void livingattackevent(LivingAttackEvent event) {
//        LivingEntity entity =event.getEntity();
//        Entity entity1 =event.getSource().getEntity();
//        if (entity!=null&&entity1 instanceof Player player){
//            InteractionHand hand = player.getUsedItemHand();
//            ItemStack stack =player.getItemInHand(hand);
//            if (stack.getItem() instanceof ModifiableItem){
//                ToolStack tool = ToolStack.from(stack);
//                if (tool.getModifierLevel(this)>0){
//                    CriticalHitEvent event1 =new CriticalHitEvent(player,entity,1.5f,true);
//                    event1.setResult(Event.Result.ALLOW);
//                    MinecraftForge.EVENT_BUS.post(event1);
//                }
//            }
//        }
//    }
//
///*
//    private void CriticalHitEvent(CriticalHitEvent event) {
//        if (event.getEntity()!=null){
//            InteractionHand hand = event.getEntity().getUsedItemHand();
//            ItemStack stack =event.getEntity().getItemInHand(hand);
//            if (stack.getItem() instanceof ModifiableItem){
//                ToolStack tool = ToolStack.from(stack);
//                if (tool.getModifierLevel(this)>0){
//                    event.setResult(Event.Result.ALLOW);
//                    event.setDamageModifier(event.getDamageModifier()+0.25f*tool.getModifierLevel(this));
//                }
//            }
//        }
//    }
//
// */
//}
