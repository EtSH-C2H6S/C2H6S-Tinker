package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

import static com.c2h6s.etshtinker.util.vecCalc.getMold;

public class ShockWaveEntity extends ItemProjectile{
    public List<LivingEntity> hitlist=new ArrayList<>(List.of());
    public float damage =12;
    public int time =0;
    public ShockWaveEntity(EntityType<? extends ItemProjectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }

    @Override
    protected Item getDefaultItem() {
        return etshtinkerItems.shock_wave.get();
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 vec3 =this.getDeltaMovement();
        this.time++;
        this.damage /=this.time;
        this.setDeltaMovement(vec3.scale(1.5));
        double mold =getMold(vec3);
        AABB aabb =new AABB(this.getX()-mold,this.getY()-1,this.getZ()-mold,this.getX()+mold,this.getY()+1,this.getZ()+mold);
        List<LivingEntity> ls =this.level.getEntitiesOfClass(LivingEntity.class,aabb);
        for (LivingEntity living : ls) {
            if (living != null && !hitlist.contains(living) && this.getOwner() instanceof LivingEntity attacker&&living!=attacker) {
                living.invulnerableTime = 0;
                living.hurt(DamageSource.explosion(attacker), damage);
                hitlist.add(living);
            }
        }
        if (this.time>=4){
            this.discard();
        }
    }
}
