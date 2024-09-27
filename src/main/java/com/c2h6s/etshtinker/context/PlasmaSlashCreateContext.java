package com.c2h6s.etshtinker.context;

import com.c2h6s.etshtinker.Entities.PlasmaSlashEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public record PlasmaSlashCreateContext(IToolStackView tool, FluidStack fluidStack, ServerPlayer player, PlasmaSlashEntity slash) {
    public PlasmaSlashCreateContext(IToolStackView tool, FluidStack fluidStack, ServerPlayer player, PlasmaSlashEntity slash){
        this.tool=tool;
        this.fluidStack=fluidStack;
        this.player=player;
        this.slash=slash;
    }
}
