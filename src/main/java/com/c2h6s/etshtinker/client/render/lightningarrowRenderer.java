package com.c2h6s.etshtinker.client.render;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import com.c2h6s.etshtinker.Entities.*;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class lightningarrowRenderer extends ArrowRenderer<lightningarrow> {
    public static final ResourceLocation TEXT =new ResourceLocation(MOD_ID,"textures/models/shot_a.png");

    public lightningarrowRenderer(EntityRendererProvider.Context context) {super(context);}

    public ResourceLocation getTextureLocation(lightningarrow arrow) {return TEXT;}

    protected int getSkyLightLevel(lightningarrow p_114509_, BlockPos p_114510_) {
        return 15;
    }
    protected int getBlockLightLevel(lightningarrow p_114496_, BlockPos p_114497_) {
        return 15;
    }
}
