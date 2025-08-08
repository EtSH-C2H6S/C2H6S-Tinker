package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;


public class organiccr extends EtshModifieriii {
    public boolean isNoLevels() {
        return true;
    }
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        LivingEntity target =context.getLivingTarget();
        if (target!=null&&!(target instanceof Player)){
            if (target.getPersistentData().contains("legacyhealth")&&target.getHealth()<target.getPersistentData().getFloat("legacyhealth")){
                target.getPersistentData().putFloat("legacyhealth",target.getHealth());
            }
        }
    }
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback){
        LivingEntity target =context.getLivingTarget();
        LivingEntity attacker =context.getAttacker();
        if (target!=null&&!(target instanceof Player)){
            if (target.getPersistentData().contains("legacyhealth")){
                float legacyHealth = target.getPersistentData().getFloat("legacyhealth");
                if (target.getHealth()>legacyHealth&&legacyHealth>0){
                    attacker.heal(Math.min( 10,target.getHealth()-target.getPersistentData().getFloat("legacyhealth")));
                    target.setHealth(legacyHealth);
                }
            }
        }
        return knockback;
    }
}
