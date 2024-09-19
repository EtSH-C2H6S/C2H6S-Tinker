package com.c2h6s.etshtinker.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.security.SecureRandom;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class vecCalc {
    public static Vec3 Entity1ToEntity2(Entity entity1,Entity entity2){
        if(entity1!=null&&entity2!=null){
            double x = entity2.getX()-entity1.getX();
            double y = entity2.getY()+0.5*entity2.getBbHeight()-entity1.getY();
            double z = entity2.getZ()-entity1.getZ();
            return new Vec3(x,y,z);
        }
        return null;
    }
    public static Vec3 Entity1ToEntity2Eye(Entity entity1,Entity entity2){
        if(entity1!=null&&entity2!=null){
            double x = entity2.getX()-entity1.getX();
            double y = entity2.getEyeY()-entity1.getY();
            double z = entity2.getZ()-entity1.getZ();
            return new Vec3(x,y,z);
        }
        return null;
    }
    public static Vec3 EntityToPos(Entity entity1,double x,double y,double z){
        if(entity1!=null){
            double vx = x-entity1.getX();
            double vy = y-entity1.getY();
            double vz = z-entity1.getZ();
            return new Vec3(vx,vy,vz);
        }
        return null;
    }
    public static double getMold(Vec3 vec3){
        if(vec3!=null){
            return Math.pow(Math.pow(vec3.x, 2)+Math.pow(vec3.y, 2)+Math.pow(vec3.z, 2),0.5);
        }
        return 0;
    }
    public static Vec3 getUnitizedVec3(Vec3 vec3){
        if(vecCalc.getMold(vec3)!=0){
            double i = vecCalc.getMold(vec3);
            return new Vec3(vec3.x/i, vec3.y/i, vec3.z/i);
        }
        return null;
    }
    public static double vec3Multiplyvec3(Vec3 vec1,Vec3 vec2){
        if (vec1!=null&&vec2!=null){
            return (vec1.x*vec2.x +vec1.y*vec2.y+vec1.z*vec2.z);
        }
        return 0;
    }
    public static LivingEntity getNearestLiEnt(Float radius, Entity centerEnt, Level level){
        double x =centerEnt.getX();
        double y =centerEnt.getY();
        double z =centerEnt.getZ();
        LivingEntity entity = null;
        List<LivingEntity> mobbb = level.getEntitiesOfClass(LivingEntity.class, new AABB(x + radius, y + radius, z + radius, x - radius, y - radius, z - radius));
        double i =radius;
        for (LivingEntity targets : mobbb){
            if (targets!=null&&targets!=centerEnt&&getMold(Entity1ToEntity2(centerEnt,targets))<i&&!(targets instanceof Player)&&!targets.isDeadOrDying()){
                i =getMold(Entity1ToEntity2(centerEnt,targets));
                entity =targets;
            }
        }
        return entity;
    }
    public static ItemEntity getNearestItemEntWithItem(Float radius, Entity centerEnt, Level level, Item item){
        double x =centerEnt.getX();
        double y =centerEnt.getY();
        double z =centerEnt.getZ();
        ItemEntity entity = null;
        List<ItemEntity> itemEntities = level.getEntitiesOfClass(ItemEntity.class, new AABB(x + radius, y + radius, z + radius, x - radius, y - radius, z - radius));
        double i =radius;
        for (ItemEntity items : itemEntities){
            if (items !=null&& items !=centerEnt&&getMold(Entity1ToEntity2(centerEnt, items))<i&&items.getItem().getItem() ==item){
                i =getMold(Entity1ToEntity2(centerEnt, items));
                entity = items;
            }
        }
        return entity;
    }
    public static LivingEntity getNearestLiEntWithBL(Float radius, Entity centerEnt, Level level, List<LivingEntity> EntBlacklist){
        double x =centerEnt.getX();
        double y =centerEnt.getY();
        double z =centerEnt.getZ();
        LivingEntity entity =null;
        List<LivingEntity> mobbb = level.getEntitiesOfClass(LivingEntity.class, new AABB(x + radius, y + radius, z + radius, x - radius, y - radius, z - radius));
        double i =radius;
        for (LivingEntity targets : mobbb){
            if (targets!=null&&targets!=centerEnt&&getMold(Entity1ToEntity2(centerEnt,targets))<i&&!EntBlacklist.contains(targets)&&!(targets instanceof Player)&&!targets.isDeadOrDying()){
                i =getMold(Entity1ToEntity2(centerEnt,targets));
                entity =targets;
            }
        }
        return entity;
    }

    public static Entity getNearestMobWithinAngle(Float radius, Entity centerEnt, Level level,Vec3 centralVec,double angleCosine){
        double x =centerEnt.getX();
        double y =centerEnt.getY();
        double z =centerEnt.getZ();
        Entity entity =centerEnt;
        List<Mob> mobbb = level.getEntitiesOfClass(Mob.class, new AABB(x + radius, y + radius, z + radius, x - radius, y - radius, z - radius));
        double i =radius;
        for (Mob targets : mobbb){
            double a =getMold(Entity1ToEntity2(centerEnt,targets));
            double b =getMold(centralVec);
            double c =vec3Multiplyvec3(centralVec,Entity1ToEntity2(centerEnt,targets));
            if (targets!=null&& targets!=centerEnt && a<i && a*b !=0 && (c/(a*b))>angleCosine){
                i =getMold(Entity1ToEntity2(centerEnt,targets));
                entity =targets;
            }
        }
        if (entity!=centerEnt){
            return entity;
        }
        else return null;
    }
    public static Vec3 getScatteredVec3(Vec3 centVec,double angletangentMax){
        SecureRandom random =EtSHrnd();
        double x =random.nextDouble()-0.5;
        double y =random.nextDouble()-0.5;
        double z =random.nextDouble()-0.5;
        double v =angletangentMax * getMold(centVec);
        double d =random.nextDouble()*v-0.5*v;
        Vec3 vec3 =new Vec3(x,y,z);
        if (getMold(vec3)!=0) {
            vec3 = getUnitizedVec3(centVec.cross(vec3));
            if (vec3 != null) {
                return vec3.scale(d).add(centVec);
            }
        }
        return centVec;
    }
}
