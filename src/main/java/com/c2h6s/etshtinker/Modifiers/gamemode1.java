package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameType;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class gamemode1 extends etshmodifieriii {
    public void modifierOnEquip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        LivingEntity entity=context.getEntity();
        if (entity instanceof ServerPlayer serverPlayer){
            serverPlayer.setGameMode(GameType.CREATIVE);
            serverPlayer.displayClientMessage(Component.translatable("已将自己的游戏模式改为创造模式"),false);
        }
    }
    public void modifierOnUnequip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        LivingEntity entity=context.getEntity();
        if (entity instanceof ServerPlayer serverPlayer){
            serverPlayer.setGameMode(GameType.SURVIVAL);
            serverPlayer.displayClientMessage(Component.translatable("已将自己的游戏模式改为生存模式"),false);
        }
    }


}
