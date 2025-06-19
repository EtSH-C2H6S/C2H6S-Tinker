package com.c2h6s.etshtinker.Modifiers.Armor;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
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
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class mentalism extends etshmodifieriii implements DamageBlockModifierHook {
    private static final TinkerDataCapability.TinkerDataKey<Integer> key = TConstruct.createKey("mentalism");
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addModule(new ArmorLevelModule(key, false, (TagKey)null));
        builder.addHook(this, ModifierHooks.DAMAGE_BLOCK);
    }
    private final ResourceLocation dpreventcd = new ResourceLocation(MOD_ID, "death_preventcd");
    private final ResourceLocation KEY_HURT_CD = new ResourceLocation(MOD_ID, "hurt_cd_mentalism");
    public void onRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(dpreventcd);
    }
    public mentalism(){
        MinecraftForge.EVENT_BUS.addListener(this::livingdeathevent);
    }

    private void livingdeathevent(LivingDeathEvent event) {
        LivingEntity entity =event.getEntity();
        entity.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
            int level = holder.get(key, 0);
            if (level > 0) {
                if (entity instanceof Player player ) {
                    List<ItemStack> equipments = player.getInventory().armor;
                    for (ItemStack equipment : equipments) {
                        if (equipment.getItem() instanceof ModifiableArmorItem) {
                            ToolStack tool = ToolStack.from(equipment);
                            if (tool.getPersistentData().getInt(dpreventcd) == 0) {
                                ModDataNBT toolData = tool.getPersistentData();
                                if (toolData.getInt(dpreventcd) == 0 && tool.getModifierLevel(this) > 0) {
                                    toolData.putInt(dpreventcd, 160);
                                    event.setCanceled(true);
                                    player.deathTime = -2;
                                    player.fallDistance = 0;
                                    player.setHealth(player.getMaxHealth() * 0.25f);
                                    player.invulnerableTime = 40;
                                    entity.sendSystemMessage(Component.translatable("etshtinker.message.death_prevent").withStyle(ChatFormatting.AQUA));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        });
    }


    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        ModDataNBT toolData = tool.getPersistentData();
        if(!level.isClientSide&&level.getGameTime()%20==0){
            if (toolData.getInt(dpreventcd)>0) toolData.putInt(dpreventcd,toolData.getInt(dpreventcd)-1);
            if (toolData.getInt(KEY_HURT_CD)>0) toolData.putInt(KEY_HURT_CD,toolData.getInt(KEY_HURT_CD)-1);
        }
    }

    public void addTooltip(IToolStackView tool, ModifierEntry modifierEntry, @Nullable Player player, List<Component> list, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT toolData = tool.getPersistentData();
            list.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.deadpreventcd").append(String.valueOf(toolData.getInt(dpreventcd)+""))));
        };
    }
    public Component getDisplayName(IToolStackView tool, ModifierEntry entry) {
        ModDataNBT toolData =tool.getPersistentData();
        if (toolData.getInt(dpreventcd)>0) {
            return Component.translatable(this.getDisplayName().getString()).append( "  " ).append( Component.translatable("etshtinker.modifier.tooltip.deadpreventcd").append(String.valueOf(toolData.getInt(dpreventcd))).withStyle(this.getDisplayName().getStyle()));
        }
        else return Component.translatable(this.getDisplayName().getString() + "  " ).append(Component.translatable( "etshtinker.modifier.tooltip.deadpreventready" ).withStyle(this.getDisplayName().getStyle()));
    }

    @Override
    public boolean isDamageBlocked(IToolStackView tool, ModifierEntry entry, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount) {
        ModDataNBT nbt = tool.getPersistentData();
        if (nbt.getInt(KEY_HURT_CD)<=0){
            if (source.getEntity() != null) {
                source.getEntity().invulnerableTime = 50;
            }
            nbt.putInt(KEY_HURT_CD,60);
        }
        return false;
    }
}
