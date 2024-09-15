package com.c2h6s.etshtinker.client.gui.adrenaline;
/*
import com.c2h6s.etshtinker.etshtinker;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import static com.c2h6s.etshtinker.client.gui.adrenaline.AdrenalineData.getAdrenaline;

public class AdrenalineHUD {
    public static final ResourceLocation BAR = new ResourceLocation(etshtinker.MOD_ID, "/textures/gui/player_gui/adrenalin.png");

    public static final IGuiOverlay ADRENALINE_OVERLAY = ((gui, poseStack, partialTick, width, height) -> {

            int x = width / 2;
            int y = height / 2;

            int count = (int) ((float) getAdrenaline() / 12.5f);
            RenderSystem.setShader(GameRenderer::getPositionShader);
            RenderSystem.setShaderColor(1, 1, 1, 1);
            RenderSystem.setShaderTexture(0, BAR);
            GuiComponent.blit(poseStack, x - 80, y - 16, 0, 0, 0, 0, 64, 16);
            for (int a = 0; a <= count; a++) {
                GuiComponent.blit(poseStack, x - 82 + 4 * a, y - 133, 0, 0, 2, 16, 6, 26);
            }

    });
}
*/