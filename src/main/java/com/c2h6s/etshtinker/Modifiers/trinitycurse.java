package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.util.C;
import com.c2h6s.etshtinker.util.slotUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.DisplayNameModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.utils.RomanNumeralHelper;

import java.util.List;

public class trinitycurse extends etshmodifieriii implements VolatileDataModifierHook {
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.VOLATILE_DATA);
    }
    public trinitycurse(){
        MinecraftForge.EVENT_BUS.addListener(this::LivingHurt);
    }

    private void LivingHurt(LivingHurtEvent event) {
        LivingEntity entity =event.getEntity();
        if (entity!=null) {
            for (EquipmentSlot slot : slotUtil.ALL) {
                ItemStack stack = entity.getItemBySlot(slot);
                if (stack.getItem() instanceof IModifiable){
                    ToolStack tool =ToolStack.from(stack);
                    if (tool.getModifierLevel(this)>0){
                        event.setAmount(event.getAmount()*3);
                        return;
                    }
                }
            }
        }
    }

    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (modifier.getLevel()>0&&isCorrectSlot&&holder!=null) {
            Vec3 v =holder.getDeltaMovement();
            if (v.y>0){
                holder.setDeltaMovement(v.x,v.y*0.75,v.z);
            }
            if (holder.invulnerableTime > 0) {
                holder.invulnerableTime-=1+modifier.getLevel()/2;
                if (modifier.getLevel() == 3 && holder.invulnerableTime > 0) {
                    holder.invulnerableTime = 0;
                }
            }
        }
    }

    @Override
    public void addVolatileData(IToolContext context, ModifierEntry modifier, ModDataNBT volatileData) {
        volatileData.addSlots(SlotType.ABILITY,-1);
        volatileData.addSlots(SlotType.UPGRADE,-1);
        volatileData.addSlots(SlotType.SOUL,-1);
        if (context.hasTag(TinkerTags.Items.ARMOR)) {
            volatileData.addSlots(SlotType.DEFENSE, -1);
        }
    }

}
