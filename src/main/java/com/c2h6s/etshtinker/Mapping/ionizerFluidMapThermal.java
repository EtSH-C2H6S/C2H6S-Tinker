package com.c2h6s.etshtinker.Mapping;

import static com.c2h6s.etshtinker.Mapping.ionizerFluidMap.*;
import static com.c2h6s.etshtinker.init.etshtinkerFluids.etshtinkerFluidThermal.molten_activated_chroma_steel;
import static com.c2h6s.etshtinker.init.etshtinkerParticleType.*;

public class ionizerFluidMapThermal {
    public static void extendMap(){
        fluidParts.put(molten_activated_chroma_steel.get(),plasmaexplosionred.get());
        fluidDmg.put(molten_activated_chroma_steel.get(),6f);
        fluidSpecial.put(molten_activated_chroma_steel.get(),"elemental");
    }
}
