package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.util.ParticleChainUtil;
import com.c2h6s.etshtinker.util.attackUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifierfluxed;

import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;



public class thermalstrike extends etshmodifieriii {
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback){
        LivingEntity attacker =context.getAttacker();
        Entity entity =context.getTarget();
        if (attacker instanceof Player player && context.isFullyCharged()&&!context.isExtraAttack()) {
            int range = modifier.getLevel()*2+3;
            AABB aabb = new AABB(entity.blockPosition()).inflate(range);
            List<Mob> mobs = player.level.getEntitiesOfClass(Mob.class,aabb);
            if (entity instanceof Mob mob1){
                mobs.remove(mob1);
            }
            if (mobs.isEmpty()){
                return knockback;
            }
            Mob mob = mobs.get(EtSHrnd().nextInt(mobs.size()));
            if (player.level instanceof ServerLevel serverLevel){
                ParticleChainUtil.SummonParticleChain(serverLevel,entity.position(),mob.position(), ParticleTypes.CRIT);
            }
            mob.invulnerableTime=0;
            attackUtil.attackEntity(tool,player,context.getHand(),mob,()->1,true,context.getSlotType(),damage*0.5f,false,true,false,false,0);
            mobs.remove(mob);
            int left =16;
            while (etshmodifierfluxed.getEnergyStored(tool)>1000&&!mobs.isEmpty()&&left>0){
                Mob mob1 = mobs.get(EtSHrnd().nextInt(mobs.size()));
                if (player.level instanceof ServerLevel serverLevel){
                    ParticleChainUtil.SummonParticleChain(serverLevel,mob.position(),mob1.position(), ParticleTypes.CRIT);
                }
                mob1.invulnerableTime=0;
                attackUtil.attackEntity(tool,player,context.getHand(),mob1,()->1,true,context.getSlotType(),damage*0.5f,false,true,false,false,0);
                mobs.remove(mob1);
                mob =mob1;
                etshmodifierfluxed.removeEnergy(tool,1000,false,false);
                left--;
            }
        }

        return baseKnockback;
    }

    @Override
    public void modifierOnProjectileLaunch(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean primary) {
        if (etshmodifierfluxed.getEnergyStored(tool)>1000&&projectile instanceof AbstractArrow arrow){
            arrow.addTag("flux_strike");
            etshmodifierfluxed.removeEnergy(tool,1000,false,false);
        }
    }

    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (attacker instanceof Player player &&projectile instanceof AbstractArrow arrow&&target!=null) {
            InteractionHand hand = attacker.getUsedItemHand();
            if (arrow.isCritArrow()&&arrow.getTags().contains("flux_strike")) {
                float damage = (float) (arrow.getBaseDamage()*arrow.getDeltaMovement().length());
                int range = modifier.getLevel()+3;
                AABB aabb = new AABB(target.blockPosition()).inflate(range);
                List<Mob> mobs = player.level.getEntitiesOfClass(Mob.class,aabb);
                if (target instanceof Mob mob1){
                    mobs.remove(mob1);
                }
                if (mobs.isEmpty()){
                    return false;
                }
                Mob mob = mobs.get(EtSHrnd().nextInt(mobs.size()));
                if (player.level instanceof ServerLevel serverLevel){
                    ParticleChainUtil.SummonParticleChain(serverLevel,target.position(),mob.position(), ParticleTypes.CRIT);
                }
                mob.invulnerableTime=0;

                mobs.remove(mob);
                int left =16;
                while (!mobs.isEmpty()&&left>0){
                    Mob mob1 = mobs.get(EtSHrnd().nextInt(mobs.size()));
                    if (player.level instanceof ServerLevel serverLevel){
                        ParticleChainUtil.SummonParticleChain(serverLevel,mob.position(),mob1.position(), ParticleTypes.CRIT);
                    }
                    mob1.invulnerableTime=0;
                    mob1.hurt(DamageSource.thrown(arrow,arrow.getOwner()),damage*0.5f);
                    mobs.remove(mob1);
                    mob =mob1;
                    left--;
                }
            }
            target.invulnerableTime=0;
        }
        return false;
    }
}
