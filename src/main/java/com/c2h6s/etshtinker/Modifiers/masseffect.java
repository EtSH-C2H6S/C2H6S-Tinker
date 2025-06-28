package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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
public class masseffect extends EtshModifieriii {
    AttributeModifier attributeModifier1 = new AttributeModifier("etsh.gravity",10, AttributeModifier.Operation.MULTIPLY_TOTAL);

    @Override
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        LivingEntity attacker =context.getAttacker();
        return damage+baseDamage * modifier.getLevel() * (float) getMold(attacker.getDeltaMovement());
    }
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (projectile instanceof AbstractArrow arrow) {
            if (attacker instanceof Player player && target != null && !(target instanceof Player)) {
                target.invulnerableTime = 0;
                target.hurt(DamageSource.playerAttack(player), (float) arrow.getBaseDamage() * Math.max(1, modifier.getLevel()*2 * (float) getMold(attacker.getDeltaMovement())));
            }
        }
        return false;
    }

}
