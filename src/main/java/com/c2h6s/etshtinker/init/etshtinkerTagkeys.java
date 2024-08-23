package com.c2h6s.etshtinker.init;

import com.c2h6s.etshtinker.etshtinker;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class etshtinkerTagkeys {
    public static class fluid {
        private static TagKey<Fluid> tag(String name) {
            return TagKey.create(Registry.FLUID_REGISTRY, etshtinker.getResourceLoc(name));
        }
        public static final TagKey<Fluid> IONIZER_PURPLE = tag("ionizer/color/purple");
        public static final TagKey<Fluid> IONIZER_BLUE = tag("ionizer/color/blue");
        public static final TagKey<Fluid> IONIZER_CYAN = tag("ionizer/color/cyan");
        public static final TagKey<Fluid> IONIZER_GREEN = tag("ionizer/color/green");
        public static final TagKey<Fluid> IONIZER_YELLOW = tag("ionizer/color/yellow");
        public static final TagKey<Fluid> IONIZER_ORANGE = tag("ionizer/color/orange");
        public static final TagKey<Fluid> IONIZER_RED = tag("ionizer/color/red");
    }
}
