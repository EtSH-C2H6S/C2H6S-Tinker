package com.c2h6s.etshtinker.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class AtomicDecomposeParticle extends TextureSheetParticle {
    public static AtomicDecomposeParticleProvider provider(SpriteSet spriteSet) {
        return new AtomicDecomposeParticleProvider(spriteSet);
    }

    public static class AtomicDecomposeParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public AtomicDecomposeParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new AtomicDecomposeParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }

    private final SpriteSet spriteSet;

    protected AtomicDecomposeParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(1f, 1f);
        this.quadSize =EtSHrnd().nextFloat()*0.55F+0.05F;
        this.lifetime = 4;
        this.gravity = 0f;
        this.hasPhysics = false;
        this.xd = vx * 0;
        this.yd = vy * 0;
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
        if (this.age>this.lifetime){
            this.remove();
        }
        super.tick();
        if (!this.removed) {
            this.setSprite(this.spriteSet.get(this.age  % 5 + 1, 5));
        }
    }
}
