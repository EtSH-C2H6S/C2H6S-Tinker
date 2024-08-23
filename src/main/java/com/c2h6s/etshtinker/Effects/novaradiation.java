package com.c2h6s.etshtinker.Effects;

import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.security.SecureRandom;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;


public class novaradiation extends etsheffect {
    public novaradiation() {
        super(MobEffectCategory.NEUTRAL, 0x8820FF);
    }
    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        SecureRandom RANDOM =EtSHrnd();
        Level world =living.level;
        if (world!=null) {
            if (RANDOM.nextInt(2) == 1) {
                if (world.isClientSide) {
                    world.addParticle(etshtinkerParticleType.nova.get(), living.getX(), living.getY() + 0.5 * living.getBbHeight(), living.getZ(), RANDOM.nextDouble() * 0.6 - 0.3, RANDOM.nextDouble() * 0.2 - 0.1, RANDOM.nextDouble() * 0.6 - 0.3);
                }
                else {
                    ((ServerLevel)world).sendParticles(etshtinkerParticleType.nova.get(), living.getX(), living.getY() + 0.5 * living.getBbHeight(), living.getZ(),1,0, 0, 0, RANDOM.nextDouble() * 0.6 - 0.3);
                }
            }
            if (RANDOM.nextInt(80) <= Math.min(4, amplifier)) {
                living.invulnerableTime = 0;
                living.hurt(DamageSource.MAGIC.bypassMagic().bypassArmor(), 64 * amplifier + 1);
                living.invulnerableTime = 0;
            }
            if (RANDOM.nextInt(80) <= Math.min(4, amplifier)) {
                living.invulnerableTime = 0;
                living.hurt(DamageSource.STALAGMITE.bypassMagic().bypassArmor(), 64 * amplifier);
                living.invulnerableTime = 0;
            }
            if (RANDOM.nextInt(80) <= Math.min(4, amplifier)) {
                living.invulnerableTime = 0;
                living.hurt(DamageSource.LAVA.bypassMagic().bypassArmor(), 64 * amplifier);
                living.invulnerableTime = 0;
            }
            if (RANDOM.nextInt(80) <= Math.min(4, amplifier)) {
                living.invulnerableTime = 0;
                living.hurt(DamageSource.WITHER.bypassMagic().bypassArmor(), 64 * amplifier);
                living.invulnerableTime = 0;
            }
        }
    }
}
