package com.c2h6s.etshtinker.Modifiers.IonizedCannon;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifierFluidWeapon;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.hooks.FluidConsumptionModifierHook;
import com.c2h6s.etshtinker.init.etshtinkerHook;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import static com.c2h6s.etshtinker.init.etshtinkerFluids.moltenExoAlloy.molten_exo_alloy;

public class MiracleCatalyst extends etshmodifieriii implements FluidConsumptionModifierHook {
    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, etshtinkerHook.FLUID_CONSUMPTION);
    }

    @Override
    public int getFluidConsumption(IToolStackView tool, FluidStack fluidStack, Player player, int baseAmount, int amount) {
        return fluidStack.getFluid()==molten_exo_alloy.get() ? Mth.clamp(amount/10,1,5) : Math.max(1,amount/10);
    }
}
