package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.awt.*;

public class test extends etshmodifieriii {
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (isCorrectSlot&&holder instanceof Player player){
            player.sendSystemMessage(Component.translatable(String.valueOf(player.getYRot())).withStyle(ChatFormatting.GREEN));
            player.sendSystemMessage(Component.translatable(String.valueOf(player.yRotO)).withStyle(ChatFormatting.GOLD));
            player.sendSystemMessage(Component.translatable(String.valueOf(player.getYHeadRot())).withStyle(ChatFormatting.YELLOW));
            player.sendSystemMessage(Component.translatable(String.valueOf(player.getXRot())));
            player.sendSystemMessage(Component.translatable(String.valueOf(player.xRotO)).withStyle(ChatFormatting.AQUA));
        }
    }
}
