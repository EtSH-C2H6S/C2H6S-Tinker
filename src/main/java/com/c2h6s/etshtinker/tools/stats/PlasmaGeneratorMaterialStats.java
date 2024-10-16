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
import static com.c2h6s.etshtinker.util.SlashColor.getSlashColorStr;

public record PlasmaGeneratorMaterialStats(float damageMultiplier, float criticalRate, float fluidEfficiency,float slashColor) implements IMaterialStats{
    public static final MaterialStatsId ID = new MaterialStatsId(MOD_ID, "plasma_generator");
    public static final MaterialStatType<PlasmaGeneratorMaterialStats> TYPE=new MaterialStatType(ID, new PlasmaGeneratorMaterialStats(0F, 0F,1f,0f), RecordLoadable.create( FloatLoadable.ANY.defaultField("damagemultiplier", 0.0F, true, PlasmaGeneratorMaterialStats::damageMultiplier),FloatLoadable.ANY.defaultField("criticalrate", 0.0F, true, PlasmaGeneratorMaterialStats::criticalRate),FloatLoadable.ANY.defaultField("fluidefficiency", 1.0F, true, PlasmaGeneratorMaterialStats::fluidEfficiency),FloatLoadable.ANY.defaultField("slash_color", 0.0F, true, PlasmaGeneratorMaterialStats::slashColor), PlasmaGeneratorMaterialStats::new));
    private static final String DAMAGE_PREFIX;
    private static final String CRITICAL_PREFIX;
    private static final String EFFICIENCY_PREFIX;
    private static final String COLOR_PREFIX;
    private static final List<Component> DESCRIPTION;

    public MaterialStatType<?> getType() {
        return TYPE;
    }
    public PlasmaGeneratorMaterialStats(float damageMultiplier, float criticalRate, float fluidEfficiency,float slashColor) {
        this.fluidEfficiency = fluidEfficiency;
        this.criticalRate = criticalRate;
        this.damageMultiplier =damageMultiplier;
        this.slashColor=slashColor;
    }
    public MaterialStatsId getIdentifier() {
        return ID;
    }


    public List<Component> getLocalizedInfo() {
        List<Component> info = Lists.newArrayList();
        info.add(IToolStat.formatColoredBonus(DAMAGE_PREFIX, this.damageMultiplier));
        info.add(IToolStat.formatColoredBonus(CRITICAL_PREFIX, this.criticalRate));
        info.add(IToolStat.formatColoredBonus(EFFICIENCY_PREFIX, this.fluidEfficiency));
        info.add(Component.translatable("etshtinker.tooltip.slashcolor_index").append(":").append(Component.translatable(getSlashColorStr((int) this.slashColor))));
        return info;
    }


    public List<Component> getLocalizedDescriptions() {
        return DESCRIPTION;
    }


    public void apply(ModifierStatsBuilder builder, float v) {
        etshtinkerToolStats.CRITICAL_RATE.update(builder,this.criticalRate);
        etshtinkerToolStats.FLUID_EFFICIENCY.update(builder,this.fluidEfficiency);
        etshtinkerToolStats.DAMAGEMULTIPLIER.update(builder,this.damageMultiplier);
        etshtinkerToolStats.SLASH_COLOR.update(builder,this.slashColor);
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
    public float slashColor(){
        return this.slashColor;
    }

    static {
        DAMAGE_PREFIX = IMaterialStats.makeTooltipKey(etshtinker.getResourceLoc("damagemultiplier"));
        CRITICAL_PREFIX = IMaterialStats.makeTooltipKey(etshtinker.getResourceLoc("criticalrate"));
        EFFICIENCY_PREFIX = IMaterialStats.makeTooltipKey(etshtinker.getResourceLoc("fluidefficiency"));
        COLOR_PREFIX = IMaterialStats.makeTooltipKey(etshtinker.getResourceLoc("slashcolor"));
        DESCRIPTION = ImmutableList.of(IMaterialStats.makeTooltip(etshtinker.getResourceLoc("plasma_generator.damagemultiplier.description")), IMaterialStats.makeTooltip(etshtinker.getResourceLoc("plasma_generator.criticalrate.description")),IMaterialStats.makeTooltip(etshtinker.getResourceLoc("plasma_generator.fluidefficiency.description")),IMaterialStats.makeTooltip(etshtinker.getResourceLoc("plasma_generator.slashcolor.description")));
    }
}
