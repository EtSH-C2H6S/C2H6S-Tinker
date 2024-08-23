package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
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
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;


import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class mentalism extends etshmodifieriii {
    private static final TinkerDataCapability.TinkerDataKey<Integer> key = TConstruct.createKey("mindprotection");
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addModule(new ArmorLevelModule(key, false, (TagKey)null));
    }
    private final ResourceLocation dpreventcd = new ResourceLocation(MOD_ID, "dpreventcd");
    public void onRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(dpreventcd);
    }
    public mentalism(){
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
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
                        ToolStack tool = ToolStack.from(equipment);
                        if (tool.getPersistentData().getInt(dpreventcd) == 0) {
                            ModDataNBT toolData = tool.getPersistentData();
                            if (toolData.getInt(dpreventcd) == 0&&tool.getModifierLevel(this)>0) {
                                toolData.putInt(dpreventcd, 3200);
                                event.setCanceled(true);
                                player.deathTime = -2;
                                player.fallDistance = 0;
                                player.setHealth(player.getMaxHealth() * 0.25f);
                                player.invulnerableTime = 40;
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    private void livinghurtevent(LivingHurtEvent event) {
        LivingEntity entity =event.getEntity();
        entity.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
            int level = holder.get(key, 0);
            if (level > 0) {
                if (entity instanceof Player player ) {
                    List<ItemStack> equipments = player.getInventory().armor;
                    for (ItemStack equipment : equipments) {
                        ToolStack tool =ToolStack.from(equipment);
                        if (tool.getModifierLevel(this)>0&&!player.getCooldowns().isOnCooldown(equipment.getItem())) {
                            event.setCanceled(true);
                            player.getCooldowns().addCooldown(equipment.getItem(),600);
                            if (event.getSource().getEntity() !=null){
                                event.getSource().getEntity().invulnerableTime=20;
                            }
                            break;
                        }
                    }
                }
            }
        });
    }


    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        ModDataNBT toolData = tool.getPersistentData();
        if(holder instanceof Player player&&toolData.getInt(dpreventcd)>0){
            toolData.putInt(dpreventcd,toolData.getInt(dpreventcd)-1);
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
}
