package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import com.mojang.math.Vector3f;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;
import static com.c2h6s.etshtinker.util.vecCalc.*;

public class SculkSwordEntity extends ItemProjectile{
    public Vec3 Rawdirection =new Vec3(0,0,0);
    public Vec3 offset;
    public float damage =24;
    public int charge =40;

    public SculkSwordEntity(EntityType<? extends ItemProjectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 direction =new Vec3(new Vector3f(Rawdirection));
        float factor1 = direction.z<0 ? -1:1;
        if (this.tickCount==1){
            direction =getUnitizedVec3(new Vec3( direction.x/2,1.5,factor1*direction.y/2));
            if (direction!=null){
                direction.scale(1.5);
            }
        }
        else if (this.tickCount==2){
            direction =getUnitizedVec3(new Vec3( -direction.x/2,1,-factor1*direction.y/2));
            if (direction!=null){
                direction.scale(1.5);
            }
        }
        else if (this.tickCount==3){
            direction =getUnitizedVec3(new Vec3( -direction.x,0.75,-factor1*direction.y));
            if (direction!=null){
                direction.scale(1.5);
            }
        }
        else if (this.tickCount==4){
            direction =getUnitizedVec3(new Vec3( -direction.x,0.0,-factor1*direction.y));
            if (direction!=null){
                direction.scale(1.5);
            }
        }
        else if (this.tickCount==5){
            direction =getUnitizedVec3(new Vec3( -direction.x,-0.5,-factor1*direction.y));
            if (direction!=null){
                direction.scale(1.5);
            }
        }
        else if (this.tickCount==6){
            direction =getUnitizedVec3(new Vec3( -direction.x,-1,-factor1*direction.y));
            if (direction!=null){
                direction.scale(1.5);
            }
        }
        if (this.getOwner() instanceof ServerPlayer player&&offset!=null&&direction!=null){
            ServerLevel serverLevel =player.getLevel();
            this.setPos(player.getX()+offset.x+direction.x,player.getEyeY()+offset.y+direction.y,player.getZ()+offset.z+direction.z);
        }
        Vec3 position = new Vec3(this.getX(),this.getY(),this.getZ());
        if (direction!=null&&this.getOwner() instanceof ServerPlayer player) {
            for (int i = 0; i < 8; i++) {
                ServerLevel serverLevel =player.getLevel();
                double x = direction.x * i+ position.x;
                double y = direction.y * i + position.y;
                double z = direction.z * i + position.z;
            serverLevel.sendParticles(etshtinkerParticleType.sonic_energy.get(), x, y, z, 1, 0, 0, 0, 0);
            }
        }
        if (this.tickCount>5){
            if (this.tickCount == 6 && offset != null && this.getOwner() instanceof ServerPlayer player){
                ServerLevel serverLevel =player.getLevel();
                for (int i=0;i<8;i++){
                    Vec3 position2 = new Vec3(player.getX()+offset.x+direction.x,player.getEyeY()+offset.y+direction.y,player.getZ()+offset.z+direction.z);
                    double x =direction.x*i + position2.x+0.1*EtSHrnd().nextFloat() -0.05;
                    double y =direction.y*i + position2.y+0.1*EtSHrnd().nextFloat() -0.05;
                    double z =direction.z*i + position2.z+0.1*EtSHrnd().nextFloat() -0.05;
                    double range = i%2==0?-16:16;
                    if (charge>=20){
                        ExperienceOrb orb =new ExperienceOrb(EntityType.EXPERIENCE_ORB,level);
                        orb.setPos(x,y,z);
                        orb.value=15;
                        level.addFreshEntity(orb);
                        this.charge-=20;
                    }else {
                        ExperienceOrb orb =new ExperienceOrb(EntityType.EXPERIENCE_ORB,level);
                        orb.setPos(x,y,z);
                        orb.value=(charge*3)/4;
                        level.addFreshEntity(orb);
                        this.charge=0;
                    }
                    this.playSound(SoundEvents.WARDEN_SONIC_BOOM,1,1);
                    CustomSonicBoomEntity entity =new CustomSonicBoomEntity(etshtinkerEntity.sonic_boom.get(),level);
                    entity.setOwner(player);
                    entity.direction=getScatteredVec3( new Vec3(0,range,0),87);
                    entity.damage=this.damage;
                    entity.setPos(x,y,z);
                    level.addFreshEntity(entity);
                }
                this.discard();
            }
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return Items.AIR;
    }
}
