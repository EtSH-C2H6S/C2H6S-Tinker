package com.c2h6s.etshtinker.Entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

import static com.c2h6s.etshtinker.util.vecCalc.getScatteredVec3;
import static com.c2h6s.etshtinker.util.vecCalc.getUnitizedVec3;

public class CustomSonicBoomEntity extends ItemProjectile{
    public float damage =24;
    public int range =12;
    public List<LivingEntity> hitList =new ArrayList<>(List.of());
    public List<AABB> aabbList =new ArrayList<>(List.of());
    public Vec3 direction ;
    public int time =0;
    public boolean Scatter=true;

    public CustomSonicBoomEntity(EntityType<? extends ItemProjectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }
    @Override
    protected Item getDefaultItem() {
        return Items.AIR;
    }

    @Override
    public void tick() {
        if (this.level instanceof ServerLevel serverLevel) {
            if (this.time==0) {
                if (direction != null) {
                    Vec3 vec3 = getUnitizedVec3(direction);
                    if (vec3 != null) {
                        double x = this.getX();
                        double y = this.getY() + 0.5;
                        double z = this.getZ();
                        for (int i = 0; i < range; i++) {
                            AABB aabb = new AABB(x - 0.85, y - 0.85, z - 0.85, x + 0.85, y + 0.85, z + 0.85);
                            aabbList.add(aabb);
                            serverLevel.sendParticles(ParticleTypes.SONIC_BOOM,x,y-0.5,z,1,0,0,0,0);
                            if (this.Scatter) {
                                vec3 = getScatteredVec3(vec3, 2.5);
                            }
                            x+=vec3.x;
                            y+=vec3.y;
                            z+=vec3.z;
                        }
                    }
                }
                if (!aabbList.isEmpty()) {
                    for (AABB aabb : aabbList) {
                        if (aabb != null) {
                            List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class,aabb);
                            if (!list.isEmpty()) {
                                for (LivingEntity target : list) {
                                    if (target != null&&this.getOwner()!=null&&!hitList.contains(target)&&target!=this.getOwner()){
                                        target.invulnerableTime=0;
                                        target.hurt(DamageSource.sonicBoom(this.getOwner()),this.damage/2);
                                        hitList.add(target);
                                    }
                                }
                            }
                        }
                    }
                    hitList.clear();
                }
            }
            if (this.time==10){
                if (!aabbList.isEmpty()) {
                    for (AABB aabb : aabbList) {
                        if (aabb != null) {
                            List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class,aabb.inflate(1.5));
                            if (!list.isEmpty()) {
                                for (LivingEntity target : list) {
                                    if (target != null&&this.getOwner()!=null&&!hitList.contains(target)&&target!=this.getOwner()){
                                        target.invulnerableTime=0;
                                        target.hurt(DamageSource.sonicBoom(this.getOwner()),this.damage);
                                        hitList.add(target);
                                    }
                                }
                            }
                        }
                    }
                    hitList.clear();
                }
            }
        }
        time++;
        if (time>10){
            this.discard();
        }
    }
}
