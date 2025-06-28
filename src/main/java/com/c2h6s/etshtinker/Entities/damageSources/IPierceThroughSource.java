package com.c2h6s.etshtinker.Entities.damageSources;

import com.hoshino.cti.util.ILivingEntityMixin;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public interface IPierceThroughSource {
    float getAmount();
    default void hurtEntity(LivingEntity living){
        ((ILivingEntityMixin) living).cti$strictHurt(this.getSource(),this.getAmount());
    }
    DamageSource getSource();
}
