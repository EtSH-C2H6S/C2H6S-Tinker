package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.tools.item.tinker.ConstrainedPlasmaSaber;
import com.c2h6s.etshtinker.tools.item.tinker.IonizedCannon;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;


import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class ultradenseex extends etshmodifieriii implements GeneralInteractionModifierHook {
    private final ResourceLocation multiplier = new ResourceLocation(MOD_ID, "multiplier");
    public void onRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(multiplier);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this,ModifierHooks.GENERAL_INTERACT);
    }

    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        ModDataNBT toolData =tool.getPersistentData();
        if (!isCorrectSlot&&toolData.getFloat(multiplier)!=0f){
            toolData.putFloat(multiplier,0f);
        }
    }

    @Override
    public float modifierBeforeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        int z =0;
        float knockback0 =baseKnockback;
        while (z<tool.getPersistentData().getFloat(multiplier)) {
            for (ModifierEntry entry : tool.getModifierList()) {
                if (entry != modifier) {
                    knockback0 = entry.getHook(ModifierHooks.MELEE_HIT).beforeMeleeHit(tool, modifier, context, damage, knockback0,knockback0);
                }
            }
            z++;
        }
        return knockback0;
    }

    @Override
    public void modifierAfterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        int z =0;
        while (z<tool.getPersistentData().getFloat(multiplier)) {
            for (ModifierEntry entry : tool.getModifierList()) {
                if (entry != modifier) {
                    entry.getHook(ModifierHooks.MELEE_HIT).afterMeleeHit(tool, modifier, context, damageDealt);
                }
            }
            z++;
        }
        tool.getPersistentData().putFloat(multiplier,0);
    }

    @Override
    public InteractionResult onToolUse(IToolStackView iToolStackView, ModifierEntry modifierEntry, Player player, InteractionHand interactionHand, InteractionSource interactionSource) {
        if (!player.level.isClientSide&&iToolStackView.getPersistentData().getFloat(multiplier)<=2*modifierEntry.getLevel()&!player.getCooldowns().isOnCooldown(iToolStackView.getItem())) {
            GeneralInteractionModifierHook.startUsing(iToolStackView, modifierEntry.getId(), player, interactionHand);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onFinishUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity) {
        if (entity instanceof Player player&&player.level instanceof ServerLevel serverLevel) {
            ModDataNBT toolData = tool.getPersistentData();
            toolData.putFloat(multiplier, toolData.getFloat(multiplier) + 0.2f * modifier.getLevel());
            player.getCooldowns().addCooldown(tool.getItem(),15);
            serverLevel.sendParticles(ParticleTypes.WITCH,player.getX(),player.getY()+0.5*player.getBbHeight(),player.getZ(),20,0.2,0.2,0.2,0.4);
        }
    }

    @Override
    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 1;
    }

    @Override
    public UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier) {
        return UseAnim.SPEAR;
    }

    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT toolData = tool.getPersistentData();
            list.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.charge").append(String.valueOf(toolData.getInt(multiplier)))));
        }
        super.addTooltip(tool, modifier, player, list, tooltipKey, tooltipFlag);
    }
}
