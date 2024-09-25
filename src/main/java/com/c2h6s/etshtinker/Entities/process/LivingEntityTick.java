package com.c2h6s.etshtinker.Entities.process;

import com.c2h6s.etshtinker.Entities.damageSources.throughSources;
import com.c2h6s.etshtinker.init.etshtinkerEffects;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

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
                entity.invulnerableTime=0;
                entity.hurt(throughSources.annihilate(Float.MAX_VALUE),Float.MAX_VALUE);
                entity.invulnerableTime=0;
                entity.hurt(throughSources.annihilate(Float.MAX_VALUE).bypassMagic(),Float.MAX_VALUE);
                entity.invulnerableTime=0;
                entity.hurt(throughSources.annihilate(Float.MAX_VALUE).bypassArmor(),Float.MAX_VALUE);
                entity.invulnerableTime=0;
                entity.hurt(throughSources.annihilate(Float.MAX_VALUE).bypassInvul(),Float.MAX_VALUE);
                entity.setHealth(0);
                entity.getPersistentData().remove("annih_countdown");
                level.sendParticles(etshtinkerParticleType.annihl_scatter.get(),entity.getX(),entity.getY()+0.5*entity.getBbHeight(),entity.getZ(),64,0.1,0.1,0.1,1);
                return;
            }
        }
        if (entity.getPersistentData().contains("atomic_dec")&&entity.isAlive()){
            int dura = entity.getPersistentData().getInt("atomic_dec");
            int amplifier =dura/20;
            entity.addEffect(new MobEffectInstance(etshtinkerEffects.atomic_dec.get(),0,amplifier,false,false));
            if (entity.level instanceof ServerLevel serverLevel){
                serverLevel.sendParticles(etshtinkerParticleType.atomic_dec.get(),entity.getX(),entity.getY()+0.5*entity.getBbHeight(),entity.getZ(),2,entity.getBbWidth()/2,entity.getBbHeight()/4,entity.getBbWidth()/2,0);
            }
            if ((entity.level.getGameTime()%5)<=amplifier) {
                float b = Math.max(1,(float) amplifier/5);
                entity.invulnerableTime=0;
                entity.hurt(throughSources.atomic(entity.getMaxHealth() * 0.0005f*b), entity.getMaxHealth() * 0.0005f*b);
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
                entity.hurt(throughSources.atomic(entity.getMaxHealth() * 0.005f*b), entity.getMaxHealth() * 0.005f*b);
            }
            entity.getPersistentData().putInt("quark_disassemble",dura-1);
            if (entity.getPersistentData().getInt("quark_disassemble")<=0){
                entity.getPersistentData().remove("quark_disassemble");
            }
        }
    }
}
