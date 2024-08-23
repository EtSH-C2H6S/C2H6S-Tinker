package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class momentoaccel extends etshmodifieriii {
    public void modifierAfterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        Entity entity = context.getAttacker();
        if (modifier.getLevel()>0&&entity instanceof LivingEntity attacker&&!tool.isBroken()){
            if (attacker.getEffect(MobEffects.DIG_SPEED)!=null&&attacker.getEffect(MobEffects.DIG_SPEED).getAmplifier()<8*modifier.getLevel()){
                attacker.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED,60,attacker.getEffect(MobEffects.DIG_SPEED).getAmplifier()+1,false,false));
            }
            else attacker.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED,60,0,false,false));
        }
    }
    public void modifierAfterBlockBreak(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        if (context.getPlayer()!=null) {
            Player player = context.getPlayer();
            if (player.getEffect(MobEffects.DIG_SPEED)!=null&&player.getEffect(MobEffects.DIG_SPEED).getAmplifier()<8*modifier.getLevel()){
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED,60,player.getEffect(MobEffects.DIG_SPEED).getAmplifier()+1,false,false));
            }
            else player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED,60,0,false,false));
        }
    }
}
