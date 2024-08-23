package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.DurabilityDisplayModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.*;
import slimeknights.tconstruct.library.tools.stat.ToolStats;


import static com.c2h6s.etshtinker.etshtinker.MOD_ID;
import static com.c2h6s.etshtinker.util.getMainOrOff.*;

import java.util.List;

public class ragedangery extends etshmodifieriii implements DurabilityDisplayModifierHook {
    private final ResourceLocation ragedur = new ResourceLocation(MOD_ID, "ragedur");
    private final ResourceLocation ragevalue = new ResourceLocation(MOD_ID, "ragevalue");
    private final ResourceLocation rageatttime = new ResourceLocation(MOD_ID, "rageatttime");
    private final ResourceLocation sound2 = new ResourceLocation(MOD_ID, "sound2");
    public void onRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(ragedur);
        tool.getPersistentData().remove(ragevalue);
        tool.getPersistentData().remove(rageatttime);
        tool.getPersistentData().remove(sound2);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.DURABILITY_DISPLAY);
    }



    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        ModDataNBT toolData = tool.getPersistentData();
        if (isSelected){
            if (toolData.getInt(ragevalue)<100&&toolData.getInt(ragedur)==0) {
                toolData.putFloat(ragevalue,toolData.getFloat(ragevalue)+0.2f);
            }
            if(toolData.getInt(ragedur)>0){
                toolData.putFloat(ragedur,toolData.getFloat(ragedur)-1f);
            }
            if(toolData.getInt(ragevalue)>99&&toolData.getInt(sound2)==0){
                holder.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP,1,2);
                toolData.putInt(sound2,1);
            }
            if (toolData.getInt(ragedur)<0&&toolData.getInt(ragevalue)<99&&toolData.getInt(sound2)==1){
                holder.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP,1,0.5f);
                toolData.putInt(sound2,0);
            }
        }
        else {
            if (toolData.getInt(ragevalue)>0) {
                toolData.putInt(ragevalue,toolData.getInt(ragevalue)-2);
            }
        }
        if(toolData.getInt(rageatttime)>9&&holder!=null){
            holder.setHealth(holder.getHealth()*0.8f);
            toolData.putInt(rageatttime,toolData.getInt(rageatttime)-3);
        }
    }
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        Level world = null;
        ModDataNBT toolData = null;
        if (attacker != null) {
            world = attacker.level;
            toolData = ToolStack.from(attacker.getMainHandItem()).getPersistentData();
        }
        if (projectile instanceof AbstractArrow arrow) {
            if (toolData !=null&&toolData.getInt(ragevalue) > 99 && target != null && attacker instanceof Player player&&world!=null) {
                attacker.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 120, 5));
                player.playSound( SoundEvents.ENDER_DRAGON_SHOOT, 0.7F, 1.0F);
                toolData.putFloat(ragevalue, 0);
                toolData.putFloat(rageatttime, 1);
                toolData.putFloat(ragedur, 200);
            }
            if (toolData !=null&&toolData.getInt(ragedur) > 0 && getMainLevel(attacker, this) > 0 && target != null) {
                target.invulnerableTime = 0;
                float arrowspeed = (float) Math.pow(Math.pow(arrow.getDeltaMovement().x, 2) + Math.pow(arrow.getDeltaMovement().y, 2) + Math.pow(arrow.getDeltaMovement().z, 2), 0.5);
                target.invulnerableTime = 0;
                target.hurt(DamageSource.arrow(arrow, attacker), (float) ((Math.pow(arrowspeed, 2) * arrow.getBaseDamage())));
                target.invulnerableTime = 0;
                toolData.putInt(rageatttime, toolData.getInt(rageatttime) + 1);
            }
        }
        return false;
    }
    public void addTooltip(IToolStackView tool, ModifierEntry modifierEntry, @org.jetbrains.annotations.Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT toolData = tool.getPersistentData();
            tooltip.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.ragevalue").append(String.valueOf( toolData.getInt(ragevalue)+"%"))));
        };
    }
    public Component getDisplayName(IToolStackView tool, ModifierEntry entry) {
        ModDataNBT toolData =tool.getPersistentData();
        if (toolData.getInt(ragedur)>0) {
            return Component.translatable(this.getDisplayName().getString() + " "  ).append(Component.translatable( "etshtinker.modifier.tooltip.rageduration" ).append( String.valueOf(toolData.getInt(ragedur))).withStyle(this.getDisplayName().getStyle()));
        }
        else return Component.translatable(this.getDisplayName().getString() + "  " ).append(Component.translatable( "etshtinker.modifier.tooltip.ragevalue"  ).append( String.valueOf(toolData.getInt(ragevalue) + "%")).withStyle(this.getDisplayName().getStyle()));
    }

    @Nullable
    @Override
    public Boolean showDurabilityBar(IToolStackView tool, ModifierEntry modifierEntry) {
        ModDataNBT toolData =tool.getPersistentData();
        if (toolData.getInt(ragevalue)>99) {
            return true;
        }
        else return tool.getDamage()>0;
    }

    @Override
    public int getDurabilityWidth(IToolStackView tool, ModifierEntry modifierEntry) {
        int max = tool.getStats().getInt(ToolStats.DURABILITY);
        int amount =tool.getCurrentDurability();
        return amount >= max ? 13 : 1 + 13 * (amount - 1) / max;
    }

    @Override
    public int getDurabilityRGB(IToolStackView tool, ModifierEntry modifierEntry) {
        ModDataNBT toolData =tool.getPersistentData();
        return toolData.getInt(ragevalue)>0 ? 0x55FFFF : - 1;
    }
}
