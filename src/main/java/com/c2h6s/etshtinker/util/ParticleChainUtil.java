package com.c2h6s.etshtinker.util;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;


public class ParticleChainUtil {
    public static void summonSparkFromTo(ServerLevel level, int idStart, int idEnd) {
        Entity entityStart = level.getEntity(idStart);
        Entity entityEnd = level.getEntity(idEnd);
        if (entityStart == null || entityEnd == null) {
            return;
        }
        Vec3 dir = entityEnd.position().subtract(entityStart.position()).normalize();
        for (int i = 0; i < 1000; i++) {
            double x = entityStart.getX() + (dir.x * i * 0.15d);
            double y = entityStart.getY() + (dir.y * i * 0.15d);
            double z = entityStart.getZ() + (dir.z * i * 0.15d);
            Vec3 pos = new Vec3(x, y, z);
            if (pos.distanceToSqr(entityEnd.position()) <= 0.04d)
                break;
            level.sendParticles(ParticleTypes.CRIT, x, y + (entityStart.getBbHeight() / 2f), z,1,0, 0d, 0d, 0d);
        }
    }
    public static void summonELECSPARKFromTo(ServerLevel level, int idStart, int idEnd) {
        Entity entityStart = level.getEntity(idStart);
        Entity entityEnd = level.getEntity(idEnd);
        if (entityStart == null || entityEnd == null) {
            return;
        }
        Vec3 dir = entityEnd.position().subtract(entityStart.position()).normalize();
        for (int i = 0; i < 1000; i++) {
            double x = entityStart.getX() + (dir.x * i * 0.3d);
            double y = entityStart.getY() + (dir.y * i * 0.3d);
            double z = entityStart.getZ() + (dir.z * i * 0.3d);
            Vec3 pos = new Vec3(x, y, z);
            if (pos.distanceToSqr(entityEnd.position()) <= 0.04d)
                break;
            level.sendParticles(ParticleTypes.ELECTRIC_SPARK, x, y, z,1,0, 0d, 0d, 0d);

        }
    }
    public static void summonELECSPARKFromTo2(ServerLevel level, int idStart, int idEnd) {
        Entity entityStart = level.getEntity(idStart);
        Entity entityEnd = level.getEntity(idEnd);
        if (entityStart == null || entityEnd == null) {
            return;
        }
        Vec3 dir = entityEnd.position().add(0,0.5*entityEnd.getBbHeight(),0).subtract(entityStart.position().add(0,entityStart.getBbHeight()*0.5,0)).normalize();
        for (int i = 0; i < 1000; i++) {
            double x = entityStart.getX() + (dir.x * i * 0.3d);
            double y = entityStart.getY()+ entityStart.getBbHeight()*0.5+ (dir.y * i * 0.3d);
            double z = entityStart.getZ() + (dir.z * i * 0.3d);
            Vec3 pos = new Vec3(x, y, z);
            if (pos.distanceToSqr(entityEnd.position()) <= 0.04d)
                break;
            level.sendParticles(ParticleTypes.ELECTRIC_SPARK, x, y, z,1,0, 0d, 0d, 0d);

        }
    }
    public static void summonElectricFromTowithlimit(ServerLevel level, int idStart, int idEnd, int limit) {
        Entity entityStart = level.getEntity(idStart);
        Entity entityEnd = level.getEntity(idEnd);
        if (entityStart == null || entityEnd == null) {
            return;
        }
        Vec3 dir = entityEnd.position().subtract(entityStart.position()).normalize();
        for (int i = 0; i < limit; i++) {
            double x = entityStart.getX() + (dir.x * i * 0.3d);
            double y = entityStart.getY() + (dir.y * i * 0.3d);
            double z = entityStart.getZ() + (dir.z * i * 0.3d);
            Vec3 pos = new Vec3(x, y, z);
            if (pos.distanceToSqr(entityEnd.position()) <= 0.04d)
                break;
            level.sendParticles(etshtinkerParticleType.electric.get(), x, y + (entityStart.getBbHeight() / 2f), z,1,0, 0d, 0d, 0d);

        }
    }
    public static void summonLaserFromTo(ServerLevel level, int idStart, int idEnd) {
        Entity entityStart = level.getEntity(idStart);
        Entity entityEnd = level.getEntity(idEnd);
        if (entityStart == null || entityEnd == null) {
            return;
        }
        Vec3 dir = entityEnd.position().subtract(entityStart.position()).normalize();
        for (int i = 0; i < 1000; i++) {
            double x = entityStart.getX() + (dir.x * i * 0.3d);
            double y = entityStart.getY() + (dir.y * i * 0.3d);
            double z = entityStart.getZ() + (dir.z * i * 0.3d);
            Vec3 pos = new Vec3(x, y, z);
            if (pos.distanceToSqr(entityEnd.position()) <= 0.04d)
                break;
            level.sendParticles(etshtinkerParticleType.laserparticle.get(), x, y, z,1,0, 0d, 0d, 0d);

        }
    }
}
