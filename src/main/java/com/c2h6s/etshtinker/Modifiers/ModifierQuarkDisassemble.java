package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.damageSources.playerThroughSource;
import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import net.minecraft.world.entity.LivingEntity;
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

public class ModifierQuarkDisassemble extends EtshModifieriii {
    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        LivingEntity target =context.getLivingTarget();
        if (target!=null&&!(target instanceof Player)&&context.isFullyCharged()){
            target.invulnerableTime=0;
            if (context.getAttacker() instanceof Player player) {
                target.hurt(playerThroughSource.PlayerQuark(player,damage),damage);
            }
            target.getPersistentData().putInt("quark_disassemble",target.getPersistentData().getInt("quark_disassemble")+30*modifier.getLevel()+80);
            target.getPersistentData().putInt("atomic_dec",target.getPersistentData().getInt("atomic_dec")+80*modifier.getLevel());
        }
        return knockback;
    }

    @Override
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (attacker!=null && target!=null&&!(target instanceof Player)&&projectile instanceof AbstractArrow arrow&&arrow.isCritArrow()){
            target.getPersistentData().putInt("quark_disassemble",target.getPersistentData().getInt("quark_disassemble")+30*modifier.getLevel()+80);
            target.getPersistentData().putInt("atomic_dec",target.getPersistentData().getInt("atomic_dec")+80*modifier.getLevel());
        }
        return false;
    }
}
