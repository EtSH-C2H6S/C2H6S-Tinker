package com.c2h6s.etshtinker.Items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ToolTipedItem extends Item {
    public final List<Component> ls;
    public ToolTipedItem(Properties p_41383_,List<Component> list) {
        super(p_41383_.rarity(Rarity.EPIC));
        this.ls=list;
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        list.addAll(ls);
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
    }
}
