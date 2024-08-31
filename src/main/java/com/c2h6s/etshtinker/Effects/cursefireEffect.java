package com.c2h6s.etshtinker.Effects;

import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.security.SecureRandom;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class cursefireEffect extends etsheffect{
    public cursefireEffect() {
        super(MobEffectCategory.NEUTRAL, 0x20FF00);
    }
    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        SecureRandom RANDOM =EtSHrnd();
        Level world =living.level;
        living.invulnerableTime =0;
        living.hurt(DamageSource.DRAGON_BREATH,amplifier+1);
        if (world!=null) {
            if (world.isClientSide) {
                world.addParticle(etshtinkerParticleType.curse.get(), living.getX(), living.getY() + 0.5 * living.getBbHeight(), living.getZ(), RANDOM.nextDouble() * 0.7 - 0.35, RANDOM.nextDouble() * 0.9, RANDOM.nextDouble() * 0.7 - 0.35);
            } else {
                ((ServerLevel) world).sendParticles(etshtinkerParticleType.curse.get(), living.getX(), living.getY() + 0.5 * living.getBbHeight(), living.getZ(), 4, 0.3, 0.3, 0.3, RANDOM.nextDouble() * 0.7 - 0.35);
            }

        }

    }
}
