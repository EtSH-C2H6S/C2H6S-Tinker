package com.c2h6s.etshtinker.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class etshtinkerConfig {
    public static final CommonConfig COMMON;
    public static ForgeConfigSpec config;

    static {
        Pair<etshtinkerConfig.CommonConfig, ForgeConfigSpec> pair = (new ForgeConfigSpec.Builder()).configure(CommonConfig::new);
        COMMON = pair.getLeft();
        config = pair.getRight();
    }

    public etshtinkerConfig() {
    }

    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, config);
    }



    public static class CommonConfig {
        //public final ForgeConfigSpec.BooleanValue EnableExoAlloy;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            //EnableExoAlloy = builder.comment("Enable exo_alloy item when is true.").define("enable_exoalloy_recipe",false);
        }
    }
}
