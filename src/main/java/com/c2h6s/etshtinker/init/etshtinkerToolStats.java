package com.c2h6s.etshtinker.init;

import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class etshtinkerToolStats {
    public static final FloatToolStat ENERGY_STORE = (FloatToolStat) ToolStats.register(new FloatToolStat(name("energy_capacity"), -3135232, 0.0F, 0.0F, Float.MAX_VALUE));
    public static final FloatToolStat SCATTER = (FloatToolStat) ToolStats.register(new FloatToolStat(name("scatter"), -3135232, 0.0F, 0.0F, 89));
    public static final FloatToolStat MULTIPLASMA = (FloatToolStat) ToolStats.register(new FloatToolStat(name("multishotplasma"), -3135232, 0.0F, 0.0F, 10));
    public static final FloatToolStat COOLDOWN = (FloatToolStat) ToolStats.register(new FloatToolStat(name("cooldown"), -3135232, 0.0F, 0.0F, 2048));
    public static final FloatToolStat PLASMARANGE = (FloatToolStat) ToolStats.register(new FloatToolStat(name("plasmarange"), -3135232, 1.0F, 0.0F, 256));
    public static final FloatToolStat DAMAGEMULTIPLIER = (FloatToolStat) ToolStats.register(new FloatToolStat(name("damagemultiplier"), -3135232, 0.0F, 0.0F, Integer.MAX_VALUE));
    public static final FloatToolStat FLUIDMULTIPLIER = (FloatToolStat) ToolStats.register(new FloatToolStat(name("fluidmultiplier"), -3135232, 1.0F, 0.0F, Integer.MAX_VALUE));
    public static final FloatToolStat SCALE = (FloatToolStat) ToolStats.register(new FloatToolStat(name("scale"), -3135232, 1.0F, 0.0F, Integer.MAX_VALUE));
    public static final FloatToolStat FLUID_EFFICIENCY = (FloatToolStat) ToolStats.register(new FloatToolStat(name("fluid_efficiency"), -3135232, 1.0F, 0.0F, 100));
    public static final FloatToolStat CRITICAL_RATE = (FloatToolStat) ToolStats.register(new FloatToolStat(name("critical_rate"), -3135232, 0.0F, 0.0F, 10f));
    public static final FloatToolStat SLASH_COLOR =(FloatToolStat) ToolStats.register(new FloatToolStat(name("slash_color"), -3135232, 0.0F, 0.0F, 11));

    private static ToolStatId name(String name) {
        return new ToolStatId(MOD_ID, name);
    }
}
