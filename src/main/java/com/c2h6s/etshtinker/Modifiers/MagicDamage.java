package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class MagicDamage extends etshmodifieriii {
    @Override
    public void modifierAfterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity entity = context.getLivingTarget();
        Player player =context.getPlayerAttacker();
        if (entity!=null){
            entity.invulnerableTime =0;
            entity.hurt(DamageSource.MAGIC,damageDealt*0.1f);
            if (player!=null){
                entity.setLastHurtByPlayer(player);
            }
        }
    }

    @Override
    public boolean isNoLevels() {
        return true;
    }
}
