package com.c2h6s.etshtinker.Effects;

import com.c2h6s.etshtinker.util.MathUtil;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class hiGravity extends etsheffect {
    public hiGravity() {
        super(MobEffectCategory.HARMFUL, 0x8820FF);
        super.addAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), MathUtil.getUUIDFromString("etshtinker:hi_gravity").toString(),10, AttributeModifier.Operation.MULTIPLY_BASE);
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        super.applyEffectTick(living, amplifier);
        if (living.getDeltaMovement().length()>0){
            living.setDeltaMovement(living.getDeltaMovement().scale(1d/(1+0.5*amplifier)));
        }
    }
}
