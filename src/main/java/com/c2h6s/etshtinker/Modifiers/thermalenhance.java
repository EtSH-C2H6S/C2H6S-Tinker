package com.c2h6s.etshtinker.Modifiers;

import cofh.core.client.particle.options.BiColorParticleOptions;
import cofh.core.init.CoreParticles;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

import static com.c2h6s.etshtinker.util.thermalentityutil.*;
import static com.c2h6s.etshtinker.util.vecCalc.getScatteredVec3;

import cofh.core.init.CoreMobEffects;

public class thermalenhance extends etshmodifieriii {
    public static boolean enabled = ModList.get().isLoaded("cofh_core");
    public void modifierAfterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        LivingEntity attacker =context.getAttacker();
        Entity entity =context.getTarget();
        if (entity instanceof LivingEntity target&&!(target instanceof Player)) {
            if (enabled && attacker instanceof Player player) {
                int modilvl = modifier.getLevel();
                target.addEffect(new MobEffectInstance(CoreMobEffects.SHOCKED.get(),400,modilvl*2));
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,400,230));
                target.addEffect(new MobEffectInstance(CoreMobEffects.ENDERFERENCE.get(), 400, modilvl * 2, false, false));
                target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 400, 0, false, false));
                AttributeInstance attribute = target.getAttributes().getInstance(Attributes.ARMOR);
                if (attribute != null){
                    attribute.setBaseValue(attribute.getBaseValue()-0.25*target.getArmorValue());
                }
                if (context.isFullyCharged()) {
                    target.hurt(DamageSource.LIGHTNING_BOLT,5*modilvl);
                    if (target.level instanceof ServerLevel serverLevel){
                        Vec3 vec3 = getScatteredVec3(new Vec3(0,-3,0),8);
                        Vec3 start = target.position().add(new Vec3(0,target.getBbHeight()*0.5,0)).add(vec3.reverse());
                        Vec3 end = target.position().add(new Vec3(0,target.getBbHeight()*0.5,0)).add(vec3);
                        serverLevel.sendParticles(new BiColorParticleOptions(CoreParticles.STRAIGHT_ARC.get(),0.5f,5f,0f,-1, -240988),start.x,start.y,start.z,0,end.x,end.y,end.z,1);
                    }
                }
            }
        }
    }
    public float modifierBeforeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback){
        LivingEntity attacker =context.getAttacker();
        Entity entity =context.getTarget();
        if (entity instanceof LivingEntity target) {
            if (attacker instanceof Player player  && target != null) {
                target.invulnerableTime = 0;
                target.hurt(DamageSource.playerAttack(player), Math.min(40,(target.getMaxHealth()- target.getHealth())*0.1f*modifier.getLevel()));
                target.invulnerableTime = 0;
            }
        }
        return baseKnockback;
    }
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if(enabled&&attacker instanceof Player player&&target!=null&&projectile instanceof AbstractArrow arrow&&!(target instanceof Player)){
            int modilvl = modifiers.getLevel(this.getId());
            target.addEffect(new MobEffectInstance(CoreMobEffects.SHOCKED.get(),400,modilvl*2));
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,400,230));
            target.addEffect(new MobEffectInstance(CoreMobEffects.ENDERFERENCE.get(),400,modilvl*2));
            target.addEffect(new MobEffectInstance(MobEffects.GLOWING,400,1));
            AttributeInstance attribute = target.getAttributes().getInstance(Attributes.ARMOR);
            if (attribute != null){
                attribute.setBaseValue(attribute.getBaseValue()-0.75*target.getArmorValue());
            }
            arrow.setBaseDamage(arrow.getBaseDamage()+Math.min(40,(target.getMaxHealth()- target.getHealth())*0.1*modifiers.getLevel(this.getId())));
            target.hurt(DamageSource.LIGHTNING_BOLT,5*modilvl);
            if (target.level instanceof ServerLevel serverLevel){
                Vec3 vec3 = getScatteredVec3(new Vec3(0,-3,0),8);
                Vec3 start = target.position().add(new Vec3(0,target.getBbHeight()*0.5,0)).add(vec3.reverse());
                Vec3 end = target.position().add(new Vec3(0,target.getBbHeight()*0.5,0)).add(vec3);
                serverLevel.sendParticles(new BiColorParticleOptions(CoreParticles.STRAIGHT_ARC.get(),0.5f,5f,0f,-1, -240988),start.x,start.y,start.z,0,end.x,end.y,end.z,1);
            }
            target.invulnerableTime=0;
        }
        return false;
    }
}
