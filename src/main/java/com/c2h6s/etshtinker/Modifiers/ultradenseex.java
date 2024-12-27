package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;


import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class ultradenseex extends etshmodifieriii {

    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        for (ModifierEntry entry : tool.getModifierList()) {
            if (entry.getModifier() != modifier.getModifier()) {
                knockback = entry.getHook(ModifierHooks.MELEE_HIT).beforeMeleeHit(tool, modifier, context, damage, baseKnockback,knockback);
            }
        }
        return knockback;
    }

    @Override
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        for (ModifierEntry entry : tool.getModifierList()) {
            if (entry.getModifier() != modifier.getModifier()) {
                damage = entry.getHook(ModifierHooks.MELEE_DAMAGE).getMeleeDamage(tool, modifier, context, baseDamage, damage);
            }
        }
        return damage;
    }

    @Override
    public void modifierDamageDealt(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, LivingEntity entity, DamageSource damageSource, float amount, boolean isDirectDamage) {
        for (ModifierEntry entry : tool.getModifierList()) {
            if (entry.getModifier() != modifier.getModifier()) {
                entry.getHook(ModifierHooks.DAMAGE_DEALT).onDamageDealt(tool, modifier, context,slotType,entity,damageSource,amount,isDirectDamage);
            }
        }
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        for (ModifierEntry entry : tool.getModifierList()) {
            if (entry.getModifier() != modifier.getModifier()) {
                entry.getHook(ModifierHooks.MELEE_HIT).afterMeleeHit(tool, modifier, context, damageDealt);
            }
        }
    }

}
