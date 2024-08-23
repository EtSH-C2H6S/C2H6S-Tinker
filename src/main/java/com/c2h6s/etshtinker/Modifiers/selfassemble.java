package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;
import static com.c2h6s.etshtinker.init.ItemReg.etshtinkerMekansimMaterial.anti_neutronium;

public class selfassemble extends etshmodifieriii {
    public boolean isNoLevels() {
        return true;
    }
    private final ResourceLocation repair = new ResourceLocation(MOD_ID, "repair");
    public void onModifierRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(repair);
    }
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT toolData = tool.getPersistentData();
            list.add(applyStyle(Component.translatable("material.etshtinker.anti_neutronium").append(String.valueOf( ":"+toolData.getInt(repair) ))));
        }
        super.addTooltip(tool,modifier,player,list,TooltipKey.UNKNOWN,tooltipFlag);
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        ModDataNBT toolData =tool.getPersistentData();
        if (tool.getDamage()>0){
            int damaged =Math.min(4096*modifier.getLevel(),tool.getDamage());
            if (toolData.getInt(repair)>damaged){
                toolData.putInt(repair,toolData.getInt(repair)-damaged);
                tool.setDamage(tool.getDamage()-damaged);
            }
            else if (toolData.getInt(repair)<=damaged&&holder instanceof Player player){
                for (int j = 0; j < player.getInventory().items.size(); j++) {
                    ItemStack stack = player.getInventory().getItem(j);
                    if (stack.getItem() == anti_neutronium.get() && stack.getCount() > 0) {
                        stack.setCount(stack.getCount()-1);
                        toolData.putInt(repair,toolData.getInt(repair)+8192*modifier.getLevel()-damaged);
                        tool.setDamage(tool.getDamage()-damaged);
                    }
                }
            }
        }
    }
}
