package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.annihilateexplosionentity;
import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import com.c2h6s.etshtinker.init.EtshtinkerModifiers;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;


public class controllableannihl extends EtshModifieriii {

    public static float cachedDamage = 0;

    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        cachedDamage = damage;
        return knockback;
    }

    @Override
    public void postMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage) {
        if (cachedDamage>0&&context.isFullyCharged()&&context.getTarget() instanceof LivingEntity target&&context.getAttacker() instanceof Player player){
            int toolDamage = Math.min(modifier.getLevel()*10,modifier.getLevel()*((tool.getCurrentDurability()+tool.getDamage())/100000));
            if (tool.getDamage()<=tool.getCurrentDurability()&&toolDamage>0&&tool.getCurrentDurability()>toolDamage*10) {
                float percentage = toolDamage * 0.1f;
                if (tool.getModifierLevel(EtshtinkerModifiers.atomorigin_STATIC_MODIFIER.getId())>0) percentage*=2f;
                annihilateexplosionentity explode = new annihilateexplosionentity(etshtinkerEntity.annihilateexplosionentity.get(), target.getLevel());
                tool.setDamage(tool.getDamage() + (toolDamage*10));
                explode.damage = cachedDamage * percentage;
                explode.target = target;
                explode.setPos(target.getX(), target.getY() + 0.5 * target.getBbHeight(), target.getZ());
                explode.setOwner(player);
                target.level.addFreshEntity(explode);
            }
            cachedDamage=0;
        }
    }
}
