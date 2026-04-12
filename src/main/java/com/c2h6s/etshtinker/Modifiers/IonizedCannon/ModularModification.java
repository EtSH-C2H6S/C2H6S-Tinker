package com.c2h6s.etshtinker.Modifiers.IonizedCannon;

import blusunrize.immersiveengineering.common.register.IEItems;
import com.c2h6s.etshtinker.etshtinker;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import com.c2h6s.etshtinker.tools.stats.fluidChamberMaterialStats;
import com.c2h6s.etshtinker.tools.stats.ionizerMaterialStats;
import com.hoshino.cti.library.modifier.CtiModifierHook;
import com.hoshino.cti.library.modifier.hooks.SlotStackModifierHook;
import com.hoshino.cti.util.CommonUtil;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierTraitHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.part.IToolPart;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

public class ModularModification extends NoLevelsModifier implements SlotStackModifierHook, ToolStatsModifierHook, ModifierTraitHook, TooltipModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.TOOL_STATS,ModifierHooks.MODIFIER_TRAITS, CtiModifierHook.SLOT_STACK,ModifierHooks.TOOLTIP);
    }
    public static final ResourceLocation KEY_MODULAR_PART = etshtinker.getResourceLoc("modular_part");

    @Override
    public void addTraits(IToolContext iToolContext, ModifierEntry modifierEntry, TraitBuilder traitBuilder, boolean b) {
        var data = iToolContext.getPersistentData();
        var originalItem = ItemStack.EMPTY;
        if (data.contains(KEY_MODULAR_PART, Tag.TAG_COMPOUND))
            originalItem = ItemStack.of(data.getCompound(KEY_MODULAR_PART));
        if (!originalItem.isEmpty()&&originalItem.getItem() instanceof IToolPart part){
            var material = part.getMaterial(originalItem).getId();
            var statType = part.getStatType();
            var modifiers = MaterialRegistry.getInstance().getTraits(material,statType);
            traitBuilder.add(modifiers);
        }
    }

    @Override
    public void addToolStats(IToolContext iToolContext, ModifierEntry modifierEntry, ModifierStatsBuilder modifierStatsBuilder) {
        var data = iToolContext.getPersistentData();
        var originalItem = ItemStack.EMPTY;
        if (data.contains(KEY_MODULAR_PART, Tag.TAG_COMPOUND))
            originalItem = ItemStack.of(data.getCompound(KEY_MODULAR_PART));
        if (!originalItem.isEmpty()&&originalItem.getItem() instanceof IToolPart part) {
            ToolStats.ATTACK_SPEED.multiply(modifierStatsBuilder,0.75);
            var material = part.getMaterial(originalItem).getId();
            var statType = part.getStatType();
            if (statType== fluidChamberMaterialStats.ID){
                MaterialRegistry.getInstance().getMaterialStats(material,statType).ifPresentOrElse(iMaterialStats ->
                        iMaterialStats.apply(modifierStatsBuilder,0.25f),()->
                        ToolTankHelper.CAPACITY_STAT.add(modifierStatsBuilder,1250));
            } else if (statType== ionizerMaterialStats.ID){
                MaterialRegistry.getInstance().getMaterialStats(material,statType).ifPresent(iMaterialStats ->
                        iMaterialStats.apply(modifierStatsBuilder,0.25f));
            }
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(IToolStackView slotTool, ModifierEntry modifier, ItemStack held, Slot slot, Player player, SlotAccess access) {
        var item = held.getItem();
        if (player instanceof ServerPlayer) {
            if (item instanceof IToolPart && (held.is(etshtinkerItems.ionizer.get()) || held.is(etshtinkerItems.fluid_chamber.get()))) {
                var data = slotTool.getPersistentData();
                var originalItem = ItemStack.EMPTY;
                if (data.contains(KEY_MODULAR_PART, Tag.TAG_COMPOUND))
                    originalItem = ItemStack.of(data.getCompound(KEY_MODULAR_PART));
                if (!originalItem.isEmpty())
                    if (!player.addItem(originalItem))
                        player.drop(originalItem, true);
                var toInstall = held.copy();
                toInstall.setCount(1);
                held.shrink(1);
                data.put(KEY_MODULAR_PART, toInstall.serializeNBT());
                ((ToolStack)slotTool).rebuildStats();
                return true;
            }
            else if (held.is(IEItems.Tools.HAMMER.get())){
                var data = slotTool.getPersistentData();
                var originalItem = ItemStack.EMPTY;
                if (data.contains(KEY_MODULAR_PART, Tag.TAG_COMPOUND))
                    originalItem = ItemStack.of(data.getCompound(KEY_MODULAR_PART));
                if (!originalItem.isEmpty())
                    if (!player.addItem(originalItem))
                        player.drop(originalItem, true);
                data.remove(KEY_MODULAR_PART);
                ((ToolStack)slotTool).rebuildStats();
                return true;
            }
        }
        return false;
    }


    @Override
    public void addTooltip(IToolStackView iToolStackView, ModifierEntry modifierEntry, @Nullable Player player, List<Component> list, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        var data = iToolStackView.getPersistentData();
        var originalItem = ItemStack.EMPTY;
        if (data.contains(KEY_MODULAR_PART, Tag.TAG_COMPOUND))
            originalItem = ItemStack.of(data.getCompound(KEY_MODULAR_PART));
        if (!originalItem.isEmpty()) {
            list.add(Component.translatable("etshtinker.item.tooltip.modular_modification").withStyle(style -> {
                style.withBold(true);
                style.withItalic(true);
                style.withColor(0x934FFF);
                return style;
            }));
            list.add(Component.literal("  >>").append( originalItem.getDisplayName()));
        }
    }
}
