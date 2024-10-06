package com.c2h6s.etshtinker.integration.jeiIntegration;

import com.c2h6s.etshtinker.etshtinker;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import com.c2h6s.etshtinker.recipes.IonizedCannonRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    public static RecipeType<IonizedCannonRecipe> IONIZED_CANNON = new RecipeType<>(IonizedCannonRecipeCategory.UID, IonizedCannonRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(etshtinker.MOD_ID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new IonizedCannonRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<IonizedCannonRecipe> recipesExtract = rm.getAllRecipesFor(IonizedCannonRecipe.Type.INSTANCE);

        registration.addRecipes(IONIZED_CANNON, recipesExtract);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(etshtinkerItems.IONIZED_CANNON.get()), IONIZED_CANNON);
    }
}
