package com.c2h6s.etshtinker.Modifiers.Armor;

import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;

import java.util.List;

public class reactiveexplosivearmor extends EtshModifieriii {
    private static final TinkerDataCapability.TinkerDataKey<Integer> key = TConstruct.createKey("reactiveexplosivearmor");
    public reactiveexplosivearmor(){
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addModule(new ArmorLevelModule(key, false, (TagKey)null));
    }
    private void livinghurtevent(LivingHurtEvent event) {
        if (event.getSource() instanceof EntityDamageSource entityDamageSource&&entityDamageSource.isThorns()){
            return;
        }
        if (event.getEntity()==event.getSource().getEntity()){
            return;
        }
        LivingEntity living = event.getEntity();
        living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
            int level = holder.get(key, 0);
            if (level > 0) {
                if (event.getSource().isExplosion()){
                    event.setCanceled(true);
                }
                if (event.isCanceled()||event.getAmount()<1){
                    return;
                }
                List<Mob> list = living.level.getEntitiesOfClass(Mob.class,living.getBoundingBox().inflate(8));
                for (Mob mob:list){
                    if (mob!=null&&!mob.getType().getCategory().isFriendly()){
                        mob.invulnerableTime=0;
                        mob.hurt(((EntityDamageSource)DamageSource.explosion(living)).setThorns(),event.getAmount());
                    }
                }
                living.playSound(SoundEvents.GENERIC_EXPLODE,1,1);
                if (living.level instanceof ServerLevel serverLevel){
                    serverLevel.sendParticles(ParticleTypes.EXPLOSION,living.getX(),living.getY()+0.5*living.getBbHeight(),living.getZ(),1,0,0,0,0);
                }
                event.setAmount(event.getAmount()*0.75f);
            }
        });
    }
}
