package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

import static com.c2h6s.etshtinker.util.vecCalc.*;
import static com.c2h6s.etshtinker.util.getMainOrOff.*;
public class masseffect extends etshmodifieriii {
    public float modifierBeforeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback){
        LivingEntity attacker =context.getAttacker();
        Entity entity =context.getTarget();
        if (entity instanceof LivingEntity target) {
            if (attacker instanceof Player player && getMainLevel(attacker, this) > 0) {
                target.invulnerableTime = 0;
                target.hurt(DamageSource.playerAttack(player), damage * Math.max(1, getMainLevel(attacker, this) *2* (float) getMold(attacker.getDeltaMovement())));
                target.setOnGround(false);
                target.setNoGravity(true);
                Level world = attacker.getLevel();
                target.invulnerableTime = 0;
                world.explode(attacker, target.getX(), target.getY(), target.getZ(), getMainLevel(attacker, this) , false, Explosion.BlockInteraction.NONE);
            }
        }
        return baseKnockback;
    }
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (projectile instanceof AbstractArrow arrow) {
            if (attacker instanceof Player player && getMainLevel(attacker, this) > 0 && target != null && !(target instanceof Player)) {
                Level world = attacker.getLevel();
                target.invulnerableTime = 0;
                target.hurt(DamageSource.playerAttack(player), (float) arrow.getBaseDamage() * Math.max(1, getMainLevel(attacker, this)*2 * (float) getMold(attacker.getDeltaMovement())));
                target.invulnerableTime = 0;
                world.explode(attacker, target.getX(), target.getY(), target.getZ(), getMainLevel(attacker, this) , false, Explosion.BlockInteraction.NONE);
                target.setOnGround(false);
                target.setNoGravity(true);
                target.invulnerableTime = 0;
                arrow.remove(Entity.RemovalReason.KILLED);
            }
        }
        return false;
    }
    public void modifierOnEquip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        LivingEntity entity = context.getEntity();
        if (entity instanceof Player player) {
            player.getAbilities().flying = true;
            player.getAbilities().mayfly = true;
        }
    }

    public void modifierOnUnequip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        LivingEntity entity = context.getEntity();
        if (entity instanceof Player player) {
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
        }
    }
}
