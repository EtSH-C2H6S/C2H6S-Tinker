package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.init.etshtinkerModifiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.materials.definition.MaterialVariant;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierTraitHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.recipe.casting.material.MaterialCastingLookup;
import slimeknights.tconstruct.library.recipe.casting.material.MaterialFluidRecipe;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.*;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.ToolDefinitions;

import java.util.ArrayList;
import java.util.List;

import static slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper.TANK_HELPER;

public class PlasmaSputtering extends Modifier implements MeleeHitModifierHook , TooltipModifierHook,ModifierTraitHook {

    @Override
    public int getPriority() {
        return 25600;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.MELEE_HIT,ModifierHooks.TOOLTIP,ModifierHooks.MODIFIER_TRAITS);
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (ValidateFluid((ToolStack) tool, modifier.getLevel())) {
            FluidStack fluidStack = TANK_HELPER.getFluid(tool);
            fluidStack.shrink(5 * modifier.getLevel());
            TANK_HELPER.setFluid(tool, fluidStack);
        }
    }

    public static boolean ValidateFluid(ToolStack toolStack,int level){
        MaterialVariant variant = MaterialVariant.UNKNOWN;
        if (!TANK_HELPER.getFluid(toolStack).isEmpty()){
            MaterialFluidRecipe recipe = getFluidMaterial(TANK_HELPER.getFluid(toolStack).getFluid());
            variant = recipe.getOutput();
        }
        return !variant.isUnknown()&&!variant.isEmpty()&&!getFluidModifiers(toolStack,level).isEmpty();
    }

    public static MaterialFluidRecipe getFluidMaterial(Fluid fluid){
        return MaterialCastingLookup.getCastingFluid(fluid);
    }
    public static List<ModifierEntry> getFluidModifiers(ToolStack toolStack, int level){
        List<ModifierEntry> list = new ArrayList<>(List.of());
        if (!TANK_HELPER.getFluid(toolStack).isEmpty()) {
            MaterialFluidRecipe recipe = getFluidMaterial(TANK_HELPER.getFluid(toolStack).getFluid());
            MaterialVariant variant = recipe.getOutput();
            if (!variant.isUnknown()&&!variant.isEmpty()) {
                FluidStack fluidStack = TANK_HELPER.getFluid(toolStack);
                if (fluidStack.getAmount() >= 20) {
                    ToolStack tool = ToolStack.createTool(TinkerTools.sword.get(), ToolDefinitions.SWORD, new MaterialNBT(List.of(variant, variant, variant)));
                    ToolStack slotValidate = toolStack.copy();
                    for (ModifierEntry entry1 : tool.getModifierList()) {
                        slotValidate.addModifier(entry1.getId(),entry1.getLevel());
                    }
                    for (SlotType slotType:SlotType.getAllSlotTypes()) {
                        if (slotValidate.getVolatileData().getSlots(slotType) != toolStack.getVolatileData().getSlots(slotType)) {
                            return list;
                        }
                    }
                    for (ModifierEntry entry1 : tool.getModifierList()) {
                        if (entry1.getModifier()!= etshtinkerModifiers.godlymetal_STATIC_MODIFIER.get()&&entry1.getModifier()!= TinkerModifiers.silkyShears.get()) {
                            entry1 = new ModifierEntry(entry1.getId(), level);
                            list.add(entry1);
                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifierEntry, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (!TANK_HELPER.getFluid(tool).isEmpty()&&player!=null&&tooltipKey==TooltipKey.SHIFT){
            MaterialFluidRecipe recipe = getFluidMaterial(TANK_HELPER.getFluid(tool).getFluid());
            MaterialVariant variant = recipe.getOutput();
            List<ModifierEntry> list =getFluidModifiers((ToolStack) tool,modifierEntry.getLevel());
            if (!list.isEmpty()){
                tooltip.add(Component.translatable(  "etshtinker.tool.tooltip.fluid_contains_modifier").withStyle(ChatFormatting.LIGHT_PURPLE).append(" : ").withStyle(ChatFormatting.BOLD).append(Component.translatable("material."+variant.getId().toLanguageKey())));
                for (ModifierEntry entry:list){
                    if (entry!=null){
                        entry =new ModifierEntry(entry.getId(),modifierEntry.getLevel());
                        Component component =entry.getDisplayName();
                        tooltip.add(component);
                    }
                }
            }else {
                if (!variant.isEmpty()&&!variant.isUnknown()){
                    tooltip.add(Component.translatable("etshtinker.tool.tooltip.material_unsupport").withStyle(ChatFormatting.RED).append(" : ").append(Component.translatable("material."+variant.getId().toLanguageKey())));
                }
                else tooltip.add(Component.translatable("etshtinker.tool.tooltip.fluid_not_modifier_melee").withStyle(ChatFormatting.GOLD));
            }
        }
    }

    @Override
    public void addTraits(IToolContext iToolContext, ModifierEntry modifierEntry, ModifierTraitHook.TraitBuilder traitBuilder, boolean b) {
        traitBuilder.add(new ModifierEntry(new ModifierId("tconstruct:tank"),modifierEntry.getLevel()*10));
    }
}
