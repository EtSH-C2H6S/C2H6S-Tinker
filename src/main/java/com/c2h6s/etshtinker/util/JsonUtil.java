package com.c2h6s.etshtinker.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class JsonUtil {
    public static FluidStack readFluidStack(JsonObject json, String key) {
        return deserializeFluid(GsonHelper.getAsJsonObject(json,key));
    }

    public static JsonElement toJson(FluidStack stack){
        return FluidStack.CODEC.encodeStart(JsonOps.INSTANCE,stack).result().orElseThrow();
    }

    public static FluidStack deserializeFluid(@NotNull JsonObject json) {
        if (!json.has("amount")) {
            throw new JsonSyntaxException("Expected to receive a amount that is greater than zero");
        }
        JsonElement count = json.get("amount");
        if (!GsonHelper.isNumberValue(count)) {
            throw new JsonSyntaxException("Expected amount to be a number greater than zero.");
        }
        int amount = count.getAsJsonPrimitive().getAsInt();
        if (amount < 1) {
            throw new JsonSyntaxException("Expected amount to be greater than zero.");
        }
        ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(json, "fluid"));
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(resourceLocation);
        if (fluid == null || fluid == Fluids.EMPTY) {
            throw new JsonSyntaxException("Invalid fluid type '" + resourceLocation + "'");
        }
        return new FluidStack(fluid, amount);
    }
    public static Fluid readFluid(JsonObject json, String key){
        ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(json, key));
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(resourceLocation);
        if (fluid == null || fluid == Fluids.EMPTY) {
            throw new JsonSyntaxException("Invalid fluid type '" + resourceLocation + "'");
        }
        return fluid;
    }
    public static SimpleParticleType readParticle(JsonObject json, String key){
        ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(json, key));
        if (!(ForgeRegistries.PARTICLE_TYPES.getValue(resourceLocation) instanceof SimpleParticleType simpleParticleType)){
            throw new JsonSyntaxException("Invalid particle type '" + resourceLocation + "'");
        }
        return simpleParticleType;
    }
}
