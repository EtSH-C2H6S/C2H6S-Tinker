package com.c2h6s.etshtinker.Entities.process;

import com.c2h6s.etshtinker.Entities.damageSources.IPierceThroughSource;
import com.c2h6s.etshtinker.Entities.damageSources.ThroughSources;
import com.c2h6s.etshtinker.init.etshtinkerEffects;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class LivingEntityTick {
    public static void onLivingTick(LivingEntity entity, ServerLevel level){
        if (entity.getPersistentData().contains("annih_countdown")&&entity.isAlive()){
            if (entity.isAlive()) {
                entity.getPersistentData().putInt("annih_countdown", entity.getPersistentData().getInt("annih_countdown") - 1);
                entity.playSound(SoundEvents.ITEM_BREAK, 1f + EtSHrnd().nextFloat()*2, 1 + EtSHrnd().nextFloat() * 2);
                entity.setHealth(EtSHrnd().nextFloat(entity.getMaxHealth()/10, entity.getMaxHealth()));
                level.sendParticles(etshtinkerParticleType.annihl.get(), entity.getX(), entity.getY(), entity.getZ(), 3, entity.getBbWidth()/2, 0.2, entity.getBbWidth()/2, 0.5);
                if (entity.getPersistentData().contains("atomic_dec")){
                    entity.getPersistentData().putInt("annih_countdown", entity.getPersistentData().getInt("annih_countdown") - 2);
                    if (entity.getPersistentData().contains("quark_disassemble")){
                        entity.getPersistentData().putInt("annih_countdown", 0);
                    }
                }
            }
            if (entity.getPersistentData().getInt("annih_countdown")<=0){
                entity.getPersistentData().remove("annih_countdown");
                ThroughSources.annihilate(Float.MAX_VALUE).hurtEntity(entity);
                level.sendParticles(etshtinkerParticleType.annihl_scatter.get(),entity.getX(),entity.getY()+0.5*entity.getBbHeight(),entity.getZ(),64,0.1,0.1,0.1,1);
                return;
            }
        }
        if (entity.getPersistentData().contains("max_health")&&entity.getHealth()>entity.getPersistentData().getFloat("max_health")&&!(entity instanceof Player)){
            entity.setHealth(entity.getPersistentData().getFloat("max_health"));
        }
        if (entity.getPersistentData().contains("atomic_dec")&&entity.isAlive()){
            int dura = entity.getPersistentData().getInt("atomic_dec");
            int amplifier =dura/20;
            entity.addEffect(new MobEffectInstance(etshtinkerEffects.atomic_dec.get(),0,amplifier,false,false));
            if (entity.level instanceof ServerLevel serverLevel){
                serverLevel.sendParticles(etshtinkerParticleType.atomic_dec.get(),entity.getX(),entity.getY()+0.5*entity.getBbHeight(),entity.getZ(),2,entity.getBbWidth()/2,entity.getBbHeight()/4,entity.getBbWidth()/2,0);
            }
            if ((entity.level.getGameTime()%5)<=amplifier) {
                float multiplier = Math.max(1,(float) amplifier/5);
                float life = Math.min(entity.getMaxHealth(),1000);
                DamageSource damageSource = switch (EtSHrnd().nextInt(4)){
                    case 1-> DamageSource.WITHER;
                    case 2-> DamageSource.MAGIC;
                    case 3-> DamageSource.FREEZE;
                    default -> ThroughSources.atomic(life * 0.00025f* multiplier);
                };
                if (damageSource instanceof IPierceThroughSource sources) sources.hurtEntity(entity);
                else {
                    entity.invulnerableTime = 0;
                    entity.hurt(damageSource, life * 0.00025f * multiplier);
                }
            }
            entity.getPersistentData().putInt("atomic_dec",dura-1);
            if (entity.getPersistentData().getInt("atomic_dec")<=0){
                entity.getPersistentData().remove("atomic_dec");
            }
        }
        if (entity.getPersistentData().contains("quark_disassemble")&&entity.isAlive()){
            int dura = entity.getPersistentData().getInt("quark_disassemble");
            int amplifier =dura/20;
            entity.addEffect(new MobEffectInstance(etshtinkerEffects.quark_disassemble.get(),0,amplifier,false,false));
            if (entity.level instanceof ServerLevel serverLevel){
                serverLevel.sendParticles(etshtinkerParticleType.quark_disassemble.get(),entity.getX(),entity.getY()+0.5*entity.getBbHeight(),entity.getZ(),4,0,0,0,0.3);
            }
            if ((entity.level.getGameTime()%5)<=amplifier) {
                float b = Math.max(1,(float) amplifier/5);
                entity.invulnerableTime=0;
                ThroughSources.quark(entity.getMaxHealth() * 0.005f*b).hurtEntity(entity);
            }
            entity.getPersistentData().putInt("quark_disassemble",dura-1);
            if (entity.getPersistentData().getInt("quark_disassemble")<=0){
                entity.getPersistentData().remove("quark_disassemble");
            }
        }
    }
}
