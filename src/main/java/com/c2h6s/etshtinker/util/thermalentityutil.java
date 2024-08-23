package com.c2h6s.etshtinker.util;

import cofh.core.entity.ElectricArc;
import cofh.core.entity.ElectricField;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;


public class thermalentityutil {
    public static void summonElectricField(Level level, LivingEntity owner, Vec3 pos, int radius, int duration, int power){
        ElectricField field =new ElectricField(level,owner,pos,radius,duration,power);
        level.addFreshEntity(field);
    }
    public static void summonArc(Level level,LivingEntity target,Vec3 pos, int radius,int damage){
        ElectricArc arc =new ElectricArc(target.level,target);
        arc.attack(target);
        arc.setDamage(damage);
        arc.setRadius(radius);
        arc.setPos(target.getEyePosition());
        level.addFreshEntity(arc);
    }
}
