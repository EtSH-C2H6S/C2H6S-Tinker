package com.c2h6s.etshtinker.Mapping;

import static com.c2h6s.etshtinker.Mapping.ionizerFluidMap.*;
import static com.c2h6s.etshtinker.init.etshtinkerFluids.etshtinkerFluidMekanism.*;
import static com.c2h6s.etshtinker.init.etshtinkerParticleType.*;
import static mekanism.common.registries.MekanismFluids.*;

public class ionizerFluidMapMek {
    public static void extendMap(){
        fluidParts.put(LITHIUM.getFluid(),plasmaexplosionred.get());
        fluidDmg.put(LITHIUM.getFluid(),3F);
        fluidParts.put(URANIUM_HEXAFLUORIDE.getFluid(),plasmaexplosionlime.get());
        fluidDmg.put(URANIUM_HEXAFLUORIDE.getFluid(),4F);
        fluidSpecial.put(URANIUM_HEXAFLUORIDE.getFluid(),"radiation");
        fluidParts.put(SUPERHEATED_SODIUM.getFluid(),plasmaexplosionyellow.get());
        fluidDmg.put(SUPERHEATED_SODIUM.getFluid(),9F);
        fluidSpecial.put(SUPERHEATED_SODIUM.getFluid(),"explosion");
        fluidParts.put(CHLORINE.getFluid(),plasmaexplosionlime.get());
        fluidDmg.put(CHLORINE.getFluid(),1.5F);
        fluidSpecial.put(CHLORINE.getFluid(),"poison");
        fluidParts.put(SULFUR_DIOXIDE.getFluid(),plasmaexplosionyellow.get());
        fluidDmg.put(SULFUR_DIOXIDE.getFluid(),2F);
        fluidSpecial.put(SULFUR_DIOXIDE.getFluid(),"poison");
        fluidParts.put(SULFUR_TRIOXIDE.getFluid(),plasmaexplosionorange.get());
        fluidDmg.put(SULFUR_TRIOXIDE.getFluid(),2F);
        fluidSpecial.put(SULFUR_TRIOXIDE.getFluid(),"corrosive");
        fluidParts.put(HYDROFLUORIC_ACID.getFluid(),plasmaexplosionlime.get());
        fluidDmg.put(HYDROFLUORIC_ACID.getFluid(),3F);
        fluidSpecial.put(HYDROFLUORIC_ACID.getFluid(),"corrosive");
        fluidParts.put(HYDROGEN_CHLORIDE.getFluid(),plasmaexplosiongreen.get());
        fluidDmg.put(HYDROGEN_CHLORIDE.getFluid(),1F);
        fluidSpecial.put(HYDROGEN_CHLORIDE.getFluid(),"corrosive");

        fluidDmg.put(antimatter_l.get(),14f);
        fluidSpecial.put(antimatter_l.get(),"antimatter_explosion");
        fluidParts.put(molten_electronium.get(),plasmaexplosioncyan.get());
        fluidDmg.put(molten_electronium.get(),33f);
        fluidSpecial.put(molten_electronium.get(),"ionize");
        fluidParts.put(molten_anti_neutronium.get(),plasmaexplosionred.get());
        fluidDmg.put(molten_anti_neutronium.get(),0f);
        fluidSpecial.put(molten_anti_neutronium.get(),"annihilate");
        fluidParts.put(molten_protonium.get(),plasmaexplosionpurple.get());
        fluidDmg.put(molten_protonium.get(),20f);
        fluidParts.put(molten_ultra_dense.get(),plasmaexplosionpurple.get());
        fluidDmg.put(molten_ultra_dense.get(),6f);
        fluidParts.put(unstable_exotic_matter.get(),plasmaexplosiongreen.get());
        fluidDmg.put(unstable_exotic_matter.get(),2.25f);
        fluidSpecial.put(unstable_exotic_matter.get(),"tracking");
        fluidParts.put(stablized_exotic_matter.get(),plasmaexplosiongreen.get());
        fluidDmg.put(stablized_exotic_matter.get(),22.25f);
        fluidSpecial.put(stablized_exotic_matter.get(),"tracking");
        fluidParts.put(annihilating_plasma.get(),plasmaexplosionpurple.get());
        fluidDmg.put(annihilating_plasma.get(),15F);
        fluidParts.put(antimatter_l.get(),plasmaexplosionpurple.get());
    }
}
