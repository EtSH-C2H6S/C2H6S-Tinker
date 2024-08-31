package com.c2h6s.etshtinker.Items;

import com.c2h6s.etshtinker.init.etshtinkerTab;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NightsAlloyItem extends Item {
    public NightsAlloyItem(Properties p_41383_) {
        super(p_41383_.stacksTo(64).tab(etshtinkerTab.MATERIALS).fireResistant().rarity(Rarity.EPIC));
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, Level world, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        list.add(Component.translatable("etshtinker.item.tooltip.nights_alloy").withStyle(ChatFormatting.WHITE));
        list.add(Component.translatable("etshtinker.item.tooltip.nights_alloy2").withStyle(ChatFormatting.WHITE));
        list.add(Component.translatable("etshtinker.item.tooltip.nights_alloy3").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("etshtinker.item.tooltip.special").withStyle(ChatFormatting.LIGHT_PURPLE));
        list.add(Component.translatable("etshtinker.item.tooltip.special2").withStyle(ChatFormatting.LIGHT_PURPLE));
        super.appendHoverText(itemstack, world, list, flag);
    }
}
