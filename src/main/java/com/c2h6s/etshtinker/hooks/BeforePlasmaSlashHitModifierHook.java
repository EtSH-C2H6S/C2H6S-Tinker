package com.c2h6s.etshtinker.hooks;

import com.c2h6s.etshtinker.Entities.PlasmaSlashEntity;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.Collection;

public interface BeforePlasmaSlashHitModifierHook {
    default void beforePlasmaSlashHit(ToolStack tool, LivingEntity target, PlasmaSlashEntity slash, boolean isCritical){
    }
    record AllMerger(Collection<BeforePlasmaSlashHitModifierHook> modules) implements BeforePlasmaSlashHitModifierHook{
        @Override
        public void beforePlasmaSlashHit(ToolStack tool, LivingEntity target, PlasmaSlashEntity slash,boolean isCritical) {
            for (BeforePlasmaSlashHitModifierHook module:this.modules){
                module.beforePlasmaSlashHit(tool,target,slash,isCritical);
            }
        }
    }
}
