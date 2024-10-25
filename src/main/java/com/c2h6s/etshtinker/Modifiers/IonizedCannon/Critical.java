package com.c2h6s.etshtinker.Modifiers.IonizedCannon;

import com.c2h6s.etshtinker.Entities.plasmaexplosionentity;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifierFluidWeapon;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class Critical extends etshmodifierFluidWeapon {
    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    public plasmaexplosionentity onPlasmaExplosionCreate(IToolStackView tool, FluidStack fluidStack, Player player, plasmaexplosionentity explosion) {
        explosion.forcedCrit=true;
        return explosion;
    }
}
