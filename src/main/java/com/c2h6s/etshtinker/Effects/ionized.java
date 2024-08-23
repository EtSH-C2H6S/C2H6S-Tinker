package com.c2h6s.etshtinker.Effects;

import com.c2h6s.etshtinker.init.etshtinkerEffects;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.security.SecureRandom;
import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class ionized extends etsheffect {
    public ionized() {
        super(MobEffectCategory.NEUTRAL, 0x8820FF);
    }
    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        SecureRandom RANDOM =EtSHrnd();
        Level world =living.level;
        if (world!=null) {
            if (RANDOM.nextInt(2) == 1) {
                if (world.isClientSide) {
                    world.addParticle(etshtinkerParticleType.electric.get(), living.getX(), living.getY() + 0.5 * living.getBbHeight(), living.getZ(), RANDOM.nextDouble() * 0.7 - 0.35, RANDOM.nextDouble() * 0.3 - 0.15, RANDOM.nextDouble() * 0.7 - 0.35);
                }
                else {
                    ((ServerLevel)world).sendParticles(etshtinkerParticleType.electric.get(), living.getX(), living.getY() + 0.5 * living.getBbHeight(), living.getZ(), 1,1, 1, 1,RANDOM.nextDouble() * 0.7 - 0.35);
                }
            }
            if (RANDOM.nextInt(20) <= Math.min(4, amplifier + 1)) {
                living.invulnerableTime = 0;
                living.hurt(DamageSource.MAGIC.bypassMagic().bypassArmor(), living.getHealth() * 0.01f * (amplifier + 1));
                living.invulnerableTime = 0;
            }
            double x = living.getX();
            double y =living.getY()+0.5*living.getBbHeight();
            double z =living.getZ();
            if (amplifier>0) {
                List<Mob> ls = world.getEntitiesOfClass(Mob.class, new AABB(x - 1.5, y - 1.5, z - 1.5, x + 1.5, y + 1.5, z + 1.5));
                for (Mob entity : ls) {
                    if (entity != null) {
                        entity.addEffect(new MobEffectInstance(etshtinkerEffects.ionized.get(),60,amplifier-1,false,false));
                    }
                }
            }
        }

    }
}
