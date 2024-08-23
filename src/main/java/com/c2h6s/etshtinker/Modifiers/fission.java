package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.util.getMainOrOff;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import mekanism.api.MekanismAPI;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import com.c2h6s.etshtinker.init.etshtinkerModifiers;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

import static com.c2h6s.etshtinker.util.getMainOrOff.*;
import java.util.List;


public class fission extends etshmodifieriii {
    public static boolean enabled = ModList.get().isLoaded("mekanism");
    public boolean isNoLevels() {
        return true;
    }
    public float modifierBeforeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback){
        LivingEntity attacker =context.getAttacker();
        Entity entity =context.getTarget();
        if (entity instanceof LivingEntity target) {
            if (enabled && attacker instanceof Player player && getMainLevel(attacker, this) > 0 && target != null && MekanismAPI.getRadiationManager().isRadiationEnabled() && getMainLevel(attacker, this) > 0) {
                target.invulnerableTime = 0;
            }
        }
        return baseKnockback;
    }

    public void modifierAfterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        LivingEntity attacker =context.getAttacker();
        Entity entity =context.getTarget();
        if (entity instanceof LivingEntity target) {
            if (enabled) {
                if (attacker instanceof Player player && getMainLevel(attacker, this) > 0 && target != null && getMainOrOff.getMainLevel(player, etshtinkerModifiers.chainreaction_STATIC_MODIFIER.get()) == 0 && MekanismAPI.getRadiationManager().isRadiationEnabled() && getMainLevel(attacker, this) > 0) {
                    MekanismAPI.getRadiationManager().radiate(player, 10);
                    MekanismAPI.getRadiationManager().radiate(target, 10);
                } else if (attacker instanceof Player player && getMainLevel(attacker, this) > 0 && target != null && getMainOrOff.getMainLevel(player, etshtinkerModifiers.chainreaction_STATIC_MODIFIER.get()) > 0 && MekanismAPI.getRadiationManager().isRadiationEnabled() && getMainOrOff.getMainLevel(player, etshtinkerModifiers.fusion_STATIC_MODIFIER.get()) == 0) {
                    MekanismAPI.getRadiationManager().radiate(target, 50);
                } else if (attacker instanceof Player player && getMainLevel(attacker, this) > 0 && target != null && getMainOrOff.getMainLevel(player, etshtinkerModifiers.fusion_STATIC_MODIFIER.get()) > 0 && MekanismAPI.getRadiationManager().isRadiationEnabled()) {
                    double x = attacker.getX();
                    double y = attacker.getY();
                    double z = attacker.getZ();
                    int iii = getMainOrOff.getMainLevel(player, etshtinkerModifiers.fusion_STATIC_MODIFIER.get());
                    List<Mob> mobabcd = player.level.getEntitiesOfClass(Mob.class, new AABB(x + (7 + iii), y + (7 + iii), z + (7 + iii), x - (7 + iii), y - (7 + iii), z - (7 + iii)));
                    int i = 0;
                    for (Mob targets : mobabcd) {
                        if (targets != null && i <= 32 * iii) {
                            MekanismAPI.getRadiationManager().radiate(targets, 100 * iii);
                            i++;
                        }
                    }
                }
            }
        }
    }
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (enabled) {
            if (attacker instanceof Player player && getMainLevel(attacker,this) > 0 && target != null && getMainOrOff.getMainLevel(player, etshtinkerModifiers.chainreaction_STATIC_MODIFIER.get()) == 0 && MekanismAPI.getRadiationManager().isRadiationEnabled() && getMainLevel(attacker,this) > 0) {
                target.invulnerableTime = 0;
                MekanismAPI.getRadiationManager().radiate(player, 10);
                MekanismAPI.getRadiationManager().radiate(target, 10);
            } else if (attacker instanceof Player player && getMainLevel(attacker,this) > 0 && target != null && getMainOrOff.getMainLevel(player, etshtinkerModifiers.chainreaction_STATIC_MODIFIER.get()) > 0 && MekanismAPI.getRadiationManager().isRadiationEnabled() && getMainOrOff.getMainLevel(player, etshtinkerModifiers.fusion_STATIC_MODIFIER.get()) == 0) {
                target.invulnerableTime = 0;
                MekanismAPI.getRadiationManager().radiate(target, 50);
            } else if (attacker instanceof Player player && getMainLevel(attacker,this) > 0 && target != null && getMainOrOff.getMainLevel(player, etshtinkerModifiers.fusion_STATIC_MODIFIER.get()) > 0 && MekanismAPI.getRadiationManager().isRadiationEnabled()) {
                target.invulnerableTime = 0;
                double x = attacker.getX();
                double y = attacker.getY();
                double z = attacker.getZ();
                int iii = getMainOrOff.getMainLevel(player, etshtinkerModifiers.fusion_STATIC_MODIFIER.get());
                List<Mob> mobabcd = player.level.getEntitiesOfClass(Mob.class, new AABB(x + 7 + iii, y + 7 + iii, z + 7 + iii, x - (7 + iii), y - (7 + iii), z - (7 + iii)));
                int i = 0;
                for (Mob targets : mobabcd) {
                    if (targets != null && i <= 32 * iii) {
                        MekanismAPI.getRadiationManager().radiate(targets, 100 * iii);
                        i++;
                    }
                }
            }
        }
        return false;
    }

}
