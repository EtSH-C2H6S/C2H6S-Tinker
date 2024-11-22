package com.c2h6s.etshtinker.Effects;

import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class AcidicPoison extends etsheffect{
    public AcidicPoison() {
        super(MobEffectCategory.HARMFUL,0x188C17);
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        if (living.invulnerableTime>0&&!(living instanceof Player)){
            living.invulnerableTime=Math.max(0,living.invulnerableTime-amplifier);
        }
        if (living.level.getGameTime()%5==0) {
            living.hurt(DamageSource.MAGIC, amplifier);
        }
        if (living.level instanceof ServerLevel serverLevel){
            serverLevel.sendParticles(etshtinkerParticleType.strong_poison.get(),living.getX(),living.getY()+living.getBbHeight()*0.5,living.getZ(),3,living.getBbWidth()*0.5,living.getBbHeight()*0.5,living.getBbWidth()*0.5,0);
        }
    }
}
