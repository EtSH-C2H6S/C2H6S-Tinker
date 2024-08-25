package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;


public class organiccr extends etshmodifieriii {
    public boolean isNoLevels() {
        return true;
    }
    public void modifierAfterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        LivingEntity target =context.getLivingTarget();
        if (target!=null){
            if (target.getPersistentData().contains("legacyhealth")&&target.getHealth()>target.getPersistentData().getFloat("legacyhealth")){
                target.getPersistentData().putFloat("legacyhealth",target.getPersistentData().getFloat("legacyhealth")-Math.abs(damageDealt));
            }
            else {
                target.getPersistentData().putFloat("legacyhealth",target.getHealth());
            }
        }
    }
    public float modifierBeforeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback){
        LivingEntity target =context.getLivingTarget();
        LivingEntity attacker =context.getAttacker();
        if (target!=null&&modifier.getLevel()>0){
            if (target.getPersistentData().contains("legacyhealth")){
                if (target.getHealth()>target.getPersistentData().getFloat("legacyhealth")){
                    attacker.heal(Math.min( 20,target.getHealth()-target.getPersistentData().getFloat("legacyhealth")));
                    target.setHealth(target.getPersistentData().getFloat("legacyhealth")-damage);
                    target.getPersistentData().putFloat("legacyhealth",target.getHealth());
                }
            }
        }
        return knockback;
    }
}
