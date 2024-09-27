package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import com.c2h6s.etshtinker.init.etshtinkerModifiers;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import static com.c2h6s.etshtinker.util.vecCalc.*;
import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class warpengine extends etshmodifieriii implements GeneralInteractionModifierHook {
    private final ResourceLocation warpdur = new ResourceLocation(MOD_ID, "warpdur");
    private static final ResourceLocation ACTIVE_MODIFIER = TConstruct.getResource("active_modifier");
    public void onRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(warpdur);
    }
    protected void registerHooks(ModuleHookMap.Builder builder){
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.GENERAL_INTERACT);
    }
    public InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand interactionHand, InteractionSource interactionSource) {
        if (interactionSource==InteractionSource.RIGHT_CLICK){
            if (player!=null) {
                GeneralInteractionModifierHook.startUsing(tool,modifier.getId(),player,interactionHand);
            }
            return InteractionResult.CONSUME;
        }
        else return InteractionResult.PASS;
    }

    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        if (tool.getPersistentData().getInt(warpdur)>0){
            return 1;
        }
        return 10;
    }

    public UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier) {
        return UseAnim.BOW;
    }

    public void onFinishUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity) {
        if (tool.getModifierLevel(this)>0&&entity instanceof Player player&&tool.getPersistentData().getInt(warpdur)<=0){
            tool.getPersistentData().putInt(warpdur,1);
            player.playSound(SoundEvents.BEACON_ACTIVATE,1,1);
            player.getCooldowns().addCooldown(player.getMainHandItem().getItem(),20);
        }
    }

    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (holder instanceof Player player&&tool.getModifierLevel(this)>0&&isCorrectSlot){
            if (tool.getPersistentData().getFloat(warpdur)>0) {
                if (tool.getPersistentData().getInt(warpdur) < tool.getModifierLevel(this) * 20 && !player.isShiftKeyDown()) {
                    tool.getPersistentData().putInt(warpdur, tool.getPersistentData().getInt(warpdur) + tool.getModifierLevel(this));
                }
                if (tool.getPersistentData().getInt(warpdur) > tool.getModifierLevel(this) * 20) {
                    tool.getPersistentData().putInt(warpdur, tool.getModifierLevel(this) * 20);
                }
                if (player.isShiftKeyDown() && tool.getPersistentData().getFloat(warpdur) >tool.getModifierLevel(this)+10) {
                    tool.getPersistentData().putFloat(warpdur, tool.getPersistentData().getFloat(warpdur)-tool.getModifierLevel(this));
                }
                if (player.isShiftKeyDown() && tool.getPersistentData().getFloat(warpdur) <=tool.getModifierLevel(this)+10) {
                    tool.getPersistentData().putInt(warpdur, 10+tool.getModifierLevel(this));
                }
                player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 5, 0));
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 5, 2));
                player.setDeltaMovement(player.getLookAngle().scale((double) tool.getPersistentData().getFloat(warpdur) / 40));
                player.level.addParticle(ParticleTypes.HAPPY_VILLAGER, player.getX(), player.getY() + 0.5 * player.getBbHeight(), player.getZ(), 0, 0, 0);
                if (!player.noPhysics){
                    player.noPhysics=true;
                }
                if (!player.isNoGravity()){
                    player.setNoGravity(true);
                }
            }
        }
        if (holder instanceof Player player&&player.swingTime == -1 && tool.getPersistentData().getFloat(warpdur) > 0&&isCorrectSlot) {
            if (tool.getModifierLevel(etshtinkerModifiers.warpingenhance_STATIC_MODIFIER.get())>0) {
                LivingEntity entity =getNearestLiEnt((float)player.getAttackRange(),player,player.level);
                if (entity!=null){
                    entity.invulnerableTime=0;
                    entity.hurt(DamageSource.playerAttack(player),tool.getStats().getInt(ToolStats.ATTACK_DAMAGE)*tool.getPersistentData().getInt(warpdur)*0.05f);
                    entity.invulnerableTime=0;
                }
            }
            tool.getPersistentData().putInt(warpdur, 0);
            player.playSound(SoundEvents.BEACON_DEACTIVATE, 1, 1);
        }
        if (tool.getPersistentData().getFloat(warpdur)<1&&holder instanceof Player player&&isCorrectSlot){
            if (player.noPhysics){
                player.noPhysics=false;
            }
            if (player.isNoGravity()){
                player.setNoGravity(false);
            }
        }
    }

    public void modifierOnUnequip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        if (tool.getPersistentData().getFloat(warpdur)>0) {
            tool.getPersistentData().putInt(warpdur, 0);
            if (context.getEntity() instanceof Player player) {
                if (player.noPhysics) {
                    player.noPhysics = false;
                }
                if (player.isNoGravity()) {
                    player.setNoGravity(false);
                }
            }
        }
    }

}
