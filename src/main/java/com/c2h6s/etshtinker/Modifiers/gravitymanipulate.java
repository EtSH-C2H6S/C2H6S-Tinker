package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class gravitymanipulate extends EtshModifieriii {
    public boolean isNoLevels() {
        return true;
    }

    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        Entity entity =context.getTarget();
        if (entity instanceof LivingEntity living){
            living.addEffect(new MobEffectInstance(etshtinkerEffects.hi_gravity.get(),400,0,false,false));
            if (!living.isNoGravity()&&!(living instanceof Player)){
                living.setDeltaMovement(0,-0.2,0);
            }
        }
        return knockback;
    }



    @Override
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (target !=null){
            target.addEffect(new MobEffectInstance(etshtinkerEffects.hi_gravity.get(),400,0,false,false));
            if (!target.isNoGravity()&&!(target instanceof Player)){
                target.setDeltaMovement(0,-0.2,0);
            }
        }
        return false;
    }
}
