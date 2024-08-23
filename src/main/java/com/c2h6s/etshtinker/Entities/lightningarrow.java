package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.etshtinkerEffects;


import static com.c2h6s.etshtinker.util.vecCalc.*;

import java.util.ArrayList;
import java.util.List;

public class lightningarrow extends AbstractArrow {
    public int time=0;
    public boolean tracking=false;
    public List<LivingEntity> ls=new ArrayList<>(List.of());

    public lightningarrow(EntityType<? extends AbstractArrow>type, Level level) {
        super(type, level);
        this.setBaseDamage(1028);
        this.pickup =Pickup.CREATIVE_ONLY;
    }
    public lightningarrow(Level world, LivingEntity livingEntity){
        super(etshtinkerEntity.lightningarrow.get(),livingEntity,world);

    }
    public @NotNull ItemStack getPickupItem() {
        return new ItemStack(Items.ARROW,1);
    }
    public void tick(){
        time++;
        if (!this.isNoGravity()){
            this.setNoGravity(true);
        }
        if (getMold(this.getDeltaMovement())>8&&getUnitizedVec3(this.getDeltaMovement())!=null){
            this.setDeltaMovement(getUnitizedVec3(this.getDeltaMovement()).scale(8));
        }
        if (time>600){
            this.remove(RemovalReason.KILLED);
        }
        if (!tracking) {
            if (getNearestLiEntWithBL(8f, this, this.level, ls) != null){
                tracking =true;
            }
        }
        if(tracking){
            this.level.addAlwaysVisibleParticle((SimpleParticleType)etshtinkerParticleType.nova.get(),true,this.getX(),this.getY(),this.getZ(),0d,0d,0d);
            LivingEntity entity =getNearestLiEntWithBL(128f, this, this.level, ls);
            if (entity!=null){
                Vec3 vec3 =getUnitizedVec3(Entity1ToEntity2(this,entity));
                if (vec3!=null){
                    this.setDeltaMovement(vec3.scale(1.5));
                }
                if (this.inGround){
                    this.inGround=false;
                    this.setOnGround(false);
                    this.setPos(entity.getX(),entity.getY()+0.5*entity.getBbHeight(),entity.getZ());
                }
            }
            else tracking=false;
        }
        super.tick();
    }
    public void onHitEntity(EntityHitResult result) {
        if (!tracking){
            tracking=true;
        }
        this.setPierceLevel((byte)(1+ this.getPierceLevel()));
        LivingEntity entity =(LivingEntity) result.getEntity();
        if (this.getOwner() instanceof Player player) {
            entity.invulnerableTime=0;
            entity.hurt(DamageSource.playerAttack(player), (float)(this.getBaseDamage() * 50));
            entity.forceAddEffect(new MobEffectInstance(etshtinkerEffects.novaradiation.get(),100,1,false,false),player);
        }
        ls.add(entity);
        super.onHitEntity(result);
    }

}
