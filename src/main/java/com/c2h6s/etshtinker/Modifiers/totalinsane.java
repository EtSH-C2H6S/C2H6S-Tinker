package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.damageSources.playerThroughSource;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.DurabilityDisplayModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;


import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;
import static com.c2h6s.etshtinker.util.getMainOrOff.*;

public class totalinsane extends etshmodifieriii implements DurabilityDisplayModifierHook {
    private final ResourceLocation insanity = new ResourceLocation(MOD_ID, "insanity");
    private final ResourceLocation fullcharged = new ResourceLocation(MOD_ID, "fullcharged");
    public totalinsane(){
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.DURABILITY_DISPLAY);
    }

    public void onRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(fullcharged);
        tool.getPersistentData().remove(insanity);
    }
    public void addTooltip(IToolStackView tool, ModifierEntry modifierEntry, @org.jetbrains.annotations.Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT toolData = tool.getPersistentData();
            tooltip.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.insanity").append(String.valueOf(toolData.getInt(insanity)+""))));
        }
    }
    public Component getDisplayName(IToolStackView tool, ModifierEntry entry) {
        ModDataNBT toolData =tool.getPersistentData();
        return Component.translatable(  this.getDisplayName().getString()+"  ").append(Component.translatable("etshtinker.modifier.tooltip.charge").append(String.valueOf( toolData.getInt(insanity)))).withStyle(this.getDisplayName().getStyle());
    }

    private void livinghurtevent(LivingHurtEvent event) {
        LivingEntity target =event.getEntity();
        Entity entity1 =event.getSource().getEntity();
        if (target instanceof Player&&entity1!=null){
            if (getMainLevel(target,this)>0){
                target.setHealth(0);
            }
            else if (getOffLevel(target,this)>0){
                target.setHealth(0);
            }
        }
        if (entity1 instanceof Player attacker&& target !=null){
            if (event.getSource() instanceof playerThroughSource){
                return;
            }
            InteractionHand hand =attacker.getUsedItemHand();
            ToolStack tool =ToolStack.from(attacker.getItemInHand(hand));
            if (tool.getModifierLevel(this)>0&&!tool.isBroken()){
                ModDataNBT toolData = tool.getPersistentData();
                if (toolData.getInt(insanity)>=500){
                    target.setLastHurtByPlayer(attacker);
                    target.setHealth(Math.max(1, target.getHealth()-(float) Math.pow((toolData.getInt(insanity)*0.01f-4),4)*tool.getStats().getInt(ToolStats.ATTACK_DAMAGE)*0.5F));
                    target.invulnerableTime=0;
                    target.hurt(playerThroughSource.PlayerPierce(attacker,(float) Math.pow((toolData.getInt(insanity)*0.01f-4),4)*tool.getStats().getInt(ToolStats.ATTACK_DAMAGE)*0.5F),(float) Math.pow((toolData.getInt(insanity)*0.01f-4),4)*tool.getStats().getInt(ToolStats.ATTACK_DAMAGE)*0.5F);
                    toolData.putInt(insanity,0);
                    toolData.putInt(fullcharged,0);
                    event.setCanceled(true);
                    attacker.getPersistentData().putInt("etshtinker.death_prevent",attacker.getPersistentData().getInt("etshtinker.death_prevent")<=0?1:attacker.getPersistentData().getInt("etshtinker.death_prevent"));
                }
                if (toolData.getInt(insanity)<500){
                    event.setCanceled(true);
                }
            }
        }
    }


    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        ModDataNBT toolData =tool.getPersistentData();
        if (!tool.isBroken()) {
            if (toolData.getInt(fullcharged) == 0 && isCorrectSlot) {
                toolData.putInt(insanity, toolData.getInt(insanity) + 5);
            }
            if (toolData.getInt(insanity) > 0 && !isCorrectSlot) {
                toolData.putInt(insanity, toolData.getInt(insanity) - 2);
            }
            if (toolData.getInt(insanity) > 605 && tool.getDamage() > 0) {
                toolData.putInt(insanity, toolData.getInt(insanity) - 5);
                tool.setDamage(tool.getDamage() - 1);
            }
            if (toolData.getInt(insanity) >= 1000&&toolData.getInt(fullcharged)==0) {
                toolData.putInt(insanity, 1000);
                toolData.putInt(fullcharged, 1);
                if (holder != null) {
                    holder.playSound(SoundEvents.PLAYER_LEVELUP, 1, 2);
                }
            }
            if (toolData.getInt(insanity) < 1000) {
                toolData.putInt(fullcharged, 0);
            }
            if (toolData.getInt(insanity) < 0) {
                toolData.putInt(insanity, 0);
            }
        }
    }
    public Boolean showDurabilityBar(IToolStackView tool, ModifierEntry modifier) {
        ModDataNBT toolData =tool.getPersistentData();
        if (toolData.getInt(insanity)>0) {
            return true;
        }
        else return tool.getDamage()>0;
    }
    public int getDurabilityWidth(IToolStackView tool, ModifierEntry modifier) {
        ModDataNBT toolData =tool.getPersistentData();
        int max = tool.getStats().getInt(ToolStats.DURABILITY);
        int amount =tool.getCurrentDurability();
        if (toolData.getInt(insanity)>0) {
            return (int) Math.max( 13 * toolData.getInt(insanity) / 1000,1);
        }
        else return amount >= max ? 13 : 1 + 13 * (amount - 1) / max;
    }
    public int getDurabilityRGB(IToolStackView tool, ModifierEntry modifier) {
        ModDataNBT toolData =tool.getPersistentData();
        return toolData.getInt(insanity)>0 ? 0x9530FF : - 1;
    }
}
