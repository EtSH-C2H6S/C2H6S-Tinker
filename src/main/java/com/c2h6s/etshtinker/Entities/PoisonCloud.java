package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.init.etshtinkerEffects;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class PoisonCloud extends ItemProjectile{
    public float damage=1f;
    public int lvl =0;
    public int time=0;
    public PoisonCloud(EntityType<? extends ItemProjectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.AIR;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        this.time++;
        if (this.level instanceof ServerLevel serverLevel) {
            if (((float)this.time)%5==0) {
                AABB aabb = new AABB(this.getX() - 4, this.getY() - 1, this.getZ() - 4, this.getX() + 4, this.getY() + 1, this.getZ() + 4);
                List<Mob> list = serverLevel.getEntitiesOfClass(Mob.class, aabb);
                for (Mob mob : list) {
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageSource.indirectMagic(this, this.getOwner()), this.damage);
                    mob.addEffect(new MobEffectInstance(etshtinkerEffects.strong_poison.get(), 100, this.lvl, false, false));
                }
            }
            serverLevel.sendParticles(etshtinkerParticleType.strong_poison.get(),this.getX(),this.getY(),this.getZ(),3,4,1,4,0);
        }
        if (this.time>30){
            this.discard();
        }
    }
}
