package com.c2h6s.etshtinker.Modifiers.modifiers;

import com.c2h6s.etshtinker.Entities.PlasmaSlashEntity;
import com.c2h6s.etshtinker.Entities.plasmaexplosionentity;
import com.c2h6s.etshtinker.hooks.*;
import com.c2h6s.etshtinker.init.etshtinkerHook;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class etshmodifierFluidWeapon extends etshmodifieriii implements FluidConsumptionModifierHook, PlasmaExplosionHitModifierHook, PlasmaSlashCreateModifierHook, BeforePlasmaSlashHitModifierHook, AfterPlasmaSlashHitModifierHook ,PlasmaExplosionCreateModifierHook{
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, etshtinkerHook.PLASMA_EXPLOSION_HIT,etshtinkerHook.AFTER_SLASH_HIT,etshtinkerHook.BEFORE_SLASH_HIT);
        builder.addHook(this, etshtinkerHook.FLUID_CONSUMPTION,etshtinkerHook.SLASH_CREATE,etshtinkerHook.PLASMA_EXPLOSION_CREATE);
    }

    @Override
    public int getFluidConsumption(IToolStackView tool, FluidStack fluidStack, Player player, int baseAmount, int amount) {
        return this.onFluidConsumption(tool,fluidStack,player,baseAmount,amount);
    }

    @Override
    public PlasmaSlashEntity plasmaSlashCreate(IToolStackView tool, FluidStack fluidStack, ServerPlayer player, PlasmaSlashEntity slash) {
        return this.onPlasmaSlashCreate(tool,fluidStack,player,slash);
    }

    @Override
    public void afterPlasmaSlashHit(ToolStack tool, LivingEntity target, PlasmaSlashEntity slash, boolean isCritical, float slashDamage) {
        this.modifierAfterPlasmaSlashHit(tool,target,slash,isCritical,slashDamage);
    }
    @Override
    public void beforePlasmaSlashHit(ToolStack tool, LivingEntity target, PlasmaSlashEntity slash, boolean isCritical) {
        this.modifierBeforePlasmaSlashHit(tool,target,slash,isCritical);
    }

    @Override
    public void beforePlasmaExplosionHit(ToolStack tool, LivingEntity target, plasmaexplosionentity explosion, boolean isCritical) {
        this.modifierBeforePlasmaExplosionHit(tool,target,explosion,isCritical);
    }
    @Override
    public void afterPlasmaExplosionHit(ToolStack tool, LivingEntity target, plasmaexplosionentity explosion, boolean isCritical) {
        this.modifierAfterPlasmaExplosionHit(tool,target,explosion,isCritical);
    }
    @Override
    public void afterSpecialAttack(ToolStack tool, LivingEntity target, plasmaexplosionentity explosion, String special) {
        this.modifierAfterSpecialAttack(tool,target,explosion,special);
    }

    @Override
    public plasmaexplosionentity plasmaExplosionCreate(IToolStackView tool, FluidStack fluidStack, Player player, plasmaexplosionentity explosion) {
        return this.onPlasmaExplosionCreate(tool,fluidStack,player,explosion);
    }


    public int onFluidConsumption(IToolStackView tool, FluidStack fluidStack, Player player, int baseAmount, int amount) {
        return amount;
    }

    public PlasmaSlashEntity onPlasmaSlashCreate(IToolStackView tool, FluidStack fluidStack, ServerPlayer player, PlasmaSlashEntity slash) {
        return slash;
    }

    public plasmaexplosionentity onPlasmaExplosionCreate(IToolStackView tool, FluidStack fluidStack, Player player, plasmaexplosionentity explosion) {
        return explosion;
    }


    public void modifierAfterPlasmaSlashHit(ToolStack tool, LivingEntity target, PlasmaSlashEntity slash, boolean isCritical, float slashDamage) {
    }
    public void modifierBeforePlasmaSlashHit(ToolStack tool, LivingEntity target, PlasmaSlashEntity slash, boolean isCritical) {
    }


    public void modifierBeforePlasmaExplosionHit(ToolStack tool, LivingEntity target, plasmaexplosionentity explosion, boolean isCritical) {
    }
    public void modifierAfterPlasmaExplosionHit(ToolStack tool, LivingEntity target, plasmaexplosionentity explosion, boolean isCritical) {
    }
    public void modifierAfterSpecialAttack(ToolStack tool, LivingEntity target, plasmaexplosionentity explosion, String special) {
    }
}

