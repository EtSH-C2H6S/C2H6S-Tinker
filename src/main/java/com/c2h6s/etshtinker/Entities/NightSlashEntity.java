package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import com.c2h6s.etshtinker.init.etshtinkerEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

import static com.c2h6s.etshtinker.util.vecCalc.getMold;


public class NightSlashEntity extends ItemProjectile{
    public int time =0;
    public float damage =0;
    public int hittimes =0;

    public NightSlashEntity(EntityType<? extends ItemProjectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public void tick() {
        Level world = this.level;
        Player player =this.getOwner() instanceof Player e ? e:null;
        time++;
        Vec3 movement =this.getDeltaMovement();
        this.setPos(movement.x+this.getX(),movement.y+this.getY(),movement.z+this.getZ());
        double angle =((this.tickCount * 100 % 360)*Math.PI)/180;
        Vec3 anglevec =new Vec3(Math.sin(angle),0,Math.cos(angle)).scale(2);
        if (getMold(movement)<=2){
            this.setDeltaMovement(movement.scale(1.15));
        }
        AABB aabb =new AABB(this.getX()+anglevec.x,this.getY(),this.getZ()+anglevec.z,this.getX(),this.getY(),this.getZ()).inflate(2.25);
        List<LivingEntity> ls = world.getEntitiesOfClass(LivingEntity.class,aabb);
        if (!ls.isEmpty()){
            for (LivingEntity targets:ls){
                if (targets!=null&&player!=null&&targets!=this.getOwner()){
                    targets.invulnerableTime =0;
                    targets.hurt(DamageSource.playerAttack(player),this.damage);
                    targets.forceAddEffect(new MobEffectInstance(etshtinkerEffects.cursefire.get(),100,0,false,false),this.getOwner());
                    hittimes++;
                }
            }
        }
        if (this.time>200){
            this.discard();
        }
        if (this.hittimes>15){
            this.discard();
        }
        super.tick();
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(this.DATA_ITEM_STACK, new ItemStack(etshtinkerItems.night_slash_a.get()));
    }
    @Override
    public ItemStack getItem() {
        return new ItemStack(etshtinkerItems.night_slash_a.get());
    }
}
