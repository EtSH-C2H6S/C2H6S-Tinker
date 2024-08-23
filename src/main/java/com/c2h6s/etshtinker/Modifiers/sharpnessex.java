package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
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

import static com.c2h6s.etshtinker.util.getMainOrOff.*;
import static com.c2h6s.etshtinker.util.vecCalc.*;

public class sharpnessex extends etshmodifieriii {
    public boolean isNoLevels() {
        return true;
    }

    public float modifierBeforeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback){
        LivingEntity attacker =context.getAttacker();
        Entity entity =context.getTarget();
        if (entity instanceof LivingEntity target) {
            if (getMainLevel(attacker, this) > 0 && attacker instanceof Player player && getMainLevel(player, this) > 0 && target != null) {
                DamageSource.playerAttack(player).bypassArmor().bypassInvul().bypassMagic();
                target.invulnerableTime = 0;
                target.hurt(DamageSource.playerAttack(player), damage * 0.5F);
                target.invulnerableTime = 0;
            }
        }
        return baseKnockback;
    }
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (getMainLevel(attacker, this) > 0 && attacker instanceof Player player && getMainLevel(player, this) > 0 && target != null&&projectile instanceof AbstractArrow arrow) {
            DamageSource.playerAttack(player).bypassArmor().bypassInvul().bypassMagic();
            target.invulnerableTime = 0;
            target.hurt(DamageSource.playerAttack(player), (float) (arrow.getBaseDamage()* getMold(arrow.getDeltaMovement()) * 0.5F));
            target.invulnerableTime = 0;
        }
        return false;
    }

}