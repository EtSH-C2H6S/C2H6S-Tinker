package com.c2h6s.etshtinker.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class SculkEnergyParticle extends TextureSheetParticle {
    public static SculkEnergyParticleProvider provider(SpriteSet spriteSet) {
        return new SculkEnergyParticleProvider(spriteSet);
    }

    public static class SculkEnergyParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public SculkEnergyParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SculkEnergyParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }

    private final SpriteSet spriteSet;

    public SculkEnergyParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.4f, 0.4f);
        this.quadSize=0.9f;
        this.lifetime =1;
        this.gravity = 0f;
        this.hasPhysics = false;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        this.pickSprite(spriteSet);
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
        if (this.age>this.lifetime){
            this.remove();
        }
        super.tick();
    }
}
