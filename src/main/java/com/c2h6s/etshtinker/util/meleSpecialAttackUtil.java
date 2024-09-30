package com.c2h6s.etshtinker.util;


import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.etshtinkerToolStats;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import com.c2h6s.etshtinker.Entities.*;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.utils.Util;
import slimeknights.tconstruct.tools.data.ModifierIds;

import java.util.List;

import static com.c2h6s.etshtinker.util.SlashColor.getSlashType;
import static com.c2h6s.etshtinker.util.vecCalc.*;
import static net.minecraft.world.entity.EquipmentSlot.Type.HAND;

public class meleSpecialAttackUtil {
    public static void createWarp(@NotNull Player attacker, Float radius, Float damage, ToolStack tool, InteractionHand hand){
        int lvl =(int)(radius/8);
        Level world =attacker.getLevel();
        LivingEntity entity =getNearestLiEnt(radius,attacker,world);
        if (entity!=null&&entity.isAlive()){
            entity.invulnerableTime=0;
            attackUtil.attackEntity(tool,attacker,hand,entity,()->1,true, Util.getSlotType(hand),damage,true,true,true,true,0.5f);
            entity.invulnerableTime=0;
            if (world.isClientSide) {
                world.addAlwaysVisibleParticle(ParticleTypes.SWEEP_ATTACK, true, entity.getX(), entity.getY() + 0.5 * entity.getBbHeight(), entity.getZ(), 0, 0, 0);
            }
            else {
                ((ServerLevel)world).sendParticles(ParticleTypes.SWEEP_ATTACK, entity.getX(), entity.getY() + 0.5 * entity.getBbHeight(), entity.getZ(), 1,0,0, 0, 0);
                ParticleChainUtil.summonELECSPARKFromTo((ServerLevel)world,attacker.getId(),entity.getId() );
            }
            world.playSound(attacker, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.NEUTRAL, 1, 1);
            double x =entity.getX();
            double y =entity.getY();
            double z =entity.getZ();
            List<LivingEntity> ls =world.getEntitiesOfClass(LivingEntity.class,new AABB(x-2-lvl,y-2-lvl,z-2-lvl,x+2+lvl,y+2+lvl,z+2+lvl));
            for (LivingEntity target : ls){
                if (target!=null&&target!=attacker){
                    target.invulnerableTime=0;
                    target.hurt(DamageSource.playerAttack(attacker),damage/2);
                    target.invulnerableTime=0;
                }
            }
            attacker.getCooldowns().addCooldown(tool.getItem(), 10);
        }
    }
    public static void createExoSlash(@NotNull Player player, Float damage, Vec3 deltamovement,int Slashcount){
        Level world =player.getLevel();
        exoSlashEntity entity =new exoSlashEntity(etshtinkerEntity.exoslash.get(), world);
        entity.setPos(player.getX(),player.getEyeY(),player.getZ());
        entity.setDeltaMovement(deltamovement);
        entity.setDamage(damage);
        entity.setOwner(player);
        entity.setNoGravity(true);
        entity.count=Slashcount;
        world.addFreshEntity(entity);
    }

}
