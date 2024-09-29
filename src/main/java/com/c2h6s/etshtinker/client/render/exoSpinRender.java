package com.c2h6s.etshtinker.client.render;

import com.c2h6s.etshtinker.Entities.ItemProjectile;
import com.c2h6s.etshtinker.Entities.exoSpinEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class exoSpinRender extends EntityRenderer<exoSpinEntity> {
    private final ItemRenderer itemRenderer;
    public exoSpinRender(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(exoSpinEntity entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        if ((entity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(entity) < 12.25D))&&entity.getItem()!=null) {
            matrixStackIn.pushPose();
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90));
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(+(entity.tickCount + partialTicks) * entity.rotationSpeed % 360));
            matrixStackIn.translate(-0.03125*entity.scale, -0.09375*entity.scale, 0);
            matrixStackIn.scale(entity.scale,entity.scale,1);
            this.itemRenderer.renderStatic(entity.getItem(), ItemTransforms.TransformType.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn, entity.getId());
            matrixStackIn.popPose();
            super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }
    protected int getSkyLightLevel(exoSpinEntity p_114509_, BlockPos p_114510_) {
        return 15;
    }
    protected int getBlockLightLevel(exoSpinEntity p_114496_, BlockPos p_114497_) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(exoSpinEntity entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
