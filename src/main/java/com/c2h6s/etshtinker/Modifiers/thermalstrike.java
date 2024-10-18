package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.util.ParticleChainUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.item.ranged.ModifiableLauncherItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifierfluxed;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.ArrayList;
import java.util.List;

import static com.c2h6s.etshtinker.util.vecCalc.*;
import static com.c2h6s.etshtinker.util.getMainOrOff.*;
import static net.minecraft.sounds.SoundSource.NEUTRAL;


public class thermalstrike extends etshmodifieriii {
    public float modifierBeforeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback){
        LivingEntity attacker =context.getAttacker();
        Entity entity =context.getTarget();
        if (entity instanceof LivingEntity target) {
            Level world = attacker.getLevel();
            if (attacker instanceof Player player && getMainLevel(attacker, this) > 0 && context.isFullyCharged()&&etshmodifierfluxed.getEnergyStored(tool)>2000) {
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FLINTANDSTEEL_USE, NEUTRAL, 1, 1.7f);
                int modilvl = getMainLevel(attacker, this);
                List<LivingEntity> ls0 = new ArrayList<>(List.of());
                int a = 0;
                LivingEntity entity1 = getNearestLiEnt((float) 5 + modilvl, player, player.level);
                while (a < 11 + modilvl) {
                    if (entity1 != null) {
                        int id0 =entity1.getId();
                        etshmodifierfluxed.removeEnergy(tool,2000,false,false);
                        ls0.add(entity1);
                        DamageSource dam = DamageSource.playerAttack(player).bypassMagic().bypassArmor();
                        entity1.invulnerableTime = 0;
                        entity1.hurt(dam, damage*0.5f);
                        entity1.invulnerableTime =0;
                        if (getNearestLiEnt((float) 5 + modilvl, entity1, entity1.level) != null) {
                            entity1 = getNearestLiEntWithBL((float) 5 + modilvl, entity1, entity1.level, ls0);
                            if (entity1!=null) {
                                int id1 = entity1.getId();
                                if (target.level instanceof ServerLevel serverLevel) {
                                    ParticleChainUtil.summonSparkFromTo(serverLevel, id0, id1);
                                }
                            }
                        } else break;
                    }
                    a++;
                }
            }
        }

        return baseKnockback;
    }
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (attacker instanceof Player player && getMainLevel(attacker,this) > 0&&projectile instanceof AbstractArrow arrow) {
            InteractionHand hand = attacker.getUsedItemHand();
            if (attacker.getItemInHand(hand).getItem() instanceof ModifiableLauncherItem) {
                IToolStackView tool = ToolStack.from(attacker.getItemInHand(hand));
                int modilvl = getMainLevel(attacker, this);
                int a = 0;
                List<LivingEntity> ls0 = new ArrayList<>(List.of());
                LivingEntity entity = getNearestLiEnt((float) 5 + modilvl, player, player.level);
                while (a < 11 + modilvl && etshmodifierfluxed.getEnergyStored(tool) > 2000) {
                    if (entity != null) {
                        int id0=entity.getId();
                        etshmodifierfluxed.removeEnergy(tool, 2000, false, false);
                        ls0.add(entity);
                        DamageSource dam = DamageSource.playerAttack(player).bypassMagic().bypassArmor();
                        entity.invulnerableTime = 0;
                        entity.hurt(dam, (float) (arrow.getBaseDamage() * 0.5 * getMold(arrow.getDeltaMovement())));
                        entity.invulnerableTime = 0;
                        if (getNearestLiEnt((float) 5 + modilvl, entity, entity.level) != null) {
                            entity = getNearestLiEntWithBL((float) 5 + modilvl, entity, entity.level, ls0);
                            if (entity!=null) {
                                int id1 = entity.getId();
                                if (entity.level instanceof ServerLevel serverLevel) {
                                    ParticleChainUtil.summonSparkFromTo(serverLevel, id0, id1);
                                }
                            }
                        } else break;
                    }
                    a++;
                }
            }
        }
        return false;
    }
}
