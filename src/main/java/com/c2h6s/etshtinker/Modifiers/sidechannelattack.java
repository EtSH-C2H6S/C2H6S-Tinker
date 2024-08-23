package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class sidechannelattack extends etshmodifieriii {
    public void modifierAfterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        Entity entity = context.getTarget();
        LivingEntity attacker =context.getAttacker();
        int lvl =tool.getModifierLevel(this);
        if (lvl>0&&entity instanceof LivingEntity target&&attacker instanceof Player player){
            target.invulnerableTime=0;
            target.hurt(DamageSource.playerAttack(player),0.025f*lvl*(target.getMaxHealth()-target.getHealth()));
            target.invulnerableTime=0;
        }
    }
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        int lvl =modifiers.getLevel(this.getId());
        if (modifier.getLevel()>0&&target!=null&&attacker instanceof Player player){
            target.invulnerableTime=0;
            target.hurt(DamageSource.playerAttack(player),0.025f*lvl*(target.getMaxHealth()-target.getHealth()));
            target.invulnerableTime=0;
        }
        return false;
    }
}
