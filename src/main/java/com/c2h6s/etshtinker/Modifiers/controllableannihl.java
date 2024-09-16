package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.annihilateexplosionentity;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.tools.item.tinker.ConstrainedPlasmaSaber;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ToolStats;


public class controllableannihl extends etshmodifieriii {
    public boolean isNoLevels() {
        return true;
    }
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage){
        LivingEntity attacker =context.getAttacker();
        Entity entity =context.getTarget();
        if (!tool.hasTag(TinkerTags.Items.DURABILITY)){
            return damage;
        }
        if (entity instanceof LivingEntity target) {
            if ( attacker instanceof Player player && modifier.getLevel()>0&&context.isFullyCharged()) {
                if (tool.getDamage() < tool.getStats().getInt(ToolStats.DURABILITY) * 0.75) {
                    annihilateexplosionentity explode = new annihilateexplosionentity(etshtinkerEntity.annihilateexplosionentity.get(), target.getLevel());
                    float d = tool.getCurrentDurability() * 0.25f;
                    tool.setDamage(tool.getDamage() + (int) d);
                    explode.damage = d * 4;
                    explode.target = target;
                    explode.setPos(target.getX(), target.getY() + 0.5 * target.getBbHeight(), target.getZ());
                    explode.setOwner(player);
                    target.level.addFreshEntity(explode);
                    return 0;
                }
            }
        }
        return 0;
    }
}
