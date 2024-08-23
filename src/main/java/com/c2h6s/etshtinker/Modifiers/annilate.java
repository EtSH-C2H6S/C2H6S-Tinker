package com.c2h6s.etshtinker.Modifiers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import com.c2h6s.etshtinker.Modifiers.modifiers.*;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;



public class annilate extends etshmodifieriii  {
    public boolean isNoLevels() {
        return true;
    }
    private final ResourceLocation des = new ResourceLocation(MOD_ID, "des");
    public void onRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(des);
    }
    public float modifierBeforeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback){
        LivingEntity attacker =context.getAttacker();
        Entity target =context.getTarget();
        if (target instanceof LivingEntity&&tool.getModifierLevel(this)>0){
            int i =0;
            while (i<100){
                target.hurt(DamageSource.mobAttack(attacker),0.01f*((LivingEntity) target).getMaxHealth());
                attacker.hurt(DamageSource.OUT_OF_WORLD,0.01f*((LivingEntity) target).getMaxHealth());
                attacker.playSound(SoundEvents.ITEM_BREAK,1,1+0.01f*i);
                target.playSound(SoundEvents.FIREWORK_ROCKET_TWINKLE,1,1+0.01f*i);
                i++;
            }
            target.kill();
            attacker.setHealth(0);
            tool.getPersistentData().putInt(des,114514);
        }
        return knockback;
    }

    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity livingEntity, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (livingEntity!=null&&isCorrectSlot&&!tool.isBroken()&&modifier.getLevel()>0&&tool.getPersistentData().getInt(des)>0){
            tool.isBroken();
            tool.setDamage(2147483647);
            livingEntity.playSound(SoundEvents.ITEM_BREAK,1,1);
        }
    }
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        super.addTooltip(tool,modifier,player,list,TooltipKey.NORMAL,tooltipFlag);
        if (player != null&&tool.getPersistentData().getInt(des)>0) {
            list.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.cantfix")));
        };
    }
    public void modifierOnProjectileLaunch(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean primary) {
        if (tool.getModifierLevel(this)>0) {
            tool.getPersistentData().putInt(des, 114514);
        }
    }
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (target instanceof LivingEntity&&attacker!=null){
            int i =0;
            while (i<100){
                target.hurt(DamageSource.mobAttack(attacker),0.01f*((LivingEntity) target).getMaxHealth());
                attacker.hurt(DamageSource.OUT_OF_WORLD,0.01f*((LivingEntity) target).getMaxHealth());
                attacker.playSound(SoundEvents.ITEM_BREAK,1,1+0.01f*i);
                target.playSound(SoundEvents.FIREWORK_ROCKET_TWINKLE,1,1+0.01f*i);
                i++;
            }
            target.kill();
            attacker.kill();
        }
        return false;
    }
}
