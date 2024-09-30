package com.c2h6s.etshtinker.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.FluidType.Properties;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FluidObject;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class etshtinkerFluids {
    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MOD_ID);

    private static FluidObject<ForgeFlowingFluid> register(String name, int temp) {
        return FLUIDS.register(name).type(Properties.create().density(2000).viscosity(10000).temperature(temp).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_EMPTY_LAVA)).bucket().flowing();
    }

    public static FluidObject<ForgeFlowingFluid> molten_lightless_alloy = register("molten_lightless_alloy", 1800);//熔融本影合金
    public static FluidObject<ForgeFlowingFluid> molten_energy_sculk = register("molten_energy_sculk", 2200);
    public static FluidObject<ForgeFlowingFluid> molten_knsu = register("molten_knsu", 3000);


    public class etshtinkerFluidMekanism{
        public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MOD_ID);
        private static FluidObject<ForgeFlowingFluid> register(String name, int temp) {
            return FLUIDS.register(name).type(FluidType.Properties.create().density(2000).viscosity(10000).temperature(temp).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_EMPTY_LAVA)).bucket().flowing();
        }
        public static FluidObject<ForgeFlowingFluid> molten_ultra_dense = register("molten_ultra_dense", 1800);//致密超流体
        public static FluidObject<ForgeFlowingFluid> antimatter_l = register("antimatter_l", 1800);//液态反物质
        public static FluidObject<ForgeFlowingFluid> annihilating_plasma = register("annihilating_plasma", 16384);//湮灭等离子体
        public static FluidObject<ForgeFlowingFluid> unstable_exotic_matter = register("unstable_exotic_matter", 1800);//不稳定奇异物质
        public static FluidObject<ForgeFlowingFluid> overchargedneutronium = register("overchargedneutronium", 1800);//超充能中子流体
        public static FluidObject<ForgeFlowingFluid> molten_electronium = register("molten_electronium", 1800);//电子流体
        public static FluidObject<ForgeFlowingFluid> molten_protonium = register("molten_protonium", 1800);//质子流体
        public static FluidObject<ForgeFlowingFluid> molten_trinity_intereactive_alloy = register("molten_trinity_intereactive_alloy", 16384);
        public static FluidObject<ForgeFlowingFluid> molten_marcoatom = register("molten_marcoatom", 65536);
        public static FluidObject<ForgeFlowingFluid> stablized_exotic_matter = register("stablized_exotic_matter", 0);//稳态奇异物质
        public static FluidObject<ForgeFlowingFluid> molten_anti_neutronium = register("molten_anti_neutronium", 1800);
    }
    public class etshtinkerFluidThermal {
        public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MOD_ID);

        private static FluidObject<ForgeFlowingFluid> register(String name, int temp) {
            return FLUIDS.register(name).type(FluidType.Properties.create().density(2000).viscosity(10000).temperature(temp).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_EMPTY_LAVA)).bucket().flowing();
        }
        public static FluidObject<ForgeFlowingFluid> molten_activated_chroma_steel = register("molten_activated_chroma_steel", 1800);
        public static FluidObject<ForgeFlowingFluid> molten_basalz_signalum = register("molten_basalz_signalum", 1800);
        public static FluidObject<ForgeFlowingFluid> molten_biltz_lumium = register("molten_biltz_lumium", 1800);
        public static FluidObject<ForgeFlowingFluid> molten_blizz_enderium = register("molten_blizz_enderium", 1800);
    }
    public class etshtinkerFluidCyclic {
        public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MOD_ID);

        private static FluidObject<ForgeFlowingFluid> register(String name, int temp) {
            return FLUIDS.register(name).type(FluidType.Properties.create().density(2000).viscosity(10000).temperature(temp).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_EMPTY_LAVA)).bucket().flowing();
        }
        public static FluidObject<ForgeFlowingFluid> molten_semicrystaline_obsidian = register("molten_semicrystaline_obsidian", 1080);
    }
    public class etshtinkerFluidAdastra {
        public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MOD_ID);

        private static FluidObject<ForgeFlowingFluid> register(String name, int temp) {
            return FLUIDS.register(name).type(FluidType.Properties.create().density(2000).viscosity(10000).temperature(temp).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_EMPTY_LAVA)).bucket().flowing();
        }
        public static FluidObject<ForgeFlowingFluid> molten_meteoralloy = register("molten_meteoralloy", 1800);//熔融星陨合金
        public static FluidObject<ForgeFlowingFluid> molten_stellaralloy = register("molten_stellaralloy", 1800);//熔融星钢
        public static FluidObject<ForgeFlowingFluid> molten_coronation = register("molten_coronation", 16384);//熔融星冕
    }
    public class etshtinkerFluidIE {
        public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MOD_ID);

        private static FluidObject<ForgeFlowingFluid> register(String name, int temp) {
            return FLUIDS.register(name).type(FluidType.Properties.create().density(2000).viscosity(10000).temperature(temp).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_EMPTY_LAVA)).bucket().flowing();
        }
        public static FluidObject<ForgeFlowingFluid> molten_bismuth = register("molten_bismuth", 600);//熔融铋
        public static FluidObject<ForgeFlowingFluid> molten_hardlead = register("molten_hardlead", 1800);
    }
    public class etshtinkerFluidAE {
        public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MOD_ID);

        private static FluidObject<ForgeFlowingFluid> register(String name, int temp) {
            return FLUIDS.register(name).type(FluidType.Properties.create().density(2000).viscosity(10000).temperature(temp).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_EMPTY_LAVA)).bucket().flowing();
        }
        public static FluidObject<ForgeFlowingFluid> molten_perfect = register("molten_perfect", 4500);
    }
    public class etshtinkerFluidMBOT {
        public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MOD_ID);

        private static FluidObject<ForgeFlowingFluid> register(String name, int temp) {
            return FLUIDS.register(name).type(FluidType.Properties.create().density(2000).viscosity(10000).temperature(temp).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_EMPTY_LAVA)).bucket().flowing();
        }
        public static FluidObject<ForgeFlowingFluid> molten_alfsteel = register("molten_alfsteel", 1200);
    }
    public class moltenExoAlloy{
        public static final FluidDeferredRegister FLUIDSa = new FluidDeferredRegister(MOD_ID);

        private static FluidObject<ForgeFlowingFluid> register(String name, int temp) {
            return FLUIDSa.register(name).type(FluidType.Properties.create().density(2000).viscosity(10000).temperature(temp).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_EMPTY_LAVA)).bucket().flowing();
        }
        public static FluidObject<ForgeFlowingFluid> molten_exo_alloy = register("molten_exo_alloy", 16384);//
    }
}