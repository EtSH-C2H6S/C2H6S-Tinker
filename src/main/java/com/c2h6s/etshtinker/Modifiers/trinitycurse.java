package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.util.C;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.DisplayNameModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.utils.RomanNumeralHelper;

import java.util.List;

public class trinitycurse extends etshmodifieriii implements VolatileDataModifierHook, DisplayNameModifierHook {
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.VOLATILE_DATA,ModifierHooks.DISPLAY_NAME);
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (modifier.getLevel()>0&&isCorrectSlot&&holder!=null) {
            Vec3 v =holder.getDeltaMovement();
            if (v.y>0){
                holder.setDeltaMovement(v.x,v.y*0.75,v.z);
            }
            if (holder.invulnerableTime > 0) {
                holder.invulnerableTime--;
                if (modifier.getLevel() == 3 && holder.invulnerableTime > 0) {
                    holder.invulnerableTime = 0;
                }
            }
        }
    }

    @Override
    public void addVolatileData(IToolContext context, ModifierEntry modifier, ModDataNBT volatileData) {
        volatileData.setSlots(SlotType.ABILITY,-10);
        volatileData.setSlots(SlotType.UPGRADE,-10);
        volatileData.setSlots(SlotType.SOUL,-10);
        volatileData.setSlots(SlotType.DEFENSE,-10);
    }

    @Override
    public Component getDisplayName(IToolStackView iToolStackView, ModifierEntry modifierEntry, Component component) {
        return Component.literal(C.GetColorT(Component.translatable(this.getDisplayName().getString()).toString())).append(" ").append(RomanNumeralHelper.getNumeral(modifierEntry.getLevel()));
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> list, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player != null) {
            list.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.trinitycurse")));
        }
        super.addTooltip(tool,modifier,player,list,TooltipKey.NORMAL,tooltipFlag);
    }
}
