package com.c2h6s.etshtinker.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import com.c2h6s.etshtinker.init.etshtinkerParticleType;

import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class novaExplosionUtil {
    public static void novaExplode(Level world,int power, float damage, double x, double y, double z,@NotNull Player creater){
        if (world!=null) {
            int i = 0;
            while (i <=Math.min( 10 * power,250)) {
                int power2 =Math.max(1, Math.min(power,60));
                if (world.isClientSide) {
                    world.addAlwaysVisibleParticle((SimpleParticleType) etshtinkerParticleType.nova.get(), x, y, z, 0.2 * EtSHrnd().nextInt(power2) - 0.1 * power2, 0.2 * EtSHrnd().nextInt(power2) - 0.1 * power2, 0.2 * EtSHrnd().nextInt(power2) - 0.1 * power2);
                }
                else {
                    ((ServerLevel)world).sendParticles(etshtinkerParticleType.nova.get(), x, y, z,1,0,0,0,0.2 * EtSHrnd().nextInt(power2) - 0.1 * power2 );
                }
                i++;
            }
            ((ServerLevel)world).sendParticles(ParticleTypes.EXPLOSION, x, y, z, 1,0,0, 0, 0);
            List<LivingEntity> ls = world.getEntitiesOfClass(LivingEntity.class, new AABB(x + power, y + power, z + power, x - power, y - power, z - power));
            for (LivingEntity entities : ls) {
                if (entities != null && !(entities instanceof Player)) {
                    entities.invulnerableTime = 0;
                    entities.hurt(DamageSource.playerAttack(creater), damage*0.1f*power);
                }
            }
        }
    }
}
