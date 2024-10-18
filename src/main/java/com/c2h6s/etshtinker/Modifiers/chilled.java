package com.c2h6s.etshtinker.Modifiers;

import cofh.core.init.CoreMobEffects;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class chilled extends etshmodifieriii {
    public static boolean enabled = ModList.get().isLoaded("cofh_core");
    public void modifierAfterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        Entity entity = context.getTarget();
        if (modifier.getLevel()>0&&entity instanceof LivingEntity target&&enabled){
            target.addEffect(new MobEffectInstance(CoreMobEffects.CHILLED.get(),200,2));
        }
    }
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (modifier.getLevel()>0&&target!=null&&enabled){
            target.addEffect(new MobEffectInstance(CoreMobEffects.CHILLED.get(),200,2));
        }
        return false;
    }
}
