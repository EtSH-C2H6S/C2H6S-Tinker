package com.c2h6s.etshtinker.Items;

import com.c2h6s.etshtinker.init.etshtinkerTab;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class sculk_energycore extends Item {

    public sculk_energycore(Properties p_41383_) {
        super(p_41383_.tab(etshtinkerTab.TOOLS).stacksTo(64).rarity(Rarity.RARE));
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, Level world, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        list.add(Component.translatable("etshtinker.item.tooltip.sculk_energycore1").withStyle(ChatFormatting.YELLOW));
        list.add(Component.translatable("etshtinker.item.tooltip.sculk_energycore2").withStyle(ChatFormatting.RED));
        super.appendHoverText(itemstack, world, list, flag);
    }
}
