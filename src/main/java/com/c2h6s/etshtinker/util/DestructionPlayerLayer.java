package com.c2h6s.etshtinker.util;
/*
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;


public class DestructionPlayerLayer extends RenderLayer {
    //by CSDY
    private static final ResourceLocation WITHER_ARMOR_LOCATION = new ResourceLocation("textures/entity/wither/wither_armor.png");//可替换成其他图片，此处为凋零护甲
    private final HumanoidModel<Player> model;

    public DestructionPlayerLayer(RenderLayerParent p_174554_) {
        super(p_174554_);
        Minecraft mc = Minecraft.getInstance();
        EntityRendererProvider.Context context = new EntityRendererProvider.Context(
                mc.getEntityRenderDispatcher(),mc.getItemRenderer(),mc.getBlockRenderer(),mc.getEntityRenderDispatcher().getItemInHandRenderer(), mc.getResourceManager(),mc.getEntityModels(),mc.font
        );
        this.model = new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER));
    }

    protected float xOffset(float p_117702_) {
        return Mth.cos(p_117702_ * 0.02F) * 3.0F;
    }

    protected ResourceLocation getTextureLocation() {
        return WITHER_ARMOR_LOCATION;
    }

    protected EntityModel<Player> model() {
        return this.model;
    }

    @Override
    public void render(PoseStack p_116970_, MultiBufferSource p_116971_, int p_116972_, Entity entity, float p_116974_, float p_116975_, float p_116976_, float p_116977_, float p_116978_, float p_116979_) {
            Player player = (Player) entity;
            if (player.getPersistentData().getInt("annih_countdown")>0) {
                float $$10 = (float) entity.tickCount + p_116976_;
                EntityModel<Player> $$11 = this.model();
                $$11.prepareMobModel((Player) entity, p_116974_, p_116975_, p_116976_);
                this.getParentModel().copyPropertiesTo($$11);
                VertexConsumer $$12 = p_116971_.getBuffer(RenderType.energySwirl(this.getTextureLocation(), this.xOffset($$10) % 1.0F, $$10 * 0.01F % 1.0F));
                $$11.setupAnim((Player) entity, p_116974_, p_116975_, p_116977_, p_116978_, p_116979_);
                $$11.renderToBuffer(p_116970_, $$12, p_116972_, OverlayTexture.NO_OVERLAY, 0.5F, 0.5F, 0.5F, 1.0F);
            }
    }
}
*/