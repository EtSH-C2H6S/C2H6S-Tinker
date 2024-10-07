package com.c2h6s.etshtinker.screen.weaponHUD;

import com.c2h6s.etshtinker.etshtinker;
import com.c2h6s.etshtinker.tools.item.tinker.IonizedCannon;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.List;

public class IonizedCannonHUD {
    public static ResourceLocation Texture0 = new ResourceLocation(etshtinker.MOD_ID,"/textures/gui/player_gui/gui_ionized_cannon_0.png");
    public static ResourceLocation Texture1 = new ResourceLocation(etshtinker.MOD_ID,"/textures/gui/player_gui/gui_ionized_cannon_1.png");
    public static ResourceLocation Texture2 = new ResourceLocation(etshtinker.MOD_ID,"/textures/gui/player_gui/gui_ionized_cannon_2.png");
    public static ResourceLocation Texture3 = new ResourceLocation(etshtinker.MOD_ID,"/textures/gui/player_gui/gui_ionized_cannon_3.png");
    public static ResourceLocation Texture4 = new ResourceLocation(etshtinker.MOD_ID,"/textures/gui/player_gui/gui_ionized_cannon_4.png");
    public static ResourceLocation Texture5 = new ResourceLocation(etshtinker.MOD_ID,"/textures/gui/player_gui/gui_ionized_cannon_5.png");
    public static ResourceLocation Texture6 = new ResourceLocation(etshtinker.MOD_ID,"/textures/gui/player_gui/gui_ionized_cannon_6.png");
    public static ResourceLocation Texture7 = new ResourceLocation(etshtinker.MOD_ID,"/textures/gui/player_gui/gui_ionized_cannon_7.png");
    public static ResourceLocation Texture8 = new ResourceLocation(etshtinker.MOD_ID,"/textures/gui/player_gui/gui_ionized_cannon_8.png");
    public static List<ResourceLocation> Texture = List.of(Texture0,Texture1,Texture2,Texture3,Texture4,Texture5,Texture6,Texture7,Texture8);
    public static IGuiOverlay IONIZED_CANNON_OVERLAY = ((gui, poseStack, partialTick, width, height) -> {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null){
            return;
        }
        if (!(player.getMainHandItem().getItem() instanceof IonizedCannon)){
            return;
        }
        ToolStack tool = ToolStack.from(player.getMainHandItem());
        if (tool.isBroken()){
            return;
        }
        float perc = IonizedCannonDrawtime.getPercentage();
        int amount = Mth.clamp((int) (perc*8),0,8);
        int x =width/2;
        int y = height/2;
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0,Texture.get(amount));
        GuiComponent.blit(poseStack,x-9,y-8,0,0,17,17,17,17);
    });
}
