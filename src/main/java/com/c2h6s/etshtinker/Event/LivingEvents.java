package com.c2h6s.etshtinker.Event;

import com.c2h6s.etshtinker.Entities.damageSources.playerThroughSource;
import com.c2h6s.etshtinker.Entities.damageSources.throughSources;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;

public class LivingEvents {
    public LivingEvents(){
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST,this::onPierceDamage);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST,this::onPierceAttack);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST,this::onPierceHurt);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST,this::onDeathPrevent);
    }

    private void onDeathPrevent(LivingDeathEvent event) {
        LivingEntity entity =event.getEntity();
        if (entity!=null){
            if (entity.getPersistentData().getInt("etshtinker.death_prevent")>0){
                entity.setHealth(entity.getMaxHealth());
                event.setCanceled(true);
                entity.deathTime=-2;
                entity.sendSystemMessage(Component.translatable("etshtinker.message.death_prevent").withStyle(ChatFormatting.AQUA));
                entity.getPersistentData().putInt("etshtinker.death_prevent",entity.getPersistentData().getInt("etshtinker.death_prevent")-1);
            }
        }
    }

    private void onPierceHurt(LivingHurtEvent event) {
        if (event.getSource() instanceof throughSources source){
            event.setAmount(source.getgetAmount());
            event.setCanceled(false);
        }
        if (event.getSource() instanceof playerThroughSource source){
            event.setAmount(source.getgetAmount());
            event.setCanceled(false);
        }
    }

    private void onPierceAttack(LivingAttackEvent event) {
        if (event.getSource() instanceof throughSources source){
            event.setCanceled(false);
        }
        if (event.getSource() instanceof playerThroughSource source){
            event.setCanceled(false);
        }
    }

    public void onPierceDamage(LivingDamageEvent event) {
        if (event.getSource() instanceof throughSources source){
            event.setAmount(source.getgetAmount());
            event.setCanceled(false);
        }
        if (event.getSource() instanceof playerThroughSource source){
            event.setAmount(source.getgetAmount());
            event.setCanceled(false);
        }
    }
}
