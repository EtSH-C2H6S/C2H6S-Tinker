package com.c2h6s.etshtinker.Modifiers.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class EtSTBaseModifier extends EtshModifieriii {
    public static String KEY_CRIT_ARROW = "etshtinker_crit";
    @Override
    public void failedMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageAttempted) {
        this.postMeleeHit(tool,modifier,context,damageAttempted);
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        this.postMeleeHit(tool,modifier,context,damageDealt);
    }

    public void postMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage){}

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean primary) {
        if (abstractArrow!=null&&abstractArrow.isCritArrow()&&!abstractArrow.getTags().contains(KEY_CRIT_ARROW)) abstractArrow.addTag(KEY_CRIT_ARROW);
        super.onProjectileLaunch(tool, modifiers, livingEntity, projectile, abstractArrow, namespacedNBT, primary);
    }
}
