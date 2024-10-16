package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import cofh.core.init.CoreMobEffects;

import static com.c2h6s.etshtinker.util.thermalentityutil.summonArc;

public class shocked extends etshmodifieriii {
    public static boolean enabled = ModList.get().isLoaded("cofh_core");
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (modifier.getLevel()>0&&isSelected&&holder!=null&&enabled){
            holder.addEffect(new MobEffectInstance(CoreMobEffects.LIGHTNING_RESISTANCE.get(),200,4,false,false));
        }
    }
    public void modifierAfterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        Entity entity = context.getTarget();
        LivingEntity attacker =context.getAttacker();
        int lvl =modifier.getLevel();
        if (modifier.getLevel()>0&&entity instanceof LivingEntity target&&enabled&&attacker instanceof Player player){
            target.addEffect(new MobEffectInstance(CoreMobEffects.SHOCKED.get(),200,4));
            target.invulnerableTime =0;
            if (context.isFullyCharged()) {
                summonArc(target.level, target, target.getEyePosition(), 4, 5);
            }
        }
    }
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (modifier.getLevel()>0&&target!=null&&enabled){
            target.addEffect(new MobEffectInstance(CoreMobEffects.SHOCKED.get(),200,4));
            summonArc(target.level, target, target.getEyePosition(), 4, 5);
        }
        return false;
    }
}
