package com.c2h6s.etshtinker.Modifiers.PlasmaSaber;

import com.c2h6s.etshtinker.Entities.PlasmaSlashEntity;
import com.c2h6s.etshtinker.hooks.FluidConsumptionModifierHook;
import com.c2h6s.etshtinker.hooks.PlasmaSlashCreateModifierHook;
import com.c2h6s.etshtinker.init.etshtinkerHook;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class PlasmaBlade extends NoLevelsModifier implements PlasmaSlashCreateModifierHook, FluidConsumptionModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, etshtinkerHook.SLASH_CREATE,etshtinkerHook.FLUID_CONSUMPTION);
    }

    @Override
    public PlasmaSlashEntity plasmaSlashCreate(IToolStackView tool, FluidStack fluidStack, ServerPlayer player, PlasmaSlashEntity slash) {
        slash.echoTriggerChance +=0.25f;
        return slash;
    }

    @Override
    public int getFluidConsumption(IToolStackView tool, FluidStack fluidStack, Player player, int baseAmount, int amount) {
        return amount+baseAmount*4;
    }
}
