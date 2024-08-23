package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
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

import static com.c2h6s.etshtinker.util.getMainOrOff.*;
import static com.c2h6s.etshtinker.util.thermalentityutil.*;

import cofh.core.init.CoreMobEffects;

public class thermalenhance extends etshmodifieriii {
    public static boolean enabled = ModList.get().isLoaded("cofh_core");

    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if(enabled&&holder instanceof Player player&&isCorrectSlot){
            int modilvl2 = getMainLevel(holder,this);
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,300,modilvl2,false,false));
            player.addEffect(new MobEffectInstance(CoreMobEffects.LIGHTNING_RESISTANCE.get(),300,modilvl2,false,false));
            player.addEffect(new MobEffectInstance(CoreMobEffects.EXPLOSION_RESISTANCE.get(),300,modilvl2,false,false));
            player.addEffect(new MobEffectInstance(CoreMobEffects.MAGIC_RESISTANCE.get(),300,modilvl2,false,false));
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,300,modilvl2,false,false));
        }
    }
    public void modifierAfterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        LivingEntity attacker =context.getAttacker();
        Entity entity =context.getTarget();
        if (entity instanceof LivingEntity target) {
            if (enabled && attacker instanceof Player player && getMainLevel(attacker, this) > 0 && entity != null) {
                int modilvl = getMainLevel(attacker, this);
                target.addEffect(new MobEffectInstance(CoreMobEffects.SHOCKED.get(),400,modilvl*2));
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,400,230));
                target.addEffect(new MobEffectInstance(CoreMobEffects.ENDERFERENCE.get(), 400, modilvl * 2, false, false));
                target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 400, 0, false, false));
                AttributeInstance attribute = target.getAttributes().getInstance(Attributes.ARMOR);
                if (attribute!=null){
                    attribute.setBaseValue(attribute.getBaseValue()-0.75*target.getArmorValue());
                }
                if (context.isFullyCharged()) {
                    summonElectricField(target.level, player, new Vec3(target.getX(), target.getY(), target.getZ()), 8, 120, modilvl);
                }
            }
        }
    }
    public float modifierBeforeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback){
        LivingEntity attacker =context.getAttacker();
        Entity entity =context.getTarget();
        if (entity instanceof LivingEntity target) {
            if (attacker instanceof Player player && getMainLevel(attacker, this) > 0 && target != null) {
                target.invulnerableTime = 0;
                target.hurt(DamageSource.playerAttack(player), (target.getMaxHealth() - target.getHealth()) * 0.1f * getMainLevel(player, this));
                target.invulnerableTime = 0;
            }
        }
        return baseKnockback;
    }
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if(enabled&&attacker instanceof Player player&&getMainLevel(attacker,this)>0&&target!=null&&projectile instanceof AbstractArrow arrow){
            int modilvl = getMainLevel(attacker,this);
            target.addEffect(new MobEffectInstance(CoreMobEffects.SHOCKED.get(),400,modilvl*2));
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,400,230));
            target.addEffect(new MobEffectInstance(CoreMobEffects.ENDERFERENCE.get(),400,modilvl*2));
            target.addEffect(new MobEffectInstance(MobEffects.GLOWING,400,1));
            AttributeInstance attribute = target.getAttributes().getInstance(Attributes.ARMOR);
            if (attribute!=null){
                attribute.setBaseValue(attribute.getBaseValue()-0.75*target.getArmorValue());
            }
            arrow.setBaseDamage(arrow.getBaseDamage()+(target.getMaxHealth()- target.getHealth())*0.1*modifiers.getLevel(this.getId()));
            summonElectricField(target.level,player,new Vec3(target.getX(),target.getY(),target.getZ()),8,120,modilvl*2);
        }
        return false;
    }
}
