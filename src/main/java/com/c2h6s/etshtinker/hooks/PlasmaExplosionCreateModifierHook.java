package com.c2h6s.etshtinker.hooks;

import com.c2h6s.etshtinker.Entities.plasmaexplosionentity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;

public interface PlasmaExplosionCreateModifierHook {
    plasmaexplosionentity plasmaExplosionCreate(IToolStackView tool, FluidStack fluidStack, Player player, plasmaexplosionentity explosion);
    record AllMerger(Collection<PlasmaExplosionCreateModifierHook> modules) implements PlasmaExplosionCreateModifierHook {
        @Override
        public plasmaexplosionentity plasmaExplosionCreate(IToolStackView tool, FluidStack fluidStack, Player player, plasmaexplosionentity explosion) {
            for (PlasmaExplosionCreateModifierHook module:this.modules){
                explosion=module.plasmaExplosionCreate(tool,fluidStack,player,explosion);
            }
            return explosion;
        }
    }
}
