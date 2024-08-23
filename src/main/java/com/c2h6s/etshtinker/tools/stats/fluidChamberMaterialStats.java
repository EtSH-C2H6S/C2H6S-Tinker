package com.c2h6s.etshtinker.tools.stats;

import com.c2h6s.etshtinker.etshtinker;
import com.c2h6s.etshtinker.init.etshtinkerToolStats;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.network.chat.Component;
import slimeknights.mantle.data.loadable.primitive.FloatLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper;
import slimeknights.tconstruct.library.tools.stat.IToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public record fluidChamberMaterialStats(float fluidCapacity) implements IMaterialStats {
    public static final MaterialStatsId ID = new MaterialStatsId(MOD_ID, "fluid_chamber");
    public static final MaterialStatType<ionizerMaterialStats> TYPE = new MaterialStatType(ID, new fluidChamberMaterialStats(0F), RecordLoadable.create(FloatLoadable.ANY.defaultField("fluid_capacity", 1000.0F, true, fluidChamberMaterialStats::fluidCapacity), fluidChamberMaterialStats::new));
    private static final String FLUID_PREFIX = IMaterialStats.makeTooltipKey(etshtinker.getResourceLoc("fluid_capacity"));

    private static final List<Component> DESCRIPTION= ImmutableList.of(IMaterialStats.makeTooltip(etshtinker.getResourceLoc("fluid_chamber.fluid_capacity.description")));;

    public MaterialStatType<?> getType() {
        return TYPE;
    }

    public MaterialStatsId getIdentifier() {
        return ID;
    }

    public fluidChamberMaterialStats(float fluidCapacity) {
        this.fluidCapacity = fluidCapacity;
    }

    public List<Component> getLocalizedInfo() {
        List<Component> info = Lists.newArrayList();
        info.add(IToolStat.formatColoredBonus(FLUID_PREFIX, this.fluidCapacity));
        return info;
    }

    public List<Component> getLocalizedDescriptions() {
        return DESCRIPTION;
    }

    public void apply(ModifierStatsBuilder builder, float v) {
        ToolTankHelper.CAPACITY_STAT.update(builder,this.fluidCapacity);
    }

    public float fluidCapacity() {
        return this.fluidCapacity;
    }

}
