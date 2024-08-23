package com.c2h6s.etshtinker.Mapping;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.Fluid;


import static com.c2h6s.etshtinker.init.etshtinkerFluids.etshtinkerFluidAE.*;
import static com.c2h6s.etshtinker.init.etshtinkerFluids.etshtinkerFluidAdastra.*;
import static com.c2h6s.etshtinker.init.etshtinkerFluids.etshtinkerFluidMekanism.*;
import static com.c2h6s.etshtinker.init.etshtinkerParticleType.*;
import static slimeknights.tconstruct.fluids.TinkerFluids.*;
import static net.minecraft.world.level.material.Fluids.*;

import java.util.HashMap;
import java.util.Map;

public class ionizerFluidMap {
    public static Map<Fluid, SimpleParticleType> fluidParts =new HashMap<>();
    public static Map<Fluid, Float> fluidDmg =new HashMap<>();
    public static Map<Fluid, String> fluidSpecial =new HashMap<>();
    public static void extendMap(){
        fluidParts.put(annihilating_plasma.get(),plasmaexplosionpurple.get());
        fluidDmg.put(annihilating_plasma.get(),15F);
        fluidParts.put(antimatter_l.get(),plasmaexplosionpurple.get());
        fluidDmg.put(antimatter_l.get(),14f);
        fluidSpecial.put(antimatter_l.get(),"antimatter_explosion");
        fluidParts.put(molten_electronium.get(),plasmaexplosioncyan.get());
        fluidDmg.put(molten_electronium.get(),33f);
        fluidSpecial.put(molten_electronium.get(),"ionize");
        fluidParts.put(molten_protonium.get(),plasmaexplosionpurple.get());
        fluidDmg.put(molten_protonium.get(),20f);
        fluidParts.put(molten_ultra_dense.get(),plasmaexplosionpurple.get());
        fluidDmg.put(molten_ultra_dense.get(),6f);
        fluidParts.put(molten_exo_alloy.get(),plasmaexplosiongreen.get());
        fluidDmg.put(molten_exo_alloy.get(),128f);
        fluidSpecial.put(molten_exo_alloy.get(),"nova_radiation");
        fluidParts.put(molten_meteoralloy.get(),plasmaexplosionorange.get());
        fluidDmg.put(molten_meteoralloy.get(),3f);
        fluidSpecial.put(molten_meteoralloy.get(),"explosion");
        fluidParts.put(molten_stellaralloy.get(),plasmaexplosionyellow.get());
        fluidDmg.put(molten_stellaralloy.get(),4f);
        fluidSpecial.put(molten_stellaralloy.get(),"explosion");
        fluidParts.put(unstable_exotic_matter.get(),plasmaexplosiongreen.get());
        fluidDmg.put(unstable_exotic_matter.get(),2.25f);
        fluidSpecial.put(unstable_exotic_matter.get(),"tracking");

        fluidParts.put(moltenCopper.get(),plasmaexplosiongreen.get());
        fluidDmg.put(moltenCopper.get(),1.5f);
        fluidParts.put(moltenConstantan.get(),plasmaexplosiongreen.get());
        fluidDmg.put(moltenConstantan.get(),1.7f);
        fluidParts.put(moltenBronze.get(),plasmaexplosiongreen.get());
        fluidDmg.put(moltenBronze.get(),1.62f);
        fluidParts.put(moltenBrass.get(),plasmaexplosiongreen.get());
        fluidDmg.put(moltenBrass.get(),1.66f);
        fluidParts.put(moltenIron.get(),plasmaexplosionred.get());
        fluidDmg.put(moltenIron.get(),1.75f);
        fluidParts.put(moltenInvar.get(),plasmaexplosionred.get());
        fluidDmg.put(moltenInvar.get(),2.08f);
        fluidParts.put(moltenSteel.get(),plasmaexplosionred.get());
        fluidDmg.put(moltenSteel.get(),1.9f);
        fluidParts.put(moltenGold.get(),plasmaexplosionyellow.get());
        fluidDmg.put(moltenGold.get(),2.18f);
        fluidParts.put(moltenElectrum.get(),plasmaexplosionyellow.get());
        fluidDmg.put(moltenElectrum.get(),2.21f);
        fluidParts.put(moltenCobalt.get(),plasmaexplosionblue.get());
        fluidDmg.put(moltenCobalt.get(),2.45f);
        fluidParts.put(moltenNetherite.get(),plasmaexplosionorange.get());
        fluidDmg.put(moltenNetherite.get(),2.9f);
        fluidParts.put(moltenAmethystBronze.get(),plasmaexplosionpurple.get());
        fluidDmg.put(moltenAmethystBronze.get(),1.5f);
        fluidSpecial.put(moltenAmethystBronze.get(),"magic_damage");
        fluidParts.put(moltenDiamond.get(),plasmaexplosioncyan.get());
        fluidDmg.put(moltenDiamond.get(),2f);
        fluidParts.put(moltenEmerald.get(),plasmaexplosiongreen.get());
        fluidDmg.put(moltenEmerald.get(),2F);
        fluidParts.put(moltenEnder.get(),plasmaexplosionpurple.get());
        fluidDmg.put(moltenEnder.get(),3.65f);
        fluidSpecial.put(moltenEnder.get(),"random_scatter");
        fluidParts.put(scorchedStone.get(),plasmaexplosionorange.get());
        fluidDmg.put(scorchedStone.get(),1.3f);
        fluidSpecial.put(scorchedStone.get(),"burn");
        fluidParts.put(searedStone.get(),plasmaexplosionorange.get());
        fluidDmg.put(searedStone.get(),1.3f);
        fluidSpecial.put(searedStone.get(),"burn");
        fluidParts.put(moltenManyullyn.get(),plasmaexplosionpurple.get());
        fluidDmg.put(moltenManyullyn.get(),3f);
        fluidParts.put(blazingBlood.get(),plasmaexplosionyellow.get());
        fluidDmg.put(blazingBlood.get(),1.35f);
        fluidSpecial.put(blazingBlood.get(),"burn");
        fluidParts.put(moltenLead.get(),plasmaexplosionpurple.get());
        fluidDmg.put(moltenLead.get(),2.1f);
        fluidSpecial.put(moltenLead.get(),"poison");

        fluidParts.put(LAVA,plasmaexplosionorange.get());
        fluidDmg.put(LAVA,1.15f);
        fluidSpecial.put(LAVA,"burn");
    }
    public static float getFluidDamage(Fluid fluid){
        if (fluidDmg.containsKey(fluid)) {
            return fluidDmg.get(fluid);
        }
        else return 2;
    }
    public static SimpleParticleType getFluidparticle(Fluid fluid){
        if (fluidParts.containsKey(fluid)) {
            return fluidParts.get(fluid);
        }
        else return null;
    }
    public static String getFluidSpecial(Fluid fluid){
        if (fluidSpecial.containsKey(fluid)) {
            return fluidSpecial.get(fluid);
        }
        else return null;
    }
}
