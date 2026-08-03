package com.c2h6s.etshtinker.Modifiers;

import cofh.core.client.particle.options.CylindricalParticleOptions;
import cofh.core.init.CoreParticles;
import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import com.hoshino.cti.client.CtiParticleType;
import com.hoshino.cti.content.entityTicker.EntityTickerInstance;
import com.hoshino.cti.content.entityTicker.EntityTickerManager;
import com.hoshino.cti.register.CtiEntityTickers;
import com.hoshino.cti.util.DamageSourceUtil;
import com.hoshino.cti.util.ParticleContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

import java.util.concurrent.atomic.AtomicBoolean;



public class thermalstrike extends EtshModifieriii {
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback){
        if (context.isFullyCharged())
            elementalExplode(tool,context.getAttacker(),context.getLivingTarget(),modifier.getLevel(),damage*0.1f*modifier.getLevel());
        return baseKnockback;
    }

    public static void elementalExplode(@Nullable IToolStackView tool, LivingEntity attacker,@Nullable LivingEntity target, int level, float damage){
        if (!(attacker.level instanceof ServerLevel serverLevel)||target==null) return;
        AtomicBoolean hasCharge = new AtomicBoolean();
        attacker.level.getEntitiesOfClass(LivingEntity.class,new AABB(target.position().add(-level*2,-level,-level*2),target.position().add(level*2,level,level*2)),living ->
                !(living instanceof Player)&&living.isAlive()&&living!=attacker).forEach(living -> {
                    if (tool!=null &&etshmodifierfluxed.getEnergyStored(tool) >= 1000) {
                        living.invulnerableTime = 0;
                        living.hurt(DamageSourceUtil.sourced(DamageSource.LIGHTNING_BOLT.bypassMagic(), attacker, attacker), damage);
                        etshmodifierfluxed.removeEnergy(tool,1000,false,false);
                        if (!hasCharge.get())
                            hasCharge.set(true);
                    }
                    living.invulnerableTime = 0;
                    living.hurt(DamageSourceUtil.sourced(DamageSource.FREEZE,attacker,attacker),damage);
                    if (living.distanceTo(target)<=1+0.5f*level) {
                        EntityTickerManager.getInstance(target).addTicker(new EntityTickerInstance(CtiEntityTickers.FIERY.get(), 5 * level, 200 * level),
                                Integer::max, Integer::max);
                        living.invulnerableTime = 0;
                        living.hurt(DamageSource.mobAttack(attacker).bypassArmor(),damage*0.5f);
                        living.invulnerableTime = 0;
                        living.hurt(DamageSourceUtil.sourced(DamageSource.ON_FIRE,attacker,attacker),damage);
                        AttributeInstance attribute = living.getAttributes().getInstance(Attributes.ARMOR);
                        if (attribute != null){
                            attribute.setBaseValue(attribute.getBaseValue()-0.1*living.getArmorValue()*level);
                        }
                    }

        });
        if (hasCharge.get())
            ParticleContext.buildParticle(etshtinkerParticleType.LIGHTNING_STRIKE.get())
                    .setPos(target.position().add(0,0.5f*target.getBbHeight(),0))
                    .setVelocity(0+RANDOM.nextFloat()*4-2,16,0+RANDOM.nextFloat()*4-2).build().sendToClient(serverLevel);
        ParticleContext.buildParticle(CtiParticleType.FIERY_EXPLODE.get())
                .setPos(target.position().add(0,0.5f*target.getBbHeight(),0))
                .setVelocity(0,0,0).build().sendToClient(serverLevel);
        CylindricalParticleOptions options = new CylindricalParticleOptions(CoreParticles.BLAST_WAVE.get(), 4*level, 10, 1.5f+0.5f*level);
        ParticleContext.buildParticle(options)
                .setPos(target.position().add(0,0.5f*target.getBbHeight(),0))
                .setVelocity(0,0,0).build().sendToClient(serverLevel);
        serverLevel.sendParticles(CoreParticles.FROST.get(),target.getX(),target.getY()+0.5f*target.getBbHeight(),target.getZ(),20,0,0,0,0.25);
        serverLevel.playSound(null,target, SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.PLAYERS,1.5f,1.5f);
    }

    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, LivingEntity attacker, LivingEntity target) {
        if (projectile instanceof AbstractArrow arrow&&arrow.isCritArrow()&&attacker!=null)
            elementalExplode(null,attacker,target,modifier.getLevel(), (float) (arrow.getBaseDamage()*0.1f*modifier.getLevel()));
        return false;
    }
}
