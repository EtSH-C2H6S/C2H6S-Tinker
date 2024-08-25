package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import vazkii.botania.common.entity.PixieEntity;

import java.security.SecureRandom;
import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;
import static com.c2h6s.etshtinker.util.vecCalc.getNearestLiEnt;

public class alfsmind extends etshmodifieriii {
    public List<MobEffect> ls = List.of(
            MobEffects.WEAKNESS,
            MobEffects.WITHER,
            MobEffects.POISON,
            MobEffects.MOVEMENT_SLOWDOWN,
            MobEffects.DIG_SLOWDOWN
    );
    public void modifierOnAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (modifier.getLevel()>0){
            SecureRandom random =EtSHrnd();
            LivingEntity target =context.getEntity();
            if (random.nextBoolean()){
                PixieEntity entity =new PixieEntity(target.level);
                LivingEntity target2 =((source.getEntity() instanceof LivingEntity entity1&&entity1.isAlive())?entity1:getNearestLiEnt(32f,target,target.level));
                entity.setProps(target2,target,0,amount);
                entity.setPos(target.getX(),target.getY()+target.getBbHeight()+0.25,target.getZ());
                entity.setApplyPotionEffect(new MobEffectInstance(ls.get(random.nextInt(5)),100,4,false,false));
                target.level.addFreshEntity(entity);
            }
        }
    }
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (target!=null&&attacker!=null&&modifier.getLevel()>0){
            SecureRandom random =EtSHrnd();
            if (random.nextBoolean()){
                PixieEntity entity =new PixieEntity(target.level);
                LivingEntity target2 =((target.isAlive())?target:getNearestLiEnt(32f,target,target.level));
                entity.setProps(target2,attacker,0,16);
                entity.setApplyPotionEffect(new MobEffectInstance(ls.get(random.nextInt(6)),100,4,false,false));
                entity.setPos(target.getRandomX(4),target.getRandomY()+0.5*target.getBbHeight(),target.getRandomZ(4));
                target.level.addFreshEntity(entity);
            }
        }
        return false;
    }
    public void modifierAfterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        LivingEntity target =context.getLivingTarget();
        LivingEntity attacker =context.getAttacker();
        if (target!=null&&modifier.getLevel()>0){
            SecureRandom random =EtSHrnd();
            if (random.nextBoolean()){
                PixieEntity entity =new PixieEntity(target.level);
                LivingEntity target2 =((target.isAlive())?target:getNearestLiEnt(32f,target,target.level));
                entity.setProps(target2,attacker,0,16);
                entity.setApplyPotionEffect(new MobEffectInstance(ls.get(random.nextInt(6)),100,4,false,false));
                entity.setPos(target.getRandomX(4),target.getRandomY()+0.5*target.getBbHeight(),target.getRandomZ(4));
                target.level.addFreshEntity(entity);
            }
        }
    }

}
