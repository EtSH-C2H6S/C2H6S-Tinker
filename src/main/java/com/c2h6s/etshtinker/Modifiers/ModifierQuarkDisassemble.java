package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.UUID;

public class ModifierQuarkDisassemble extends etshmodifieriii {

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity target =context.getLivingTarget();
        if (target!=null&&!(target instanceof Player)&&context.isFullyCharged()){
            target.getPersistentData().putInt("quark_disassemble",target.getPersistentData().getInt("quark_disassemble")+30*modifier.getLevel()+80);
            target.getPersistentData().putInt("atomic_dec",target.getPersistentData().getInt("atomic_dec")+80*modifier.getLevel());
        }
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
