package com.c2h6s.etshtinker.hooks;

import com.c2h6s.etshtinker.Entities.PlasmaSlashEntity;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.Collection;

public interface AfterPlasmaSlashHitModifierHook {
    default void afterPlasmaSlashHit(ToolStack tool, LivingEntity target, PlasmaSlashEntity slash,boolean isCritical,float slashDamage){
    }
    record AllMerger(Collection<AfterPlasmaSlashHitModifierHook> modules) implements AfterPlasmaSlashHitModifierHook{
        @Override
        public void afterPlasmaSlashHit(ToolStack tool, LivingEntity target, PlasmaSlashEntity slash,boolean isCritical,float slashDamage) {
            for (AfterPlasmaSlashHitModifierHook module:this.modules){
                module.afterPlasmaSlashHit(tool,target,slash,isCritical,slashDamage);
            }
        }
    }
}
