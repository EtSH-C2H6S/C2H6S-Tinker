package com.c2h6s.etshtinker.Effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public abstract class etsheffect extends MobEffect {
    public etsheffect(MobEffectCategory type, int color) {
        super(type, color);
    }
    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
    }
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
