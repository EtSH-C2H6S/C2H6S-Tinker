package com.c2h6s.etshtinker.util;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class ParticleUtil {


    public static void spawnParticle(Level level, SimpleParticleType type,boolean alwaysVisible,double x,double y,double z,double vx,double vy,double vz){
        level.addParticle(type,alwaysVisible,x,y,z,vx,vy,vz);
    }
}
