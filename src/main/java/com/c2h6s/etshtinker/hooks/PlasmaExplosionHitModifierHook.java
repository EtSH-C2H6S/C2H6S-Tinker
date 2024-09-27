package com.c2h6s.etshtinker.hooks;

import com.c2h6s.etshtinker.Entities.PlasmaSlashEntity;
import com.c2h6s.etshtinker.Entities.plasmaexplosionentity;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.Collection;

public interface PlasmaExplosionHitModifierHook {
    default void beforePlasmaExplosionHit(ToolStack tool, LivingEntity target, plasmaexplosionentity explosion, boolean isCritical){}
    default void afterPlasmaExplosionHit(ToolStack tool, LivingEntity target, plasmaexplosionentity explosion, boolean isCritical){}
    default void afterSpecialAttack(ToolStack tool, LivingEntity target, plasmaexplosionentity explosion,String special){}
    record AllMerger(Collection<PlasmaExplosionHitModifierHook> modules) implements PlasmaExplosionHitModifierHook{
        @Override
        public void beforePlasmaExplosionHit(ToolStack tool, LivingEntity target, plasmaexplosionentity explosion, boolean isCritical) {
            for (PlasmaExplosionHitModifierHook module:this.modules){
                module.beforePlasmaExplosionHit(tool,target,explosion,isCritical);
            }
        }
        @Override
        public void afterPlasmaExplosionHit(ToolStack tool, LivingEntity target, plasmaexplosionentity explosion, boolean isCritical) {
            for (PlasmaExplosionHitModifierHook module:this.modules){
                module.afterPlasmaExplosionHit(tool,target,explosion,isCritical);
            }
        }
        @Override
        public void afterSpecialAttack(ToolStack tool, LivingEntity target, plasmaexplosionentity explosion,String special) {
            for (PlasmaExplosionHitModifierHook module:this.modules){
                module.afterSpecialAttack(tool,target,explosion,special);
            }
        }
    }
}
