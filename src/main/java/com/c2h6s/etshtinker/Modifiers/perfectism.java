package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.damageSources.playerThroughSource;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class perfectism extends etshmodifieriii {
    public perfectism(){
        MinecraftForge.EVENT_BUS.addListener(this::AttackEvent);
    }

    private void AttackEvent(LivingAttackEvent event) {
        if (event.getSource().getEntity() instanceof Player player&&event.getEntity() !=null){
            player.sendSystemMessage(Component.translatable("1"+String.valueOf(event.amount)));
            LivingEntity target =event.getEntity();
            InteractionHand hand =player.getUsedItemHand();
            if (player.getItemInHand(hand).getItem() instanceof ModifiableItem){
                ToolStack tool = ToolStack.from(player.getItemInHand(hand));
                if (tool.getModifierLevel(this)>0){
                    float am =event.amount * tool.getModifierLevel(this);
                    int dmgBit =(int) Math.log10(am);
                    if (dmgBit <=0){
                        event.setCanceled(true);
                        event.source = playerThroughSource.PlayerPierce(player,10f);
                        event.amount=10;
                        player.sendSystemMessage(Component.translatable("2"+String.valueOf(event.amount)));
                    }
                    else {
                        int a =(int) (am/Math.pow(10,dmgBit));
                        if (a<5){
                            event.amount=(float) Math.max(10,Math.pow(10,dmgBit-1));
                            player.sendSystemMessage(Component.translatable("3"+String.valueOf(event.amount)));
                            event.source = playerThroughSource.PlayerPierce(player,event.amount);
                        }
                        else {
                            event.amount =(float) Math.pow(10,dmgBit+1);
                            if (a>=9){
                                event.amount*=10;
                            }
                            event.source = playerThroughSource.PlayerPierce(player,event.amount);
                            player.sendSystemMessage(Component.translatable("4"+String.valueOf(event.amount)));
                        }
                    }
                }
            }
        }
    }
}
