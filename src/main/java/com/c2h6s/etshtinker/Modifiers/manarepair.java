package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import vazkii.botania.api.mana.ManaItemHandler;

import static com.c2h6s.etshtinker.util.modloaded.BOTloaded;

public class manarepair extends etshmodifieriii {
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (modifier.getLevel() > 0 && BOTloaded) {
            if (tool.getDamage()>0&&holder instanceof Player player){
                if (ManaItemHandler.INSTANCE.requestManaExactForTool(itemStack,player,200,true)){
                    tool.setDamage(tool.getDamage()-1);
                }
            }
        }
    }
}
