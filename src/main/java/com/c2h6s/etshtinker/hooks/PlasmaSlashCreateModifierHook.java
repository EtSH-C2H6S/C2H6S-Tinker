package com.c2h6s.etshtinker.hooks;

import com.c2h6s.etshtinker.Entities.PlasmaSlashEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;

public interface PlasmaSlashCreateModifierHook {
    PlasmaSlashEntity plasmaSlashCreate(IToolStackView tool, FluidStack fluidStack, ServerPlayer player, PlasmaSlashEntity slash);
    record AllMerger(Collection<PlasmaSlashCreateModifierHook> modules) implements PlasmaSlashCreateModifierHook {

        @Override
        public PlasmaSlashEntity plasmaSlashCreate(IToolStackView tool, FluidStack fluidStack, ServerPlayer player, PlasmaSlashEntity slash) {
            for (PlasmaSlashCreateModifierHook module:this.modules){
                slash=module.plasmaSlashCreate(tool,fluidStack,player,slash);
            }
            return slash;
        }
    }
}
