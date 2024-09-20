package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEffects;
import com.c2h6s.etshtinker.util.ParticleChainUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class electricArmor extends etshmodifieriii {
    public void modifierOnAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (tool.getModifierLevel(this)>0){
            Entity entity =source.getEntity();
            LivingEntity target =context.getEntity();
            Level level =target.level;
            if (entity instanceof LivingEntity attacker) {
                if (target instanceof Player player && modifier.getLevel()>0) {
                    int lvl000 = tool.getModifierLevel(this);
                    double xx = attacker.getX();
                    double yy = attacker.getY();
                    double zz = attacker.getZ();
                    attacker.playSound(SoundEvents.FIREWORK_ROCKET_TWINKLE, 1.2f, 1.2f);
                    attacker.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 100, false, false), attacker);
                    List<Mob> ls001 = attacker.level.getEntitiesOfClass(Mob.class, new AABB(xx + 5 * lvl000, yy + 5 * lvl000, zz + 5 * lvl000, xx - 5 * lvl000, yy - 5 * lvl000, zz - 5 * lvl000));
                    for (Mob mob1 : ls001) {
                        if (mob1 != null) {
                            mob1.invulnerableTime = 0;
                            mob1.hurt(DamageSource.playerAttack(player).bypassMagic().bypassArmor(), mob1.getMaxHealth() * 0.01f * lvl000);
                            mob1.invulnerableTime = 0;
                            mob1.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, Math.min(100 * lvl000, 200), 100, false, false), attacker);
                            if (level!=null) {
                                attacker.forceAddEffect(new MobEffectInstance(etshtinkerEffects.ionized.get(),100,2*lvl000,false,false),attacker);
                                mob1.forceAddEffect(new MobEffectInstance(etshtinkerEffects.ionized.get(),100,2*lvl000,false,false),attacker);
                                ParticleChainUtil.summonElectricFromTowithlimit(level,attacker.getId(),mob1.getId(),128);
                            }
                        }
                    }
                }
            }
        }
    }
}
