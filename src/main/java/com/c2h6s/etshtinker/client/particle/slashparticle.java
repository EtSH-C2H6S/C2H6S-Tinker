package com.c2h6s.etshtinker.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

import java.security.SecureRandom;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class slashparticle extends TextureSheetParticle {
    public static slashParticleProvider provider(SpriteSet spriteSet) {
        return new slashParticleProvider(spriteSet);
    }

    public static class slashParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public slashParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new slashparticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }

    private final SpriteSet spriteSet;

    protected slashparticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        SecureRandom random1 =EtSHrnd();
        this.roll = random1.nextFloat(180);
        this.oRoll = 0;
        this.lifetime = 4;
        this.gravity = 0f;
        this.hasPhysics = false;
        this.xd = vx * 0;
        this.yd = vy * 0;
        this.zd = vz * 0;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
    public int getLightColor(float p_234080_) {
        return 240;
    }
    @Override
    public void tick() {
        super.tick();
        this.quadSize *=2f;
        this.alpha *=0.7f;
        if (this.age>this.lifetime){
            this.remove();
        }
    }
}
