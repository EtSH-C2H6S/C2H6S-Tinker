package com.c2h6s.etshtinker.hooks;

import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;

public interface FoilModifierHook {
    boolean isFoil(IToolStackView tool,Boolean isFoil);
    record AllMerger(Collection<FoilModifierHook> modules) implements FoilModifierHook {
        @Override
        public boolean isFoil(IToolStackView tool,Boolean isFoil){
            for (FoilModifierHook module:this.modules){
                isFoil=isFoil||module.isFoil(tool,isFoil);
            }
            return isFoil;
        }
    }
}
