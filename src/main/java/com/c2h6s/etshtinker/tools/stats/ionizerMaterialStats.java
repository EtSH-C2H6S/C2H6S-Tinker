package com.c2h6s.etshtinker.tools.stats;

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
import com.c2h6s.etshtinker.etshtinker;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;
import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public record ionizerMaterialStats(float cooldown, float baseRange, float damageMultiplier) implements IMaterialStats {
    public static final MaterialStatsId ID = new MaterialStatsId(MOD_ID, "ionizer");
    public static final MaterialStatType<ionizerMaterialStats> TYPE=new MaterialStatType(ID, new ionizerMaterialStats(0F, 0F,0f), RecordLoadable.create( FloatLoadable.ANY.defaultField("cooldown", 0.0F, true, ionizerMaterialStats::cooldown),FloatLoadable.ANY.defaultField("plasmarange", 1.0F, true, ionizerMaterialStats::baseRange),FloatLoadable.ANY.defaultField("damagemultiplier", 0.0F, true, ionizerMaterialStats::damageMultiplier), ionizerMaterialStats::new));;
    private static final String RANGE_PREFIX;
    private static final String COOLDOWM_PREFIX;
    private static final String MULTIPLYER_PREFIX;
    private static final List<Component> DESCRIPTION;

    public MaterialStatType<?> getType() {
        return TYPE;
    }
    public ionizerMaterialStats(float cooldown, float baseRange, float damageMultiplier) {
        this.baseRange = baseRange;
        this.cooldown = cooldown;
        this.damageMultiplier =damageMultiplier;
    }
    public MaterialStatsId getIdentifier() {
        return ID;
    }


    public List<Component> getLocalizedInfo() {
        List<Component> info = Lists.newArrayList();
        info.add(IToolStat.formatColoredBonus(RANGE_PREFIX, this.baseRange));
        info.add(IToolStat.formatColoredBonus(COOLDOWM_PREFIX, this.cooldown));
        info.add(IToolStat.formatColoredBonus(MULTIPLYER_PREFIX, this.damageMultiplier));
        return info;
    }


    public List<Component> getLocalizedDescriptions() {
        return DESCRIPTION;
    }


    public void apply(ModifierStatsBuilder builder, float v) {
        etshtinkerToolStats.COOLDOWN.update(builder,this.cooldown);
        etshtinkerToolStats.PLASMARANGE.update(builder,this.baseRange);
        etshtinkerToolStats.DAMAGEMULTIPLIER.update(builder,this.damageMultiplier);
        ToolStats.DURABILITY.update(builder,(float)Integer.MAX_VALUE);
    }

    public float baseRange() {
        return this.baseRange;
    }
    public float cooldown(){
        return this.cooldown;
    }
    public float damageMultiplier(){
        return this.damageMultiplier;
    }

    static {
        COOLDOWM_PREFIX = IMaterialStats.makeTooltipKey(etshtinker.getResourceLoc("cooldown"));
        RANGE_PREFIX = IMaterialStats.makeTooltipKey(etshtinker.getResourceLoc("plasmarange"));
        MULTIPLYER_PREFIX = IMaterialStats.makeTooltipKey(etshtinker.getResourceLoc("damagemultiplier"));
        DESCRIPTION = ImmutableList.of(IMaterialStats.makeTooltip(etshtinker.getResourceLoc("ionizer.plasmarange.description")), IMaterialStats.makeTooltip(etshtinker.getResourceLoc("ionizer.cooldown.description")),IMaterialStats.makeTooltip(etshtinker.getResourceLoc("ionizer.damagemultiplier.description")));
    }
}
