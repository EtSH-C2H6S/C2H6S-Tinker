package com.c2h6s.etshtinker.init;

import com.c2h6s.etshtinker.etshtinker;
import com.c2h6s.etshtinker.hooks.*;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHook;

public class etshtinkerHook {
    public static final ModuleHook<PlasmaSlashCreateModifierHook> SLASH_CREATE = ModifierHooks.register(etshtinker.getResourceLoc("slash_create"), PlasmaSlashCreateModifierHook.class, PlasmaSlashCreateModifierHook.AllMerger::new, (tool, fluidStack, player, slash)-> slash);
    public static final ModuleHook<AfterPlasmaSlashHitModifierHook> AFTER_SLASH_HIT = ModifierHooks.register(etshtinker.getResourceLoc("after_slash_hit"), AfterPlasmaSlashHitModifierHook.class, AfterPlasmaSlashHitModifierHook.AllMerger::new, new AfterPlasmaSlashHitModifierHook() {});
    public static final ModuleHook<BeforePlasmaSlashHitModifierHook> BEFORE_SLASH_HIT = ModifierHooks.register(etshtinker.getResourceLoc("before_slash_hit"), BeforePlasmaSlashHitModifierHook.class, BeforePlasmaSlashHitModifierHook.AllMerger::new, new BeforePlasmaSlashHitModifierHook() {});
    public static final ModuleHook<PlasmaExplosionCreateModifierHook> PLASMA_EXPLOSION_CREATE = ModifierHooks.register(etshtinker.getResourceLoc("plsm_explosion_create"), PlasmaExplosionCreateModifierHook.class, PlasmaExplosionCreateModifierHook.AllMerger::new, (tool, fluidStack, player, explosion)-> explosion);
    public static final ModuleHook<PlasmaExplosionHitModifierHook> PLASMA_EXPLOSION_HIT = ModifierHooks.register(etshtinker.getResourceLoc("plsm_explosion_hit"), PlasmaExplosionHitModifierHook.class, PlasmaExplosionHitModifierHook.AllMerger::new, new PlasmaExplosionHitModifierHook() {});
    public static final ModuleHook<FluidConsumptionModifierHook> FLUID_CONSUMPTION = ModifierHooks.register(etshtinker.getResourceLoc("fluid_consumption"), FluidConsumptionModifierHook.class, FluidConsumptionModifierHook.AllMerger::new,  (tool, fluidStack, player, baseAmount,amount)-> amount);
}
