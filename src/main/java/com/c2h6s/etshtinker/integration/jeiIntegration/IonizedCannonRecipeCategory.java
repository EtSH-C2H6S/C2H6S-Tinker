package com.c2h6s.etshtinker.integration.jeiIntegration;
/*
import com.c2h6s.etshtinker.etshtinker;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import com.c2h6s.etshtinker.recipes.IonizedCannonRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class IonizedCannonRecipeCategory implements IRecipeCategory<IonizedCannonRecipe>{
    public static final ResourceLocation UID = new ResourceLocation(etshtinker.MOD_ID,
            "ionized_cannon");

    public static final ResourceLocation TEXTURE = new ResourceLocation(etshtinker.MOD_ID,
            "textures/gui/jei/gui_ionized_recipe.png");


    private final IDrawable background;

    private final IDrawable icon;


    public IonizedCannonRecipeCategory(IGuiHelper helper){
        this.background  = helper.createDrawable(TEXTURE,0,0,140,26);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(etshtinkerItems.IONIZED_CANNON.get()));

    }

    @Override
    public RecipeType<IonizedCannonRecipe> getRecipeType() {
        return JEIPlugin.IONIZED_CANNON;
    }


    @Override
    public Component getTitle() {
        return Component.translatable("etshtinker.jei.ionized_cannon");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }


    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, IonizedCannonRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,5,5).setFluidRenderer(1,false,16,16).addIngredient(ForgeTypes.FLUID_STACK,new FluidStack(recipe.getFluid(),1));
    }

    @Override
    public void draw(IonizedCannonRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        Component damage =Component.translatable("etshtinker.tool.tooltip.effectivefluid").append(": ").append(String.format("%.2f",recipe.getDamage())).withStyle(ChatFormatting.GOLD);
        if (recipe.getSpecial()!=null) {
            Component special = Component.translatable("etshtinker.tool.tooltip.fluidhasspecial").append(": ").append(Component.translatable("etshtinker.tool.tooltip.fluidspecial." + recipe.getSpecial())).withStyle(ChatFormatting.LIGHT_PURPLE);
            Minecraft.getInstance().font.draw(stack, special, 26, 14, 0);
        }
        Minecraft.getInstance().font.draw(stack, damage, 26, 4, 0);
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
    }
}
*/