package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerToolStats;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

public class beconcerted extends etshmodifieriii implements ToolStatsModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.TOOL_STATS);
    }
    public boolean isNoLevels() {
        return true;
    }

    public final List<FloatToolStat> toolStats =List.of(
            ToolStats.DURABILITY,
            ToolStats.BLOCK_ANGLE,
            ToolStats.ATTACK_DAMAGE,
            ToolStats.ATTACK_SPEED,
            ToolStats.KNOCKBACK_RESISTANCE,
            ToolStats.ARMOR,
            ToolStats.PROJECTILE_DAMAGE,
            ToolStats.ACCURACY,
            ToolStats.ARMOR_TOUGHNESS,
            ToolStats.DRAW_SPEED,
            ToolStats.BLOCK_AMOUNT,
            ToolStats.MINING_SPEED,
            etshtinkerToolStats.DAMAGEMULTIPLIER,
            etshtinkerToolStats.ENERGY_STORE
    );
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        float multiplier = ModList.get().size()*0.062134f+1;
        for (FloatToolStat toolStat:toolStats){
            toolStat.multiply(builder,multiplier);
        }
    }

    public Component getDisplayName(IToolStackView tool, ModifierEntry entry) {
        float multiplier = ModList.get().size()*6.2134f;
        return (Component.translatable(this.getDisplayName().getString()).append(" ").append(Component.translatable("etshtinker.modifier.tooltip.multi")).append(":"+String.format("%.01f", multiplier)+"%").withStyle(this.getDisplayName().getStyle()));
    }
}
