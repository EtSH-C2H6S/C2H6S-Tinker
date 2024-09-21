package com.c2h6s.etshtinker.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class curseParticle extends TextureSheetParticle {
    public static curseParticleProvider provider(SpriteSet spriteSet) {
        return new curseParticleProvider(spriteSet);
    }

    public static class curseParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public curseParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new curseParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }

    private final SpriteSet spriteSet;

    protected curseParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        this.lifetime =10;
        this.gravity = 0.25f;
        this.hasPhysics = true;
        this.xd = vx * 0.25;
        this.yd = Math.pow(vx*vx+vy*vy+vz*vz,0.5)*0.5;
        this.zd = vz * 0.25;
        this.pickSprite(spriteSet);
        this.quadSize =EtSHrnd().nextFloat()*0.55F+0.1F;
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
        this.alpha*=0.9f;
        super.tick();
    }
}
