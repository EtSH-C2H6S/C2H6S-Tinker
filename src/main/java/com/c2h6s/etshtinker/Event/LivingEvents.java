package com.c2h6s.etshtinker.Event;

import com.c2h6s.etshtinker.Entities.damageSources.playerThroughSource;
import com.c2h6s.etshtinker.Entities.damageSources.throughSources;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;
import static com.c2h6s.etshtinker.init.ItemReg.etshtinkerThermalMaterial.nights_alloy;

public class LivingEvents {
    public LivingEvents(){
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST,this::onPierceDamage);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST,this::onPierceAttack);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST,this::onPierceHurt);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST,this::onDeathPrevent);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST,this::onWardenHurt);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST,this::onWardenDeath);
    }

    private void onWardenDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Warden warden){
            if (!warden.getTags().contains("not_drop_special")&&warden.getLevel() instanceof ServerLevel level){
                ItemEntity alloy =new ItemEntity(EntityType.ITEM,level);
                alloy.setItem(new ItemStack(etshtinkerItems.energized_sculk_alloy.get(),EtSHrnd().nextInt(5)+4));
                alloy.setPos(warden.getX(),warden.getY(),warden.getZ());
                level.addFreshEntity(alloy);
            }
            if (warden.getLevel() instanceof ServerLevel level){
                if (EtSHrnd().nextInt(50)==0) {
                    ItemEntity alloy = new ItemEntity(EntityType.ITEM, level);
                    alloy.setItem(new ItemStack(etshtinkerItems.ionized_cannon_prototype.get(), 1));
                    alloy.setPos(warden.getX(), warden.getY(), warden.getZ());
                    level.addFreshEntity(alloy);
                }
            }
        }
    }

    private void onWardenHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Warden warden){
            if (!event.getSource().getMsgId().equals("sonic_boom")) {
                warden.addTag("not_drop_special");
            }
        }
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
