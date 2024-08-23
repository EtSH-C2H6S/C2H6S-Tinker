package com.c2h6s.etshtinker.Modifiers;


import com.c2h6s.etshtinker.Modifiers.modifiers.etshtinkertoolenergystorage;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.DurabilityDisplayModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifierfluxed;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;



public class fluxloaded extends etshtinkertoolenergystorage implements DurabilityDisplayModifierHook, ToolDamageModifierHook {

    public int getCapacity(IToolContext context, ModifierEntry modifier, ModDataNBT volatileData) {
        return 1000000;
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.DURABILITY_DISPLAY,ModifierHooks.TOOL_DAMAGE);
    }

    public Boolean showDurabilityBar(IToolStackView tool, ModifierEntry modifier) {
        if (etshmodifierfluxed.getEnergyStored(tool)>0) {
            return true;
        }
        return tool.getDamage()>0;
    }
    public int getDurabilityWidth(IToolStackView tool, ModifierEntry modifier) {
        int max = tool.getStats().getInt(ToolStats.DURABILITY);
        int amount =tool.getCurrentDurability();
        if (etshmodifierfluxed.getEnergyStored(tool)>0&&etshmodifierfluxed.getMaxEnergyStored(tool)>0) {
            return Math.min((int) (13 *  ( (float) etshmodifierfluxed.getEnergyStored(tool)/ etshmodifierfluxed.getMaxEnergyStored(tool)))+1,13);
        }
        return amount >= max ? 13 : 1 + 13 * (amount - 1) / max;
    }
    public int getDurabilityRGB(IToolStackView tool, ModifierEntry modifier) {
        return etshmodifierfluxed.getEnergyStored(tool)>0 ? 0xFF4A30 : - 1;
    }

    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifierEntry, int amount, @Nullable LivingEntity livingEntity) {
        if (etshmodifierfluxed.getEnergyStored(tool) > 1000*amount) {
            etshmodifierfluxed.removeEnergy(tool, 1000*amount, false, false);
            return 0;
        }
        return amount;
    }
}
