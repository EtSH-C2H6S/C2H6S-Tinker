package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.EtSTBaseModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.*;
import slimeknights.tconstruct.tools.TinkerModifiers;

public class TriChroma extends EtSTBaseModifier implements VolatileDataModifierHook {
    @Override
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        if (context.getTarget() instanceof LivingEntity living){
            damage+=Math.min(100,(living.getMaxHealth()- living.getHealth())*0.1f*modifier.getLevel());
        }
        return damage;
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getTarget() instanceof LivingEntity living){
            living.invulnerableTime=0;
            living.forceAddEffect(new MobEffectInstance(MobEffects.GLOWING,200*modifier.getLevel(),0),context.getAttacker());
            living.forceAddEffect(new MobEffectInstance(TinkerModifiers.enderferenceEffect.get(),200*modifier.getLevel(),0),context.getAttacker());
        }
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (target !=null){
            target.invulnerableTime=0;
            target.forceAddEffect(new MobEffectInstance(MobEffects.GLOWING,200*modifier.getLevel(),0),attacker);
            target.forceAddEffect(new MobEffectInstance(TinkerModifiers.enderferenceEffect.get(),200*modifier.getLevel(),0),attacker);
            target.invulnerableTime=0;
            target.hurt(DamageSource.indirectMobAttack(projectile,attacker),Math.min(100,(target.getMaxHealth()- target.getHealth())*0.1f*modifier.getLevel()));
        }
        return false;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.VOLATILE_DATA);
    }

    @Override
    public void addVolatileData(IToolContext iToolContext, ModifierEntry modifierEntry, ModDataNBT modDataNBT) {
        modDataNBT.addSlots(SlotType.UPGRADE,1);
        modDataNBT.addSlots(SlotType.ABILITY,1);
    }
}
