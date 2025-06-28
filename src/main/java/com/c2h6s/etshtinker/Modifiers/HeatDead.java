package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.damageSources.playerThroughSource;
import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

import java.util.UUID;

public class HeatDead extends EtshModifieriii {
    public static final UUID hdUUID = UUID.fromString("b43c5ccf-1763-f7ae-0086-0c25fc64249d");

    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        if (context.getTarget() instanceof LivingEntity living&&context.getAttacker() instanceof Player player){
            playerThroughSource.PlayerAnnihilate(player,damage).hurtEntity(living);
        }
        return knockback;
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity target =context.getLivingTarget();
        if (target!=null&&!(target instanceof Player)&&context.isFullyCharged()&&context.getAttacker() instanceof Player player){
            if (target.getPersistentData().contains("max_health")&&target.getHealth()>target.getPersistentData().getFloat("max_health")){
                playerThroughSource.PlayerAnnihilate(player,target.getHealth()-target.getPersistentData().getFloat("max_health")).hurtEntity(target);
            }
            AttributeInstance instance = target.getAttribute(Attributes.MAX_HEALTH);
            double d =0;
            if (instance!=null) {
                if (instance.getModifier(hdUUID) != null) {
                    d = instance.getModifier(hdUUID).getAmount();
                    instance.removeModifier(hdUUID);
                }
                instance.addTransientModifier(new AttributeModifier(hdUUID,Attributes.MAX_HEALTH.getDescriptionId(),d-damageDealt, AttributeModifier.Operation.ADDITION));
                target.getPersistentData().putFloat("max_health",target.getHealth());
            }
        }
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (target!=null&&!(target instanceof Player)&&projectile instanceof AbstractArrow arrow&&arrow.isCritArrow()&&attacker instanceof Player player){
            if (target.getPersistentData().contains("max_health")&&target.getHealth()>target.getPersistentData().getFloat("max_health")){
                playerThroughSource.PlayerAnnihilate(player,target.getHealth()-target.getPersistentData().getFloat("max_health")).hurtEntity(target);
            }
            AttributeInstance instance = target.getAttribute(Attributes.MAX_HEALTH);
            double d =0;
            if (instance!=null) {
                if (instance.getModifier(hdUUID) != null) {
                    d += instance.getModifier(hdUUID).getAmount();
                    instance.removeModifier(hdUUID);
                }
                double damage = arrow.getBaseDamage()*arrow.getDeltaMovement().length();
                instance.addTransientModifier(new AttributeModifier(hdUUID,Attributes.MAX_HEALTH.getDescriptionId(),d-damage, AttributeModifier.Operation.ADDITION));
                target.getPersistentData().putFloat("max_health",target.getHealth());
            }
        }
        return false;
    }
}
