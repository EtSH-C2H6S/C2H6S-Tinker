package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
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
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.RequirementsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.BowAmmoModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.recipe.casting.material.MaterialCastingLookup;
import slimeknights.tconstruct.library.recipe.casting.material.MaterialFluidRecipe;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;
import slimeknights.tconstruct.library.tools.capability.PersistentDataCapability;
import slimeknights.tconstruct.library.tools.nbt.*;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.ToolDefinitions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper.TANK_HELPER;

public class PlasmaArrowModifier extends Modifier implements RequirementsModifierHook,BowAmmoModifierHook, ProjectileHitModifierHook, ProjectileLaunchModifierHook, TooltipModifierHook {

    @Override
    public int getPriority() {
        return 25600;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this,ModifierHooks.REQUIREMENTS,ModifierHooks.BOW_AMMO,ModifierHooks.PROJECTILE_LAUNCH,ModifierHooks.PROJECTILE_HIT,ModifierHooks.TOOLTIP);
    }

    @Override
    public List<ModifierEntry> displayModifiers(ModifierEntry entry) {
        return List.of(new ModifierEntry(new ModifierId("tconstruct:tank"),1));
    }

    @Override
    public ItemStack findAmmo(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, ItemStack itemStack, Predicate<ItemStack> predicate) {
        if (!TANK_HELPER.getFluid(tool).isEmpty()){
            MaterialFluidRecipe recipe = getFluidMaterial(TANK_HELPER.getFluid(tool).getFluid());
            MaterialVariant variant = recipe.getOutput();
            if (!variant.isUnknown()&&!variant.isEmpty()){
                FluidStack fluidStack =TANK_HELPER.getFluid(tool);
                if (fluidStack.getAmount()>=10) {
                    ToolStack toolStack = ToolStack.createTool(TinkerTools.longbow.get(), ToolDefinitions.LONGBOW, new MaterialNBT(List.of(variant, variant, variant)));
                    for (ModifierEntry entry:toolStack.getModifierList()){
                        entry =new ModifierEntry(entry.getId(),modifiers.getLevel());
                        if (entry !=modifiers) {
                            itemStack = entry.getHook(ModifierHooks.BOW_AMMO).findAmmo(toolStack,entry,livingEntity,itemStack,predicate);
                        }
                    }
                }
            }
        }
        return itemStack;
    }

    @Override
    public void shrinkAmmo(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, ItemStack ammo, int needed) {
        if (!ammo.isEmpty()){
            FluidStack fluidStack =TANK_HELPER.getFluid(tool);
            if (!fluidStack.isEmpty()) {
                fluidStack.shrink(5);
                TANK_HELPER.setFluid(tool, fluidStack);
            }
        }
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean primary) {
        if (!TANK_HELPER.getFluid(tool).isEmpty()&&livingEntity!=null&&abstractArrow!=null){
            MaterialFluidRecipe recipe = getFluidMaterial(TANK_HELPER.getFluid(tool).getFluid());
            MaterialVariant variant = recipe.getOutput();
            if (!variant.isUnknown()&&!variant.isEmpty()){
                FluidStack fluidStack =TANK_HELPER.getFluid(tool);
                if (fluidStack.getAmount()>=5) {
                    ToolStack toolStack = ToolStack.createTool(TinkerTools.longbow.get(), ToolDefinitions.LONGBOW, new MaterialNBT(List.of(variant, variant, variant)));
                    abstractArrow.setBaseDamage(ConditionalStatModifierHook.getModifiedStat(toolStack, livingEntity, ToolStats.PROJECTILE_DAMAGE, (float) abstractArrow.getBaseDamage()));
                    ModifierNBT nbt = toolStack.getModifiers();
                    List<ModifierEntry> list =new ArrayList<>(List.of());
                    for (ModifierEntry entry:nbt.getModifiers()){
                        entry =new ModifierEntry(entry.getId(),modifiers.getLevel());
                        list.add(entry);
                    }
                    list.addAll(tool.getModifierList());
                    final ModifierNBT modifierNBT =new ModifierNBT(list);
                    abstractArrow.getCapability(EntityModifierCapability.CAPABILITY).ifPresent(cap -> cap.setModifiers(modifierNBT));
                    NamespacedNBT arrowData = PersistentDataCapability.getOrWarn(abstractArrow);
                    for (ModifierEntry entry1 : nbt.getModifiers()) {
                        entry1 =new ModifierEntry(entry1.getId(),modifiers.getLevel());
                        if (entry1 !=modifiers) {
                            entry1.getHook(ModifierHooks.PROJECTILE_LAUNCH).onProjectileLaunch(toolStack, entry1, livingEntity, abstractArrow, abstractArrow, arrowData, primary);
                        }
                    }
                }
                fluidStack.shrink(5);
                TANK_HELPER.setFluid(tool,fluidStack);
            }
        }
    }

    public static MaterialFluidRecipe getFluidMaterial(Fluid fluid){
        return MaterialCastingLookup.getCastingFluid(fluid);
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifierEntry, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (!TANK_HELPER.getFluid(tool).isEmpty()&&player!=null&&tooltipKey==TooltipKey.SHIFT){
            MaterialFluidRecipe recipe = getFluidMaterial(TANK_HELPER.getFluid(tool).getFluid());
            MaterialVariant variant = recipe.getOutput();
            if (!variant.isUnknown()&&!variant.isEmpty()){
                ToolStack toolStack =ToolStack.createTool(TinkerTools.longbow.get(), ToolDefinitions.LONGBOW,new MaterialNBT(List.of(variant,variant,variant)));
                tooltip.add(Component.translatable(  "etshtinker.tool.tooltip.fluid_contains_modifier").append(" : ").withStyle(ChatFormatting.BOLD).append(Component.translatable("material."+variant.getId().toLanguageKey())));
                for (ModifierEntry entry:toolStack.getModifierList()){
                    if (entry!=null){
                        entry =new ModifierEntry(entry.getId(),modifierEntry.getLevel());
                        Component component =entry.getDisplayName();
                        tooltip.add(component);
                    }
                }
            }
        }
    }
}
