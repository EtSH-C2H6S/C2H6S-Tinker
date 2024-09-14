package com.c2h6s.etshtinker.client.render;

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
import slimeknights.tconstruct.gadgets.entity.shuriken.ShurikenEntityBase;

public class renderScikle extends EntityRenderer<ShurikenEntityBase> {
    private final ItemRenderer itemRenderer;
    public renderScikle(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ShurikenEntityBase entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        if (entity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(entity) < 12.25D)) {
            matrixStackIn.pushPose();
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90));
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(+(entity.tickCount + partialTicks) * 60 % 360));
            matrixStackIn.translate(-0.03125, -0.09375, 0);
            this.itemRenderer.renderStatic(entity.getItem(), ItemTransforms.TransformType.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn, entity.getId());
            matrixStackIn.popPose();
            
            super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }
    protected int getSkyLightLevel(ShurikenEntityBase p_114509_, BlockPos p_114510_) {
        return 15;
    }
    protected int getBlockLightLevel(ShurikenEntityBase p_114496_, BlockPos p_114497_) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(ShurikenEntityBase entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
