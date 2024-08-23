package com.c2h6s.etshtinker.init;

import mekanism.api.chemical.infuse.InfuseType;
import mekanism.common.registration.impl.InfuseTypeDeferredRegister;
import mekanism.common.registration.impl.InfuseTypeRegistryObject;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class etshtinkerChemicals {
    public static final InfuseTypeDeferredRegister INFUSE_TYPES = new InfuseTypeDeferredRegister(MOD_ID);

    public static final InfuseTypeRegistryObject<InfuseType> ULTRADENSE;
    public static final InfuseTypeRegistryObject<InfuseType> REFINED_GLOWSTONE;

    private etshtinkerChemicals() {
    }

    static {
        ULTRADENSE =INFUSE_TYPES.register("ultra_dense",0xAE7AFF);
        REFINED_GLOWSTONE =INFUSE_TYPES.register("refined_glowstone",0xFFF200);
    }
}
