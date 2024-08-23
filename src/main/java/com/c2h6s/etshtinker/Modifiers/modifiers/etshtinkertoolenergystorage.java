package com.c2h6s.etshtinker.Modifiers.modifiers;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ValidateModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.*;
import java.util.List;
import com.c2h6s.etshtinker.init.etshtinkerToolStats;

public class etshtinkertoolenergystorage extends etshmodifieriii implements VolatileDataModifierHook, ValidateModifierHook {
    public etshtinkertoolenergystorage() {
    }

    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.VALIDATE, ModifierHooks.VOLATILE_DATA, ModifierHooks.REMOVE);
    }

    public Component validate(IToolStackView tool, ModifierEntry modifier) {
        int max = tool.getVolatileData().getInt(etshmodifierfluxed.MAX_ENERGY);
        if (tool.getPersistentData().getInt(etshmodifierfluxed.STORED_ENERGY) > max) {
            tool.getPersistentData().putInt(etshmodifierfluxed.STORED_ENERGY, max);
        }
        return null;
    }

    public Component onRemoved(IToolStackView tool, Modifier modifier) {
        if (tool.getVolatileData().getInt(etshmodifierfluxed.MAX_ENERGY) == 0) {
            tool.getPersistentData().remove(etshmodifierfluxed.STORED_ENERGY);
        }
        return null;
    }

    public void addVolatileData(IToolContext context, ModifierEntry modifier, ModDataNBT volatileData) {
        if (volatileData.contains(etshmodifierfluxed.MAX_ENERGY, 3)) {
            volatileData.putInt(etshmodifierfluxed.MAX_ENERGY, volatileData.getInt(etshmodifierfluxed.MAX_ENERGY) + this.getCapacity(context, modifier, volatileData) * modifier.getLevel());
        } else {
            volatileData.putInt(etshmodifierfluxed.MAX_ENERGY, this.getCapacity(context, modifier, volatileData) * modifier.getLevel());
        }

        if (!volatileData.contains(etshmodifierfluxed.ENERGY_OWNER, 8)) {
            volatileData.putString(etshmodifierfluxed.ENERGY_OWNER, this.getId().toString());
        }
    }

    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (tool instanceof ToolStack && this.isOwner(tool.getVolatileData())) {
            int energy_store = tool.getStats().getInt(etshtinkerToolStats.ENERGY_STORE);
            if (energy_store > 0) {
                list.add(Component.translatable("modifier.etshtinker.tooltip.storedenergy").append( String.valueOf( tool.getPersistentData().getInt(etshmodifierfluxed.STORED_ENERGY))+"/"+String.valueOf( tool.getVolatileData().getInt(etshmodifierfluxed.MAX_ENERGY)+energy_store)).withStyle(this.getDisplayName().getStyle()));
            }
            else {
                list.add(Component.translatable("modifier.etshtinker.tooltip.storedenergy").append( String.valueOf( tool.getPersistentData().getInt(etshmodifierfluxed.STORED_ENERGY))+"/"+String.valueOf( tool.getVolatileData().getInt(etshmodifierfluxed.MAX_ENERGY))).withStyle(this.getDisplayName().getStyle()));
            }
        }
    }

    public int getCapacity(IToolContext context, ModifierEntry modifier, ModDataNBT volatileData) {
        return 100000;
    }

    public boolean isOwner(IModDataView volatileData) {
        return this.getId().toString().equals(volatileData.getString(etshmodifierfluxed.ENERGY_OWNER));
    }



}
