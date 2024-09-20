package com.c2h6s.etshtinker.init;

import slimeknights.tconstruct.library.materials.IMaterialRegistry;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import com.c2h6s.etshtinker.tools.stats.*;

import static slimeknights.tconstruct.library.materials.MaterialRegistry.MELEE_HARVEST;
import static slimeknights.tconstruct.library.materials.MaterialRegistry.RANGED;

public class etshtinkerMaterialStats {
    public static void setup() {
        IMaterialRegistry registry = MaterialRegistry.getInstance();
        registry.registerStatType(ionizerMaterialStats.TYPE, MELEE_HARVEST);
        registry.registerStatType(fluidChamberMaterialStats.TYPE, MELEE_HARVEST);
        registry.registerStatType(PlasmaGeneratorMaterialStats.TYPE,MELEE_HARVEST);
    }



}
