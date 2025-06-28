package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

import static com.c2h6s.etshtinker.util.vecCalc.*;

public class sharpnessex extends EtshModifieriii {
    public boolean isNoLevels() {
        return true;
    }

    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        LivingEntity attacker =context.getAttacker();
        Entity entity =context.getTarget();
        if (entity instanceof LivingEntity target) {
            if (modifier.getLevel() > 0 && attacker instanceof Player player && modifier.getLevel() > 0) {
                target.invulnerableTime = 0;
                target.hurt(DamageSource.playerAttack(player).bypassArmor().bypassMagic(), damage * 0.5F);
                target.invulnerableTime = 0;
            }
        }
        return baseKnockback;
    }

    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (attacker instanceof Player player && target != null&&projectile instanceof AbstractArrow arrow) {
            target.invulnerableTime = 0;
            target.hurt(DamageSource.thrown(arrow,player).bypassArmor().bypassMagic(), (float) (arrow.getBaseDamage()* getMold(arrow.getDeltaMovement()) * 0.5F));
            target.invulnerableTime = 0;
        }
        return false;
    }

}