package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.init.etshtinkerEffects;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import static com.c2h6s.etshtinker.util.vecCalc.getMold;

public class PlasmaArrowEntity extends AbstractArrow {
    public int time =0;

    public PlasmaArrowEntity(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
        this.setBaseDamage(4);
    }
    public PlasmaArrowEntity(Level world, LivingEntity livingEntity){
        super(etshtinkerEntity.plasmarrowEntity.get(),livingEntity,world);
        this.setBaseDamage(4);
    }
    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public void tick() {
        time++;
        if (time>=1200){
            this.remove(RemovalReason.DISCARDED);
        }
        if (getMold(this.getDeltaMovement())<5) {
            this.setDeltaMovement(this.getDeltaMovement().scale(1.05));
        }
        if (this.inGround){
            this.discard();
        }
        this.level.addParticle(etshtinkerParticleType.electric.get(),this.getX(),this.getY(),this.getZ(),0,0,0);
        super.tick();
    }
    public void onHitEntity(EntityHitResult result) {
        this.setPierceLevel((byte)(1+ this.getPierceLevel()));
        Entity e =result.getEntity();
        Vec3 movement = this.getDeltaMovement();
        if (e instanceof LivingEntity entity) {
            if (this.getOwner() instanceof Player player&&!(entity instanceof Player)) {
                entity.invulnerableTime = 0;
                entity.hurt(DamageSource.thrown(this,player), (float) (getMold(this.getDeltaMovement())*this.getBaseDamage()));
                entity.setSecondsOnFire(65535);
                entity.forceAddEffect(new MobEffectInstance(etshtinkerEffects.ionized.get(),1000,1,false,false),player);
                entity.invulnerableTime = 0;
            }
        }
        super.onHitEntity(result);
        this.setDeltaMovement(movement);
    }
    protected void onHitBlock(BlockHitResult result) {
        this.discard();
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }
}
