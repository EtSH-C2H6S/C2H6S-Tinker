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

public class plasmarrowentity extends AbstractArrow {
    public int time =0;
    protected plasmarrowentity(EntityType<? extends AbstractArrow> p_36717_, LivingEntity p_36718_, Level p_36719_) {
        super(p_36717_, p_36718_, p_36719_);
    }

    protected plasmarrowentity(EntityType<? extends AbstractArrow> p_36711_, double p_36712_, double p_36713_, double p_36714_, Level p_36715_) {
        super(p_36711_, p_36712_, p_36713_, p_36714_, p_36715_);
    }

    public plasmarrowentity(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }
    public plasmarrowentity(Level world, LivingEntity livingEntity){
        super(etshtinkerEntity.plasmarrowEntity.get(),livingEntity,world);

    }
    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public void tick() {
        time++;
        if (this.isCritArrow()) {
            this.setCritArrow(false);
        }
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
        if (e instanceof LivingEntity entity) {
            if (this.getOwner() instanceof Player player) {
                entity.invulnerableTime = 0;
                entity.hurt(DamageSource.playerAttack(player), (float) (4 * getMold(this.getDeltaMovement())));
                entity.setSecondsOnFire(65535);
                entity.forceAddEffect(new MobEffectInstance(etshtinkerEffects.ionized.get(),1000,1,false,false),player);
                entity.invulnerableTime = 0;
            }
        }
        super.onHitEntity(result);
    }
    protected void onHitBlock(BlockHitResult result) {
        Level world =this.getLevel();
        Vec3 vec3 = this.getDeltaMovement();
        world.createFireworks(this.getX(), this.getY(), this.getZ(), vec3.x, vec3.y, vec3.z,null);
        this.discard();
        super.onHitBlock(result);
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }
}
