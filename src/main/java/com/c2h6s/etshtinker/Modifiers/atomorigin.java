package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerToolStats;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper;
import slimeknights.tconstruct.library.tools.nbt.*;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class atomorigin extends etshmodifieriii implements ToolStatsModifierHook, VolatileDataModifierHook, ToolDamageModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.TOOL_STATS,ModifierHooks.VOLATILE_DATA,ModifierHooks.TOOL_DAMAGE);
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (modifier.getLevel()>0&&isCorrectSlot&&holder!=null) {
            int slotamount =tool.getVolatileData().getSlots(SlotType.ABILITY)+tool.getVolatileData().getSlots(SlotType.UPGRADE)+tool.getVolatileData().getSlots(SlotType.DEFENSE)+tool.getVolatileData().getSlots(SlotType.SOUL);
            if (slotamount>10){
                holder.addEffect(new MobEffectInstance(MobEffects.SATURATION,20,4,false,false));
                holder.addEffect(new MobEffectInstance(MobEffects.LUCK,20,4,false,false));
                holder.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,20,2,false,false));
                if (slotamount>25){
                    holder.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED,20,4,false,false));
                    holder.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,20,4,false,false));
                    holder.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,20,49,false,false));
                    holder.addEffect(new MobEffectInstance(MobEffects.REGENERATION,20,9,false,false));
                }
            }
        }
    }
    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        List<ModifierEntry> ls =context.getModifierList();
        int slotamount =0;
        for (ModifierEntry entry :ls){
            if (entry!=null){
                slotamount+=entry.getLevel();
            }
        }
        ToolStats.DURABILITY.multiply(builder, Math.pow(1.3, slotamount));
        ToolStats.ATTACK_SPEED.multiply(builder, Math.pow(1.3, slotamount));
        ToolStats.ATTACK_DAMAGE.multiply(builder, Math.pow(1.3, slotamount));
        ToolStats.ACCURACY.multiply(builder, Math.pow(1.3, slotamount));
        ToolStats.DRAW_SPEED.multiply(builder, Math.pow(1.3, slotamount));
        ToolStats.MINING_SPEED.multiply(builder, Math.pow(1.3, slotamount));
        ToolStats.ARMOR.multiply(builder, Math.pow(1.3, slotamount));
        ToolStats.ARMOR_TOUGHNESS.multiply(builder, Math.pow(1.3, slotamount));
        ToolStats.PROJECTILE_DAMAGE.multiply(builder, Math.pow(1.3, slotamount));
        ToolStats.KNOCKBACK_RESISTANCE.multiply(builder, Math.pow(1.3, slotamount));
        ToolStats.BLOCK_AMOUNT.multiply(builder, Math.pow(1.3, slotamount));
        ToolStats.BLOCK_ANGLE.multiply(builder, Math.pow(1.3, slotamount));
        etshtinkerToolStats.PLASMARANGE.multiply(builder, Math.pow(1.3, slotamount));
        etshtinkerToolStats.ENERGY_STORE.multiply(builder, Math.pow(1.3, slotamount));
        ToolTankHelper.CAPACITY_STAT.multiply(builder, Math.pow(1.3, slotamount));
    }

    @Override
    public void addVolatileData(IToolContext tool, ModifierEntry modifier, ModDataNBT volatileData) {
        volatileData.addSlots(SlotType.ABILITY,1);
        volatileData.addSlots(SlotType.UPGRADE,1);
        volatileData.addSlots(SlotType.DEFENSE,1);
        if (tool.hasTag(TinkerTags.Items.ARMOR)) {
            volatileData.addSlots(SlotType.DEFENSE, 1);
        }
        else {
            volatileData.addSlots(SlotType.UPGRADE,1);
        }
    }
    public int getPriority() {
        return 512;
    }
    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int i, @Nullable LivingEntity livingEntity) {
        if (modifier.getLevel()>0){
            int slotamount =tool.getVolatileData().getSlots(SlotType.ABILITY)+tool.getVolatileData().getSlots(SlotType.UPGRADE)+tool.getVolatileData().getSlots(SlotType.DEFENSE)+tool.getVolatileData().getSlots(SlotType.SOUL);
            if (slotamount>10) {
                return 0;
            }
        }
        return i;
    }
    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifierEntry, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player!=null){
            List<ModifierEntry> ls =tool.getModifierList();
            int slotamount =0;
            for (ModifierEntry entry :ls){
                if (entry!=null){
                    slotamount+=entry.getLevel();
                }
            }
            float multiplier =(float) Math.pow(1.3,slotamount)*0.005f;
            if (multiplier<1){
                tooltip.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.marconotenough").withStyle(ChatFormatting.DARK_RED)));
            }
            if (multiplier>1){
                tooltip.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.marconenough").withStyle(ChatFormatting.GOLD)));
                tooltip.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.marcomulti").append(" : ").append(String.format("%.01f", multiplier)).withStyle(ChatFormatting.LIGHT_PURPLE)));
            }
        }
    }
}
