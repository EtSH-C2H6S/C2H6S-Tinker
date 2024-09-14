package com.c2h6s.etshtinker.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class annhilScatterParticle extends TextureSheetParticle {
    public static annhilScatterParticleProvider provider(SpriteSet spriteSet) {
        return new annhilScatterParticleProvider(spriteSet);
    }

    public static class annhilScatterParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public annhilScatterParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ElectricParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }

    private final SpriteSet spriteSet;

    public annhilScatterParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.alpha = 0.65f;
        this.spriteSet = spriteSet;
        this.setSize(0.4f, 0.4f);
        this.lifetime =Math.max(1, 10 + (this.random.nextInt(4) - 2));
        this.gravity = 0f;
        this.hasPhysics = false;
        this.xd = vx;
        this.yd = vy;
        this.zd = vz;
        this.quadSize=0.5F+(this.random.nextFloat() * 4F + 4F) * 2.0F;
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
        this.alpha*=0.975f;
        super.tick();
    }
}
