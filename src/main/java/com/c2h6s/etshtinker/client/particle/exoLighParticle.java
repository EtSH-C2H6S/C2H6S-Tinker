package com.c2h6s.etshtinker.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class exoLighParticle extends TextureSheetParticle {
    public static exoLighParticleProvider provider(SpriteSet spriteSet) {
        return new exoLighParticleProvider(spriteSet);
    }

    public static class exoLighParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public exoLighParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new exoLighParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }

    private final SpriteSet spriteSet;

    protected exoLighParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        this.lifetime = 16;
        this.quadSize = 1f;
        this.alpha =0.25f;
        this.gravity = 0f;
        this.hasPhysics = false;
        this.xd = vx * 1;
        this.yd = vy * 1;
        this.zd = vz * 1;
        this.setSpriteFromAge(spriteSet);
    }

    public void render(VertexConsumer consumer, Camera camera, float p_107680_) {
        Vec3 cameraPos = camera.getPosition();
        float x1 = (float)(Mth.lerp(p_107680_, this.xo, this.x) - cameraPos.x());
        float y1 = (float)(Mth.lerp(p_107680_, this.yo, this.y) - cameraPos.y());
        float z1 = (float)(Mth.lerp(p_107680_, this.zo, this.z) - cameraPos.z());
        Quaternion quaternion = camera.rotation();
        quaternion =new Quaternion(0,quaternion.j(),0,quaternion.r());

        Vector3f vector3f = new Vector3f(-1.0F, -1.0F, 0.0F);
        vector3f.transform(quaternion);
        Vector3f[] ls = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float size = this.getQuadSize(p_107680_);

        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f1 = ls[i];
            vector3f1.transform(quaternion);
            vector3f1.mul(size);
            vector3f1.add(x1, y1, z1);
        }

        float u0 = this.getU0();
        float u1 = this.getU1();
        float v0 = this.getV0();
        float v1 = this.getV1();
        int lightColor = this.getLightColor(p_107680_);
        consumer.vertex((double) ls[0].x(), (double) ls[0].y(), (double) ls[0].z()).uv(u1, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(lightColor).endVertex();
        consumer.vertex((double) ls[1].x(), (double) ls[1].y(), (double) ls[1].z()).uv(u1, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(lightColor).endVertex();
        consumer.vertex((double) ls[2].x(), (double) ls[2].y(), (double) ls[2].z()).uv(u0, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(lightColor).endVertex();
        consumer.vertex((double) ls[3].x(), (double) ls[3].y(), (double) ls[3].z()).uv(u0, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(lightColor).endVertex();
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
        if (this.age<=3) {
            this.alpha *= 2f;
        }
        if (this.age>=14) {
            this.alpha -= 0.5F;
        }
        if (!this.removed) {
            this.setSprite(this.spriteSet.get(EtSHrnd().nextInt(11)+1, 11));
        }
        super.tick();
    }
}
