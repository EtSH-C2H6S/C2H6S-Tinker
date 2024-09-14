package com.c2h6s.etshtinker.Items;

import com.c2h6s.etshtinker.init.etshtinkerTab;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class meteoralloy_block extends BlockItem {
    public meteoralloy_block(Block block, Properties properties) {
        super(block, properties.tab(etshtinkerTab.BLOCKS));
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, Level world, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            list.add(Component.translatable("etshtinker.item.tooltip.meteoralloy_block1").withStyle(ChatFormatting.YELLOW));
            list.add(Component.translatable("etshtinker.item.tooltip.meteoralloy_block2").withStyle(ChatFormatting.RED));
        }else {
            list.add(Component.translatable("etshtinker.item.tooltip.shift").withStyle(ChatFormatting.YELLOW));
        }
        super.appendHoverText(itemstack, world, list, flag);
    }
}
