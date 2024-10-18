package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class gravitymanipulate extends etshmodifieriii {
    public boolean isNoLevels() {
        return true;
    }
    public void modifierOnEquip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        LivingEntity entity = context.getEntity();
        if (entity instanceof Player player&&!player.isCreative()) {
            player.getAbilities().flying = true;
            player.getAbilities().mayfly = true;
        }
    }

    public void modifierOnUnequip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        LivingEntity entity = context.getEntity();
        if (entity instanceof Player player&&!player.isCreative()) {
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
        }
    }

    @Override
    public float modifierBeforeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        Entity entity =context.getTarget();
        if (entity instanceof LivingEntity living){
            living.addEffect(new MobEffectInstance(etshtinkerEffects.hi_gravity.get(),200,0,false,false));
        }
        return knockback;
    }



    @Override
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (target !=null){
            target.addEffect(new MobEffectInstance(etshtinkerEffects.hi_gravity.get(),200,0,false,false));
        }
        return false;
    }
}
