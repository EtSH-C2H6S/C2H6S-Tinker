package com.c2h6s.etshtinker.client.render;

import com.c2h6s.etshtinker.Entities.ExoSlashProjectile;
import com.hoshino.cti.Entity.Projectiles.base.BasicElementalOrbEntity;
import com.hoshino.cti.client.renderer.projectile.RenderElementalOrb;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class ExoSlashRenderer extends RenderElementalOrb {
    private final EntityRendererProvider.Context context;
    public ExoSlashRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, 64, 255, 145);
        this.context = pContext;
    }

    @Override
    public void render(BasicElementalOrbEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        ThrownItemRenderer<ExoSlashProjectile> renderer = new ThrownItemRenderer<>(this.context,1,true);
        if (pEntity instanceof ExoSlashProjectile projectile)
            renderer.render(projectile,pEntityYaw,pPartialTick,pPoseStack,pBuffer, LightTexture.FULL_BRIGHT);
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }
}
