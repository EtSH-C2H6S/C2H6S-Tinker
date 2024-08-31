package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerModifiers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

import static com.c2h6s.etshtinker.util.getMainOrOff.*;
import static com.c2h6s.etshtinker.util.vecCalc.getMold;

public class nightsenhance extends etshmodifieriii {
    public nightsenhance(){
        MinecraftForge.EVENT_BUS.addListener(this::onmobfindtarget);
        MinecraftForge.EVENT_BUS.addListener(this::onapplyeffect);
    }

    private void onapplyeffect(MobEffectEvent.Applicable event) {
        LivingEntity target =event.getEntity();
        if (target!=null&&(getMainLevel(target, etshtinkerModifiers.nightsenhance_STATIC_MODIFIER.get())>0||getOffLevel(target,etshtinkerModifiers.nightsenhance_STATIC_MODIFIER.get())>0)&&(event.getEffectInstance().getEffect()== MobEffects.DARKNESS||event.getEffectInstance().getEffect()== MobEffects.BLINDNESS)){
            event.setResult(Event.Result.DENY);
        }
    }

    private void onmobfindtarget(LivingChangeTargetEvent event) {
        LivingEntity target =event.getNewTarget();
        LivingEntity entity =event.getEntity();
        if (target!=null&&(getMainLevel(target, etshtinkerModifiers.nightsenhance_STATIC_MODIFIER.get())>0||getOffLevel(target,etshtinkerModifiers.nightsenhance_STATIC_MODIFIER.get())>0)){
            if (entity!=null&&entity.getMaxHealth()<=200) {
                event.setCanceled(true);
            }
            else if (entity instanceof Warden){
                event.setCanceled(true);
            }
        }
    }

    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (modifier.getLevel()>0&&holder!=null){
            holder.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,300,0,false,false));
        }
    }

    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage){
        int LightLevel =Math.max( context.getAttacker().level.getBrightness(LightLayer.BLOCK,context.getAttacker().blockPosition()) , context.getAttacker().level.getBrightness(LightLayer.SKY,context.getAttacker().blockPosition()));
        if (LightLevel >7){
            damage *=0.5f;
        }
        if (LightLevel <=7){
            damage*=modifier.getLevel();
        }
        return damage;
    }

    public float modifierBeforeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback){
        int LightLevel =context.getAttacker().level.getBrightness(LightLayer.BLOCK,context.getAttacker().blockPosition()) ;
        if (LightLevel >7&&context.getLivingTarget()!=null&&context.getPlayerAttacker()!=null){
            int i =0;
            while (i<modifier.getLevel()*2 -1) {
                context.getLivingTarget().invulnerableTime=0;
                context.getLivingTarget().hurt(DamageSource.playerAttack(context.getPlayerAttacker()), damage);
                i++;
            }
        }
        if (context.getPlayerAttacker()!=null){
            context.getPlayerAttacker().invulnerableTime=0;
        }
        return knockback;
    }
    public void modifierOnProjectileLaunch(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean primary) {
        if (abstractArrow!=null&&modifiers.getLevel()>0){
            abstractArrow.addTag("nightsarrow");
        }
    }
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (projectile instanceof AbstractArrow arrow&&modifier.getLevel()>0){
            if (arrow.getTags().contains("nightsarrow")&&attacker!=null){
                if (!arrow.getPersistentData().contains("basedmg")) {
                    arrow.getPersistentData().putDouble("basedmg", arrow.getBaseDamage());
                }
                int LightLevel =attacker.level.getBrightness(LightLayer.BLOCK,attacker.blockPosition()) ;
                if (LightLevel>7){
                    arrow.setBaseDamage(arrow.getPersistentData().getDouble("basedmg") *modifier.getLevel());
                }
                else {
                    arrow.setBaseDamage(arrow.getPersistentData().getDouble("basedmg")/2);
                    if (target!=null&&attacker instanceof Player player) {
                        int i = 0;
                        while (i < modifier.getLevel() * 2 - 1) {
                            target.invulnerableTime = 0;
                            target.hurt(DamageSource.playerAttack(player), (float)(arrow.getBaseDamage()*getMold(arrow.getDeltaMovement())));
                            i++;
                        }
                    }
                }
            }
        }
        return false;
    }

}
