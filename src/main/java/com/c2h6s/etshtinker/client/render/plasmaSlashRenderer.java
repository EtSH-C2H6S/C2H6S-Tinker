package com.c2h6s.etshtinker.client.render;

import com.c2h6s.etshtinker.Entities.PlasmaSlashEntity;
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
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;

public class plasmaSlashRenderer extends EntityRenderer<PlasmaSlashEntity> {

    private final ItemRenderer itemRenderer;
    public plasmaSlashRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
        this.itemRenderer=p_174008_.getItemRenderer();
    }
    public void render(PlasmaSlashEntity entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        if (entity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(entity) < 12.25D)) {
            matrixStackIn.pushPose();
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(entityYaw, entity.yRotO, entity.getYRot())- 90.0F ));
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(entityYaw, entity.xRotO, entity.getXRot())));
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(270+entity.angle));
            matrixStackIn.translate(-0.03125*entity.size, -0.09375*entity.size,0);
            matrixStackIn.scale(entity.size,entity.size,1);
            this.itemRenderer.renderStatic(entity.getSlash(), ItemTransforms.TransformType.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn, entity.getId());
            matrixStackIn.popPose();
            super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }

    protected int getSkyLightLevel(PlasmaSlashEntity p_114509_, BlockPos p_114510_) {
        return 15;
    }
    protected int getBlockLightLevel(PlasmaSlashEntity p_114496_, BlockPos p_114497_) {
        return 15;
    }


    @Override
    public ResourceLocation getTextureLocation(PlasmaSlashEntity entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
