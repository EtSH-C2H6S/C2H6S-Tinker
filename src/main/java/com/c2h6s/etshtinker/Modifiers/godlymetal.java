package com.c2h6s.etshtinker.Modifiers;

import cofh.core.init.CoreMobEffects;
import com.c2h6s.etshtinker.Entities.damageSources.playerThroughSource;
import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import mekanism.api.MekanismAPI;
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
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;


import static com.c2h6s.etshtinker.util.vecCalc.*;

public class godlymetal extends EtshModifieriii implements ToolDamageModifierHook {
    public static boolean enabled = ModList.get().isLoaded("mekanism");
    public static boolean enabled2 = ModList.get().isLoaded("cofh_core");

    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback){
        LivingEntity attacker =context.getAttacker();
        Entity entity =context.getTarget();
        if (entity instanceof LivingEntity target&&!(target instanceof Player)) {

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
        return baseKnockback;
    }
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        LivingEntity attacker =context.getAttacker();
        Entity entity =context.getTarget();
        if (entity instanceof LivingEntity target) {
            if ( attacker instanceof Player player && tool.getModifierLevel( this) > 0) {
                target.hurt(playerThroughSource.PlayerQuark(player,10), 10);
                if (target.getHealth()>=1) {
                    target.setHealth(Math.max(1, target.getHealth() - target.getHealth()*0.05f*modifier.getLevel()));
                }
            }
        }
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (holder instanceof Player player&&isCorrectSlot){
            int modilvl2 = modifier.getLevel();
            if (enabled2) {
                player.addEffect(new MobEffectInstance(CoreMobEffects.LIGHTNING_RESISTANCE.get(), 100, modilvl2, false, false));
                player.addEffect(new MobEffectInstance(CoreMobEffects.EXPLOSION_RESISTANCE.get(), 100, modilvl2, false, false));
                player.addEffect(new MobEffectInstance(CoreMobEffects.MAGIC_RESISTANCE.get(), 100, modilvl2, false, false));
            }
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 0, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,400,modilvl2,false,false));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,100,9*modilvl2,false,false));
        }
    }

    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (projectile instanceof AbstractArrow arrow&&target!=null&&attacker instanceof Player player&&!(target instanceof Player)) {
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

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.TOOL_DAMAGE);
    }

    @Override
    public int onDamageTool(IToolStackView iToolStackView, ModifierEntry modifierEntry, int i, @Nullable LivingEntity livingEntity) {
        return 0;
    }
}
