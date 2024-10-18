package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class tightennerve extends etshmodifieriii {
    public boolean isNoLevels() {
        return true;
    }

    private final ResourceLocation adrenaline = new ResourceLocation(MOD_ID, "adrenaline");
    private final ResourceLocation nerv = new ResourceLocation(MOD_ID, "nerv");
    public void onRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(nerv);
    }

    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        ModDataNBT toolData = tool.getPersistentData();
        if (isSelected){
            if (toolData.getInt(adrenaline)>99) {
                toolData.putFloat(nerv, toolData.getFloat(nerv) + 0.05f);
            }
            if (toolData.getInt(adrenaline)<99&&toolData.getInt(nerv)>0) {
                toolData.putFloat(nerv, toolData.getFloat(nerv) - 0.05f);
            }
            if (toolData.getInt(nerv)>19){
                toolData.putFloat(adrenaline,50);
                toolData.putFloat(nerv,0);
                holder.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 3));
                holder.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 3));
                holder.hurt(DamageSource.OUT_OF_WORLD,5);
                holder.level.playSound((Player) null, holder.getX(), holder.getY(), holder.getZ(), SoundEvents.ELDER_GUARDIAN_CURSE, SoundSource.NEUTRAL, 0.7F, 1.0F);
            }
        }
        else {
            if (toolData.getInt(nerv)>0)
                toolData.putFloat(nerv, toolData.getFloat(nerv) - 0.1f);
        }
    }


    public void addTooltip(IToolStackView tool, ModifierEntry modifierEntry, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT toolData = tool.getPersistentData();
            tooltip.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.nervous").append(String.valueOf(toolData.getInt(nerv)+""))));
        }
    }

}
