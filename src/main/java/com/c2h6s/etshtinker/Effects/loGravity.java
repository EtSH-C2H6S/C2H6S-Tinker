package com.c2h6s.etshtinker.Effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

public class loGravity extends etsheffect {
    public loGravity() {
        super(MobEffectCategory.HARMFUL, 0x8820FF);
        super.addAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), "A5B9CF2A-2F9C-31EF-9052-7C3E7D5E6ABA",-0.9, AttributeModifier.Operation.MULTIPLY_BASE);
    }
}
