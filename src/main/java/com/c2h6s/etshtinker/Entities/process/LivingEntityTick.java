package com.c2h6s.etshtinker.Entities.process;

import com.c2h6s.etshtinker.Entities.damageSources.throughSources;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class LivingEntityTick {
    public static void onLivingTick(LivingEntity entity, ServerLevel level){
        if (entity.getPersistentData().contains("annih_countdown")&&entity.isAlive()){
            if (entity.getPersistentData().getInt("annih_countdown")<=0){
                entity.hurt(throughSources.annihilate(Float.MAX_VALUE),Float.MAX_VALUE);
                entity.hurt(throughSources.annihilate(Float.MAX_VALUE).bypassMagic(),Float.MAX_VALUE);
                entity.hurt(throughSources.annihilate(Float.MAX_VALUE).bypassArmor(),Float.MAX_VALUE);
                entity.hurt(throughSources.annihilate(Float.MAX_VALUE).bypassInvul(),Float.MAX_VALUE);
                level.sendParticles(etshtinkerParticleType.annihl.get(),entity.getX(),entity.getY()+0.5*entity.getBbHeight(),entity.getZ(),64,0.1,0.1,0.1,1);
                return;
            }
            if (entity.isAlive()) {
                entity.getPersistentData().putInt("annih_countdown", entity.getPersistentData().getInt("annih_countdown") - 1);
                entity.playSound(SoundEvents.ITEM_BREAK, 1f + EtSHrnd().nextFloat()*2, 1 + EtSHrnd().nextFloat() * 2);
                entity.setHealth(EtSHrnd().nextFloat(0.5f, Math.max(1, entity.getMaxHealth())));
                level.sendParticles(etshtinkerParticleType.annihl.get(), entity.getX(), entity.getY()+0.5*entity.getBbHeight(), entity.getZ(), 32, entity.getBbWidth()/2, entity.getBbHeight()/2, entity.getBbWidth()/2, 0);
            }
        }
    }
}
