package com.c2h6s.etshtinker.Modifiers.Armor;

import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.DamageBlockModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.item.armor.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;


import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;
import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class mentalism extends EtshModifieriii implements DamageBlockModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.DAMAGE_BLOCK);
    }
    private final ResourceLocation KEY_HURT_CD = new ResourceLocation(MOD_ID, "hurt_cd_mentalism");
    public void onRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(KEY_HURT_CD);
    }

    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        ModDataNBT toolData = tool.getPersistentData();
        if(!level.isClientSide&&level.getGameTime()%20==0){
            if (toolData.getInt(KEY_HURT_CD)>0) toolData.putInt(KEY_HURT_CD,toolData.getInt(KEY_HURT_CD)-1);
        }
    }

    public Component getDisplayName(IToolStackView tool, ModifierEntry entry) {
        ModDataNBT toolData =tool.getPersistentData();
        if (toolData.getInt(KEY_HURT_CD)>0) {
            return Component.translatable(this.getDisplayName().getString()).append( "  " ).append( Component.translatable("etshtinker.modifier.tooltip.dodge_cd").append(String.valueOf(toolData.getInt(KEY_HURT_CD))).withStyle(this.getDisplayName().getStyle()));
        }
        else return Component.translatable(this.getDisplayName().getString() + "  " ).append(Component.translatable( "etshtinker.modifier.tooltip.dodge_ready" ).withStyle(this.getDisplayName().getStyle()));
    }

    @Override
    public boolean isDamageBlocked(IToolStackView tool, ModifierEntry entry, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount) {
        ModDataNBT nbt = tool.getPersistentData();
        if (EtSHrnd().nextFloat()<0.15f) return true;
        if (nbt.getInt(KEY_HURT_CD)<=0){
            nbt.putInt(KEY_HURT_CD,30);
            return true;
        }
        return false;
    }
}
