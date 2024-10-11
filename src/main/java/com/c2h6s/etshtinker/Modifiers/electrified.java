package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEffects;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;


public class electrified extends etshmodifieriii {
    public void modifierAfterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        LivingEntity attacker =context.getAttacker();
        Entity entity =context.getTarget();
        if (entity instanceof LivingEntity target) {
            if (attacker instanceof Player player && tool.getModifierLevel(this) > 0 && target instanceof Mob) {
                int lvl000 = tool.getModifierLevel(this);
                target.playSound(SoundEvents.FIREWORK_ROCKET_TWINKLE, 1.2f, 1.2f);
                target.forceAddEffect(new MobEffectInstance(etshtinkerEffects.ionized.get(),100,2*lvl000,false,false),attacker);
                target.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 100, false, false), attacker);
            }
        }
    }

    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (attacker instanceof Player player&&modifiers.getLevel(this.getId())>0&&target instanceof Mob mob) {
            int lvl000 = modifiers.getLevel(this.getId());
            target.playSound(SoundEvents.FIREWORK_ROCKET_TWINKLE,1.2f,1.2f);
            target.forceAddEffect(new MobEffectInstance(etshtinkerEffects.ionized.get(),100,2*lvl000,false,false),attacker);
            target.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 100, false, false), attacker);
        }
        return false;
    }
}
