package com.c2h6s.etshtinker.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class annhilParticle extends TextureSheetParticle {
    public static annhilParticleProvider provider(SpriteSet spriteSet) {
        return new annhilParticleProvider(spriteSet);
    }

    public static class annhilParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public annhilParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new annhilParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }

    private final SpriteSet spriteSet;

    public annhilParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.alpha = 0.75f;
        this.spriteSet = spriteSet;
        this.setSize(0.4f, 0.4f);
        this.lifetime =Math.max(1, 10 + (this.random.nextInt(4) - 2));
        this.gravity = -1f;
        this.hasPhysics = false;
        this.xd = 0;
        this.yd = vy;
        this.zd = 0;
        this.quadSize=0.25F+(this.random.nextFloat() * 1.5F + 1F) * 0.25F;
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
        super.tick();
        if (this.age>this.lifetime){
            this.remove();
        }
        scale(1.1f);
        this.alpha*=0.975f;
    }

    @Override
    public void move(double p_107246_, double p_107247_, double p_107248_) {
        super.move(p_107246_, p_107247_, p_107248_);
        //this.setPos(this.x,this.y+0.25,this.z);
    }
}
