package com.c2h6s.etshtinker.Items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EnergizedSculkAlloyItem extends Item {

    public EnergizedSculkAlloyItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, Level world, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            list.add(Component.translatable("etshtinker.item.tooltip.energized_sculk_alloy").withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("etshtinker.item.tooltip.energized_sculk_alloy1").withStyle(ChatFormatting.DARK_AQUA));
            list.add(Component.translatable("etshtinker.item.tooltip.energized_sculk_alloy2").withStyle(ChatFormatting.GOLD));
        }else {
            list.add(Component.translatable("etshtinker.item.tooltip.shift").withStyle(ChatFormatting.YELLOW));
        }
        super.appendHoverText(itemstack, world, list, flag);
    }
}
