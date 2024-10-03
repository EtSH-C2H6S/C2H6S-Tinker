package com.c2h6s.etshtinker.Modifiers.PlasmaSaber;

import com.c2h6s.etshtinker.Entities.PlasmaSlashEntity;
import com.c2h6s.etshtinker.Modifiers.modifiers.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import static com.c2h6s.etshtinker.util.vecCalc.getMold;

public class FlexibleBlade extends etshmodifierFluidWeapon {
    @Override
    public PlasmaSlashEntity onPlasmaSlashCreate(IToolStackView tool, FluidStack fluidStack, ServerPlayer player, PlasmaSlashEntity slash) {
        slash.setDeltaMovement(slash.getDeltaMovement().scale(0.5));
        return slash;
    }

    @Override
    public void modifierAfterPlasmaSlashHit(ToolStack tool, LivingEntity target, PlasmaSlashEntity slash, boolean isCritical, float slashDamage) {
        if (getMold(slash.getDeltaMovement())<=50) {
            slash.setDeltaMovement(slash.getDeltaMovement().scale(1.2));
        }
    }
}
