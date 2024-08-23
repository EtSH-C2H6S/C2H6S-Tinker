package com.c2h6s.etshtinker.Items;

import com.c2h6s.etshtinker.init.etshtinkerTab;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class trinityalloyitem extends Item {
    public trinityalloyitem(Properties p_41383_) {
        super(p_41383_.stacksTo(64).tab(etshtinkerTab.MATERIALS).fireResistant());
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, Level world, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        list.add(Component.translatable("etshtinker.item.tooltip.trinityhint").withStyle(ChatFormatting.DARK_RED));
        list.add(Component.translatable("etshtinker.item.tooltip.csdy").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(itemstack, world, list, flag);
    }
}
