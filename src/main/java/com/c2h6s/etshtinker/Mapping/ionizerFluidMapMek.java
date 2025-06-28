package com.c2h6s.etshtinker.Mapping;

import static com.c2h6s.etshtinker.Mapping.ionizerFluidMap.*;
import static com.c2h6s.etshtinker.init.etshtinkerFluids.etshtinkerFluidMekanism.*;
import static com.c2h6s.etshtinker.init.etshtinkerParticleType.*;
import static mekanism.common.registries.MekanismFluids.*;

public class ionizerFluidMapMek {
    public static void extendMap(){
        fluidParts.put(LITHIUM.getFluid(),plasmaexplosionred.get());
        fluidDmg.put(LITHIUM.getFluid(),3.5F);
        fluidParts.put(URANIUM_HEXAFLUORIDE.getFluid(),plasmaexplosionlime.get());
        fluidDmg.put(URANIUM_HEXAFLUORIDE.getFluid(),4.5F);
        fluidSpecial.put(URANIUM_HEXAFLUORIDE.getFluid(),"radiation");
        fluidParts.put(SUPERHEATED_SODIUM.getFluid(),plasmaexplosionyellow.get());
        fluidDmg.put(SUPERHEATED_SODIUM.getFluid(),9.5F);
        fluidSpecial.put(SUPERHEATED_SODIUM.getFluid(),"explosion");
        fluidParts.put(CHLORINE.getFluid(),plasmaexplosionlime.get());
        fluidDmg.put(CHLORINE.getFluid(),1.5F);
        fluidSpecial.put(CHLORINE.getFluid(),"poison");
        fluidParts.put(SULFUR_DIOXIDE.getFluid(),plasmaexplosionyellow.get());
        fluidDmg.put(SULFUR_DIOXIDE.getFluid(),2.5F);
        fluidSpecial.put(SULFUR_DIOXIDE.getFluid(),"poison");
        fluidParts.put(SULFUR_TRIOXIDE.getFluid(),plasmaexplosionorange.get());
        fluidDmg.put(SULFUR_TRIOXIDE.getFluid(),2.5F);
        fluidSpecial.put(SULFUR_TRIOXIDE.getFluid(),"corrosive");
        fluidParts.put(HYDROFLUORIC_ACID.getFluid(),plasmaexplosionlime.get());
        fluidDmg.put(HYDROFLUORIC_ACID.getFluid(),3.5F);
        fluidSpecial.put(HYDROFLUORIC_ACID.getFluid(),"corrosive");
        fluidParts.put(HYDROGEN_CHLORIDE.getFluid(),plasmaexplosiongreen.get());
        fluidDmg.put(HYDROGEN_CHLORIDE.getFluid(),1.5F);
        fluidSpecial.put(HYDROGEN_CHLORIDE.getFluid(),"corrosive");

        fluidDmg.put(antimatter_l.get(),10f);
        fluidSpecial.put(antimatter_l.get(),"antimatter_explosion");
        fluidParts.put(molten_electronium.get(),plasmaexplosioncyan.get());
        fluidDmg.put(molten_electronium.get(),12f);
        fluidSpecial.put(molten_electronium.get(),"ionize");
        fluidParts.put(molten_anti_neutronium.get(),plasmaexplosionred.get());
        fluidDmg.put(molten_anti_neutronium.get(),32f);
        fluidSpecial.put(molten_anti_neutronium.get(),"annihilate");
        fluidParts.put(molten_protonium.get(),plasmaexplosionpurple.get());
        fluidDmg.put(molten_protonium.get(),10f);
        fluidParts.put(molten_ultra_dense.get(),plasmaexplosionpurple.get());
        fluidDmg.put(molten_ultra_dense.get(),8f);
        fluidParts.put(unstable_exotic_matter.get(),plasmaexplosiongreen.get());
        fluidDmg.put(unstable_exotic_matter.get(),2.25f);
        fluidSpecial.put(unstable_exotic_matter.get(),"tracking");
        fluidParts.put(stablized_exotic_matter.get(),plasmaexplosiongreen.get());
        fluidDmg.put(stablized_exotic_matter.get(),5f);
        fluidSpecial.put(stablized_exotic_matter.get(),"tracking");
        fluidParts.put(annihilating_plasma.get(),plasmaexplosionpurple.get());
        fluidDmg.put(annihilating_plasma.get(),12F);
        fluidParts.put(antimatter_l.get(),plasmaexplosionpurple.get());
    }
}
