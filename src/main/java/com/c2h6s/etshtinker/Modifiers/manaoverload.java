package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import com.c2h6s.etshtinker.init.EtshtinkerModifiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import vazkii.botania.api.mana.ManaItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class manaoverload extends EtshModifieriii {

    public static int getTotalMana(Player player){
        ManaItemHandler handler = ManaItemHandler.instance();
        XplatAbstractions instance = XplatAbstractions.INSTANCE;
        if (handler !=null&&instance!=null) {
            List<ItemStack> items = handler.getManaItems(player);
            items.addAll(handler.getManaAccesories(player));
            AtomicInteger atomicInteger = new AtomicInteger(0);
            items.forEach(itemStack -> {
                ManaItem manaItem = instance.findManaItem(itemStack);
                if (manaItem!=null) atomicInteger.addAndGet(manaItem.getMana());
            });
            return atomicInteger.get();
        }
        return 0;
    }

    public static float getDamageBoost(Player player,IToolStackView tool){
        int modifierLevel = tool.getModifierLevel(EtshtinkerModifiers.manaoverload_STATIC_MODIFIER.get());
        int mana = getTotalMana(player);
        if (mana>5000&&modifierLevel>0){
            int usableMana = Math.min(getTotalMana(player),10000*modifierLevel);
            return usableMana/25f;
        }
        return 0;
    }

    @Override
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        if (context.getAttacker() instanceof Player player) {
            float boost = getDamageBoost(player,tool);
            if (boost>0){
                return damage + ManaItemHandler.instance().requestManaForTool(player.getItemInHand(context.getHand()),player,(int) (boost*5),true)/5f;
            }
        }
        return damage;
    }

    @Override
    public void modifierOnProjectileLaunch(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean primary) {
        if (livingEntity instanceof Player player&&abstractArrow!=null){
            float boost = getDamageBoost(player,tool);
            if (boost>0){
                abstractArrow.setBaseDamage( abstractArrow.getBaseDamage() + ManaItemHandler.instance().requestManaForTool(player.getItemInHand(player.getUsedItemHand()),player,(int) (boost*5),true)/5f);
            }
        }
    }

    public void addTooltip(IToolStackView tool, ModifierEntry modifierEntry, @org.jetbrains.annotations.Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player != null) {
            if (getTotalMana(player)>5000){
                int charge =Math.min(getTotalMana(player),100000*modifierEntry.getLevel());
                float boost = charge/500f;
                tooltip.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.manaenough")).withStyle(ChatFormatting.BLUE));
                tooltip.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.multiplier").append(String.format("%.1f",boost))).withStyle(ChatFormatting.AQUA));
                tooltip.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.manadrain").append(String.valueOf((int) (boost*10))).withStyle(ChatFormatting.DARK_AQUA)));
            }
        }
    }
}
