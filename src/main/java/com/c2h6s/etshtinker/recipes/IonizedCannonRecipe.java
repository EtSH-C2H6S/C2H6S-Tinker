package com.c2h6s.etshtinker.recipes;

import com.c2h6s.etshtinker.etshtinker;
import com.c2h6s.etshtinker.util.JsonUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.openjdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import javax.annotation.Nullable;

public class IonizedCannonRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final Fluid fluid;
    private final float damage;
    private final String special;
    private final SimpleParticleType particle;

    public IonizedCannonRecipe(ResourceLocation id, Fluid fluid, float damage, String special, SimpleParticleType particle){
        this.id = id;
        this.fluid = fluid;
        this.damage = damage;
        this.special =special;
        this.particle=particle;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return !pLevel.isClientSide();
    }


    public Fluid getFluid(){return fluid;}

    public String getSpecial(){return special;}

    public SimpleParticleType getParticle(){return particle;}

    public float getDamage(){return damage;}

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return ItemStack.EMPTY;
    }
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }
    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }


    public static class Type implements RecipeType<IonizedCannonRecipe>{
        private Type(){}
        public static final Type INSTANCE = new Type();
        public static final String ID = "ionized_cannon";
    }



    public static class Serializer implements RecipeSerializer<IonizedCannonRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final  ResourceLocation ID =
                new ResourceLocation(etshtinker.MOD_ID,"ionized_cannon");

        @Override
        public IonizedCannonRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Fluid fluid = JsonUtil.readFluid(pSerializedRecipe,"fluid");
            String special = GsonHelper.getAsString(pSerializedRecipe,"special");
            SimpleParticleType simpleParticleType =JsonUtil.readParticle(pSerializedRecipe,"simple_particle_type");
            float damage =GsonHelper.getAsFloat(pSerializedRecipe,"damage");

            return new IonizedCannonRecipe(pRecipeId,fluid,damage,special,simpleParticleType);
        }

        @Override
        public @Nullable IonizedCannonRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            FluidStack stack = pBuffer.readFluidStack();
            Fluid fluid1 = stack.getFluid();
            String special = pBuffer.readUtf();
            ResourceLocation resourceLocation = pBuffer.readResourceLocation();
            ParticleType<?> particleType = ForgeRegistries.PARTICLE_TYPES.getValue(resourceLocation);
            if (!(particleType instanceof SimpleParticleType simpleParticleType)){
                throw new SyntaxException("Invalid particle type '" + resourceLocation + "'");
            }
            float damage = pBuffer.readFloat();
            return new IonizedCannonRecipe(pRecipeId,fluid1,damage,special,simpleParticleType);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, IonizedCannonRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());
            pBuffer.writeUtf(pRecipe.getSpecial());
            pBuffer.writeResourceLocation(ForgeRegistries.PARTICLE_TYPES.getKey(pRecipe.getParticle()));
            pBuffer.writeFluidStack(new FluidStack(pRecipe.getFluid(),1));
            pBuffer.writeFloat(pRecipe.damage);
        }
    }
}
