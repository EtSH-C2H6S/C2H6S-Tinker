package com.c2h6s.etshtinker.recipes;

import com.c2h6s.etshtinker.etshtinker;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class etshRecipeSerializer {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, etshtinker.MOD_ID);

    public static final RegistryObject<RecipeSerializer<IonizedCannonRecipe>> IONIZER_SERIALIZER = SERIALIZERS.register("ionized_cannon",() -> IonizedCannonRecipe.Serializer.INSTANCE);


    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}
