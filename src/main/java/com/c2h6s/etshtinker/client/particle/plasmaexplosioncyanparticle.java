package com.c2h6s.etshtinker.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class plasmaexplosioncyanparticle extends TextureSheetParticle {
    public static plasmaexplosioncyanparticle.PlasmaexplosioncyanParticleProvider provider(SpriteSet spriteSet) {
        return new plasmaexplosioncyanparticle.PlasmaexplosioncyanParticleProvider(spriteSet);
    }

    public static class PlasmaexplosioncyanParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public PlasmaexplosioncyanParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new plasmaexplosionpurpleparticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }

    private final SpriteSet spriteSet;

    protected plasmaexplosioncyanparticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(1f, 1f);
        this.quadSize =1.3f;
        this.lifetime = 11;
        this.gravity = 0f;
        this.hasPhysics = true;
        this.xd = vx * 0;
        this.yd = vy * 0;
        this.zd = vz * 0;
        this.setSpriteFromAge(spriteSet);
    }
    public int getLightColor(float p_234080_) {
        return 240;
    }
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.removed) {
            this.setSprite(this.spriteSet.get(this.age  % 12 + 1, 12));
        }
        if (this.age>this.lifetime){
            this.remove();
        }
    }
}
