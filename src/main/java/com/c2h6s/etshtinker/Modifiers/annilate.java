package com.c2h6s.etshtinker.Modifiers;
import com.c2h6s.etshtinker.init.etshtinkerEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
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
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.RepairFactorModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import com.c2h6s.etshtinker.Modifiers.modifiers.*;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;



public class annilate extends etshmodifieriii implements RepairFactorModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this,ModifierHooks.REPAIR_FACTOR);
    }


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
        if (target instanceof LivingEntity living&&tool.getModifierLevel(this)>0){
            tool.getPersistentData().putInt(des, 114514);
            living.getPersistentData().putInt("annih_countdown",60);
            attacker.getPersistentData().putInt("annih_countdown",60);
            living.forceAddEffect(new MobEffectInstance(etshtinkerEffects.annihilating.get(),60,0,false,false),attacker);
            attacker.forceAddEffect(new MobEffectInstance(etshtinkerEffects.annihilating.get(),60,0,false,false),attacker);
        }
        return knockback;
    }

    @Override
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        return 1;
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
        if (target !=null&&attacker!=null){
            target.getPersistentData().putInt("annih_countdown",60);
            attacker.getPersistentData().putInt("annih_countdown",60);
            if (projectile!=null){
                projectile.discard();
            }
            target.forceAddEffect(new MobEffectInstance(etshtinkerEffects.annihilating.get(),60,0,false,false),attacker);
            attacker.forceAddEffect(new MobEffectInstance(etshtinkerEffects.annihilating.get(),60,0,false,false),attacker);
        }
        return true;
    }

    @Override
    public float getRepairFactor(IToolStackView iToolStackView, ModifierEntry modifierEntry, float v) {
        return 0.01f;
    }
}
