package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.util.ParticleChainUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class electromotiveforce extends etshmodifieriii {
    public void modifierOnAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (tool.getModifierLevel(this)>0){
            Entity entity =source.getEntity();
            LivingEntity target =context.getEntity();
            if (entity instanceof LivingEntity attacker) {
                if (target instanceof Player player && modifier.getLevel()>0) {
                    int lvl000 = tool.getModifierLevel(this);
                    double xx = attacker.getX();
                    double yy = attacker.getY();
                    double zz = attacker.getZ();
                    int id0 =attacker.getId();
                    attacker.playSound(SoundEvents.FIREWORK_ROCKET_BLAST_FAR, 2f, 2f);
                    List<Mob> ls001 = attacker.level.getEntitiesOfClass(Mob.class, new AABB(xx + 4 * lvl000, yy + 4 * lvl000, zz + 4 * lvl000, xx - 4 * lvl000, yy - 4 * lvl000, zz - 4 * lvl000));
                    for (Mob mob1 : ls001) {
                        if (mob1 != null) {
                            mob1.invulnerableTime = 0;
                            mob1.hurt(DamageSource.playerAttack(player).bypassMagic().bypassArmor(), amount);
                            mob1.invulnerableTime = 0;
                            int id1=mob1.getId();
                            if (mob1.level instanceof ServerLevel serverLevel){
                                ParticleChainUtil.summonSparkFromTo(serverLevel,id0,id1);
                            }
                        }
                    }
                }
            }
        }
    }
}
