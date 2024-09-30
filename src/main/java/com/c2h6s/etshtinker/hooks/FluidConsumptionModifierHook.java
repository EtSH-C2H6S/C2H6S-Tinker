package com.c2h6s.etshtinker.hooks;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;

public interface FluidConsumptionModifierHook {
    int getFluidConsumption(IToolStackView tool, FluidStack fluidStack, Player player,int baseAmount,int amount);
    record AllMerger(Collection<FluidConsumptionModifierHook> modules) implements FluidConsumptionModifierHook {
        @Override
        public int getFluidConsumption(IToolStackView tool, FluidStack fluidStack, Player player, int baseAmount,int amount){
            for (FluidConsumptionModifierHook module:this.modules){
                amount=module.getFluidConsumption(tool,fluidStack,player,baseAmount,amount);
            }
            return amount;
        }
    }
}
