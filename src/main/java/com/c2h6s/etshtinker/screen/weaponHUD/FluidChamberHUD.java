package com.c2h6s.etshtinker.screen.weaponHUD;

import com.c2h6s.etshtinker.etshtinker;
import com.c2h6s.etshtinker.tools.item.tinker.ConstrainedPlasmaSaber;
import com.c2h6s.etshtinker.tools.item.tinker.IonizedCannon;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class FluidChamberHUD {
    public static ResourceLocation Texture = new ResourceLocation(etshtinker.MOD_ID,"/textures/gui/player_gui/gui_fluid_weapon.png");
    public static IGuiOverlay FLUID_CHAMBER_OVERLAY = ((gui, poseStack, partialTick, width, height) -> {
        if (FluidChamberData.getNbt()==null){
            return;
        }
        if (!FluidChamberData.getNbt().contains("amount")||FluidChamberData.getNbt().getInt("max")==0){
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null){
            return;
        }
        if (!(player.getMainHandItem().getItem() instanceof IonizedCannon||player.getMainHandItem().getItem() instanceof ConstrainedPlasmaSaber)){
            return;
        }
        ToolStack tool = ToolStack.from(player.getMainHandItem());
        if (tool.isBroken()){
            return;
        }
        float amount =FluidChamberData.getNbt().getInt("amount");
        float vl =FluidChamberData.getNbt().getInt("max");
        float perc = amount/vl;
        float visual = perc*100;
        int x =width/2;
        int y = height/2;
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0,Texture);
        gui.blit(poseStack,x+20,y-7,0,0,11,14);
        GuiComponent.drawString(poseStack, Minecraft.getInstance().font,  Component.translatable(String.format("%.1f",visual)+"%").withStyle(ChatFormatting.RED),x+20,y-4,255);
    });
}
