package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.PoisonCloud;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

import javax.swing.text.html.parser.Entity;

public class AcidicPoisonModifier extends etshmodifieriii {
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.isFullyCharged()&&context.getTarget() instanceof Mob mob){
            PoisonCloud cloud =new PoisonCloud(etshtinkerEntity.poison_cloud.get(),mob.level);
            cloud.setOwner(context.getAttacker());
            cloud.lvl = modifier.getLevel();
            cloud.damage =0.5F* modifier.getLevel();
            cloud.setPos(mob.position().add(0,mob.getBbHeight()/2,0));
            mob.level.addFreshEntity(cloud);
        }
    }

    @Override
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (target instanceof Mob mob&& projectile instanceof AbstractArrow arrow&&arrow.isCritArrow()){
            PoisonCloud cloud =new PoisonCloud(etshtinkerEntity.poison_cloud.get(),mob.level);
            cloud.setOwner(attacker);
            cloud.lvl = modifier.getLevel();
            cloud.damage = modifier.getLevel();
            cloud.setPos(mob.position().add(0,mob.getBbHeight()/2,0));
            mob.level.addFreshEntity(cloud);
        }
        return false;
    }
}
