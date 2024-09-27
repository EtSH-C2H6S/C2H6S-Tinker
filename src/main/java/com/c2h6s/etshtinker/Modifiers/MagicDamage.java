package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.PlasmaSlashEntity;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.hooks.AfterPlasmaSlashHitModifierHook;
import com.c2h6s.etshtinker.init.etshtinkerHook;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;


public class MagicDamage extends etshmodifieriii implements AfterPlasmaSlashHitModifierHook {
    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, etshtinkerHook.AFTER_SLASH_HIT);
    }
    @Override
    public void afterPlasmaSlashHit(ToolStack tool, LivingEntity target, PlasmaSlashEntity slash, boolean isCritical, float slashDamage) {
        target.hurt(DamageSource.MAGIC, isCritical ? slashDamage*(slash.CriticalRate+1):slashDamage*0.5f);
    }
}
