package com.c2h6s.etshtinker.client.render;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GlowingThrownRenderer<T extends Entity & ItemSupplier> extends ThrownItemRenderer<T> {
    public GlowingThrownRenderer(EntityRendererProvider.Context p_174416_, float p_174417_, boolean p_174418_) {
        super(p_174416_, p_174417_, p_174418_);
    }

    public GlowingThrownRenderer(EntityRendererProvider.Context context) {
        this(context, 1.0F, false);
    }

    protected int getSkyLightLevel(Entity p_114509_, BlockPos p_114510_) {
        return 15;
    }
    protected int getBlockLightLevel(Entity p_114496_, BlockPos p_114497_) {
        return 15;
    }
}
