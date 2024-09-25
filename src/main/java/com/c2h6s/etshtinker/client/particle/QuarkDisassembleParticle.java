package com.c2h6s.etshtinker.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class QuarkDisassembleParticle extends TextureSheetParticle {
    public static QuarkDisassembleParticleProvider provider(SpriteSet spriteSet) {
        return new QuarkDisassembleParticleProvider(spriteSet);
    }

    public static class QuarkDisassembleParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public QuarkDisassembleParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new QuarkDisassembleParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }

    private final SpriteSet spriteSet;

    public QuarkDisassembleParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.4f, 0.4f);
        this.lifetime =this.random.nextInt(4)+3;
        this.gravity = 0f;
        this.hasPhysics = false;
        this.xd = vx * 1;
        this.yd = vy * 1;
        this.zd = vz * 1;
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
        this.quadSize*=1.25f;
        if (this.age>this.lifetime*0.5f) {
            this.friction=0.75f;
            this.alpha *= 0.75f;
        }
        super.tick();
    }
}
