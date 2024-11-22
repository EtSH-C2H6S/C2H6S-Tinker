package com.c2h6s.etshtinker.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class StrongPoisonParticle extends TextureSheetParticle {
    public static StrongPoisonParticle.StrongPoisonParticleProvider provider(SpriteSet spriteSet) {
        return new StrongPoisonParticle.StrongPoisonParticleProvider(spriteSet);
    }

    public static class StrongPoisonParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public StrongPoisonParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new StrongPoisonParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }


    private final SpriteSet spriteSet;

    protected StrongPoisonParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.oRoll =this.roll;
        this.lifetime = 4;
        this.gravity = -0.15f;
        this.xd = vx * 0;
        this.yd = 0.15F;
        this.zd = vz * 0;
        this.setSpriteFromAge(spriteSet);
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
        if (!this.removed) {
            this.setSprite(this.spriteSet.get(this.age  % 5 + 1, 5));
        }
        if (this.age>this.lifetime){
            this.remove();
        }
    }
}
