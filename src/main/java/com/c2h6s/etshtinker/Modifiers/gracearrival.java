package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.item.armor.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import vazkii.botania.api.mana.ManaItemHandler;

import static com.c2h6s.etshtinker.util.modloaded.*;

public class gracearrival extends etshmodifieriii {
    public gracearrival(){
        MinecraftForge.EVENT_BUS.addListener(this::livinghealevent);
    }
    private void livinghealevent(LivingHealEvent event) {
        LivingEntity living = event.getEntity();
        if (BOTloaded&&living instanceof Player player){
            for (ItemStack stack:player.getInventory().armor){
                if (stack.getItem() instanceof ModifiableArmorItem){
                    ToolStack tool = ToolStack.from(stack);
                    if (tool.getModifierLevel(this)>0&& ManaItemHandler.INSTANCE.requestManaExactForTool(stack,player,400,true)){
                        event.setAmount(event.getAmount()*(1+0.2f*tool.getModifierLevel(this)));
                    }
                }
            }
        }
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (modifier.getLevel()>0&&isCorrectSlot&&BOTloaded&&holder instanceof Player player){
            if (player.level.getGameTime()%20==0) {
                if (player.getHealth()<player.getMaxHealth()) {
                    if (ManaItemHandler.INSTANCE.requestManaExactForTool(itemStack, player, 200, true)) {
                        player.heal(1);
                    }
                }
            }
        }
    }
}
