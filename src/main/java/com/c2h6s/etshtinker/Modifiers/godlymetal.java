package com.c2h6s.etshtinker.Modifiers;

import cofh.core.init.CoreMobEffects;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import mekanism.api.MekanismAPI;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.util.OffhandCooldownTracker;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;


import static com.c2h6s.etshtinker.util.vecCalc.*;
import static com.c2h6s.etshtinker.util.getMainOrOff.*;

public class godlymetal extends etshmodifieriii implements ToolDamageModifierHook {
    public static boolean enabled = ModList.get().isLoaded("mekanism");
    public static boolean enabled2 = ModList.get().isLoaded("cofh_core");

    public float modifierBeforeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback){
        LivingEntity attacker =context.getAttacker();
        Entity entity =context.getTarget();
        if (entity instanceof LivingEntity target) {
            if (getMainLevel(attacker, this) > 0) {
                target.invulnerableTime = 0;
                target.hurt(DamageSource.explosion(attacker).bypassMagic().bypassArmor().bypassMagic(), 0.5f * damage);
                target.invulnerableTime = 0;
                target.hurt(DamageSource.MAGIC.bypassMagic().bypassArmor().bypassMagic(), 0.5f * damage);
                target.invulnerableTime = 0;
                if (enabled) {
                    MekanismAPI.getRadiationManager().radiate(target, 2000);
                    target.hurt(MekanismAPI.getRadiationManager().getRadiationDamageSource(), 0.5F * damage);
                    target.invulnerableTime = 0;
                }
                target.setNoGravity(true);
            }
        }
        return baseKnockback;
    }
    public void modifierAfterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        LivingEntity attacker =context.getAttacker();
        Entity entity =context.getTarget();
        if (entity instanceof LivingEntity target) {
            if ( attacker instanceof Player player && tool.getModifierLevel( this) > 0) {
                DamageSource dmg00 = DamageSource.playerAttack(player);
                dmg00.bypassArmor().bypassMagic().bypassInvul();
                target.hurt(dmg00, 10);
                if (target.getHealth()>=1) {
                    target.setHealth(Math.max(1, target.getHealth() - target.getHealth()*0.05f*modifier.getLevel()));
                }
            }
        }
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (holder instanceof Player player&&isCorrectSlot){
            int modilvl2 = getMainLevel(player,this);
            if (enabled2) {
                player.addEffect(new MobEffectInstance(CoreMobEffects.LIGHTNING_RESISTANCE.get(), 100, modilvl2, false, false));
                player.addEffect(new MobEffectInstance(CoreMobEffects.EXPLOSION_RESISTANCE.get(), 100, modilvl2, false, false));
                player.addEffect(new MobEffectInstance(CoreMobEffects.MAGIC_RESISTANCE.get(), 100, modilvl2, false, false));
            }
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 0, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,400,modilvl2,false,false));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,100,9*modilvl2,false,false));
            if (player.hasEffect(MobEffects.SLOW_FALLING)){
                player.removeEffect(MobEffects.SLOW_FALLING);
            }
        }
    }

    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (projectile instanceof AbstractArrow arrow&&target!=null&&attacker instanceof Player player&&getMainLevel(player, this)>0) {
            float damageDealt =(float) (arrow.getBaseDamage()*getMold(arrow.getDeltaMovement()));
            target.invulnerableTime =0;
            target.hurt(DamageSource.explosion(attacker).bypassMagic().bypassArmor().bypassMagic(),0.3F*damageDealt);
            target.invulnerableTime =0;
            target.hurt(DamageSource.MAGIC.bypassMagic().bypassArmor().bypassMagic(),0.3F*damageDealt);
            target.invulnerableTime =0;
            if (enabled) {
                MekanismAPI.getRadiationManager().radiate(target, 2000);
                target.hurt(MekanismAPI.getRadiationManager().getRadiationDamageSource(), 0.3F * damageDealt);
                target.invulnerableTime = 0;
            }
            target.setNoGravity(true);
            if (target.getHealth()>=1) {
                target.setHealth(Math.max(1, target.getHealth() - damageDealt));
            }
        }
        return false;
    }
    protected boolean canAttack(IToolStackView tool, Player player, InteractionHand hand) {
        return !tool.isBroken() && hand == InteractionHand.OFF_HAND && OffhandCooldownTracker.isAttackReady(player);
    }
    public InteractionResult beforeEntityUse(IToolStackView tool, ModifierEntry modifier, Player player, Entity target, InteractionHand hand, InteractionSource source) {
        if (this.canAttack(tool, player, hand)) {
            if (!player.level.isClientSide()) {
                ToolAttackUtil.attackEntity(tool, player, InteractionHand.OFF_HAND, target, ToolAttackUtil.getCooldownFunction(player, InteractionHand.OFF_HAND), false, source.getSlot(hand));
                float damage =(float)tool.getStats().getInt(ToolStats.ATTACK_DAMAGE);
                target.invulnerableTime = 0;
                target.hurt(DamageSource.explosion(player).bypassMagic().bypassArmor().bypassMagic(), 0.5f * damage);
                target.invulnerableTime = 0;
                target.hurt(DamageSource.MAGIC.bypassMagic().bypassArmor().bypassMagic(), 0.5f * damage);
                target.invulnerableTime = 0;
            }
            OffhandCooldownTracker.applyCooldown(player, source == InteractionSource.ARMOR ? 4.0F : (Float)tool.getStats().get(ToolStats.ATTACK_SPEED), 20);
            OffhandCooldownTracker.swingHand(player, InteractionHand.OFF_HAND, false);
            return InteractionResult.CONSUME;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public int onDamageTool(IToolStackView iToolStackView, ModifierEntry modifierEntry, int i, @Nullable LivingEntity livingEntity) {
        return 0;
    }
}
