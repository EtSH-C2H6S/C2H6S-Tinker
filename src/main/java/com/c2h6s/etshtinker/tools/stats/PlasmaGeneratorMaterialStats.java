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
import slimeknights.tconstruct.library.tools.stat.IToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public record PlasmaGeneratorMaterialStats(float damageMultiplier, float criticalRate, float fluidEfficiency) {
    public static final MaterialStatsId ID = new MaterialStatsId(MOD_ID, "plasma_generator");
    public static final MaterialStatType<ionizerMaterialStats> TYPE=new MaterialStatType(ID, new ionizerMaterialStats(0F, 0F,0f), RecordLoadable.create( FloatLoadable.ANY.defaultField("cooldown", 0.0F, true, ionizerMaterialStats::cooldown),FloatLoadable.ANY.defaultField("plasmarange", 1.0F, true, ionizerMaterialStats::baseRange),FloatLoadable.ANY.defaultField("damagemultiplier", 0.0F, true, ionizerMaterialStats::damageMultiplier), ionizerMaterialStats::new));;
    private static final String DAMAGE_PREFIX;
    private static final String CRITICAL_PREFIX;
    private static final String EFFICIENCY_PREFIX;
    private static final List<Component> DESCRIPTION;

    public MaterialStatType<?> getType() {
        return TYPE;
    }
    public PlasmaGeneratorMaterialStats(float damageMultiplier, float criticalRate, float fluidEfficiency) {
        this.fluidEfficiency = fluidEfficiency;
        this.criticalRate = criticalRate;
        this.damageMultiplier =damageMultiplier;
    }
    public MaterialStatsId getIdentifier() {
        return ID;
    }


    public List<Component> getLocalizedInfo() {
        List<Component> info = Lists.newArrayList();
        info.add(IToolStat.formatColoredBonus(DAMAGE_PREFIX, this.damageMultiplier));
        info.add(IToolStat.formatColoredBonus(CRITICAL_PREFIX, this.criticalRate));
        info.add(IToolStat.formatColoredBonus(EFFICIENCY_PREFIX, this.fluidEfficiency));
        return info;
    }


    public List<Component> getLocalizedDescriptions() {
        return DESCRIPTION;
    }


    public void apply(ModifierStatsBuilder builder, float v) {
        etshtinkerToolStats.CRITICAL_RATE.update(builder,this.criticalRate);
        etshtinkerToolStats.FLUID_EFFICIENCY.update(builder,this.fluidEfficiency);
        etshtinkerToolStats.DAMAGEMULTIPLIER.update(builder,this.damageMultiplier);
        ToolStats.DURABILITY.update(builder,(float)Integer.MAX_VALUE);
    }

    public float damageMultiplier() {
        return this.damageMultiplier;
    }
    public float criticalRate(){
        return this.criticalRate;
    }
    public float fluidEfficiency(){
        return this.fluidEfficiency;
    }

    static {
        DAMAGE_PREFIX = IMaterialStats.makeTooltipKey(etshtinker.getResourceLoc("fluidefficiency"));
        CRITICAL_PREFIX = IMaterialStats.makeTooltipKey(etshtinker.getResourceLoc("criticalrate"));
        EFFICIENCY_PREFIX = IMaterialStats.makeTooltipKey(etshtinker.getResourceLoc("damagemultiplier"));
        DESCRIPTION = ImmutableList.of(IMaterialStats.makeTooltip(etshtinker.getResourceLoc("plasma_generator.damagemultiplier.description")), IMaterialStats.makeTooltip(etshtinker.getResourceLoc("plasma_generator.criticalrate.description")),IMaterialStats.makeTooltip(etshtinker.getResourceLoc("plasma_generator.fluidefficiency.description")));
    }
}
