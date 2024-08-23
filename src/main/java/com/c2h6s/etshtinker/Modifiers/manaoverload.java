package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.DurabilityDisplayModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.helper.ItemNBTHelper;

import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.*;
import static com.c2h6s.etshtinker.util.modloaded.BOTloaded;


public class manaoverload extends etshmodifieriii implements DurabilityDisplayModifierHook {
    private static final ResourceLocation manacharge = new ResourceLocation(MOD_ID, "manacharge");
    private static final ResourceLocation manaactivated = new ResourceLocation(MOD_ID, "manaactivated");
    public void onModifierRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(manacharge);
        tool.getPersistentData().remove(manaactivated);
    }
    public int ticks =0;
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.DURABILITY_DISPLAY);
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (modifier.getLevel()>0&&BOTloaded) {
            ModDataNBT toolData = tool.getPersistentData();
            ticks++;
            if (ticks == 10) {
                ticks=0;
                if ( holder instanceof Player player) {
                    List<ItemStack> Items = player.getInventory().items;
                    float mana = 0;
                    for (ItemStack stack : Items) {
                        int manaAmount = ItemNBTHelper.getInt(stack, "mana", 0);
                        if (manaAmount > 0) {
                            if (ItemNBTHelper.verifyExistance(stack,"manaBacklog")) {
                                if (tool.getDamage() > 0) {
                                    int repair = Math.min(10, Math.min(manaAmount / 100, tool.getDamage()));
                                    if (repair > 0 && ManaItemHandler.INSTANCE.requestManaExactForTool(Items.get(itemSlot), player, repair * 100, true)) {
                                        tool.setDamage(tool.getDamage() - repair);
                                    }
                                }
                            } else {
                                if (tool.getDamage() > 0) {
                                    int repair = Math.min(10, Math.min(manaAmount / 100, tool.getDamage()));
                                    if (repair > 0) {
                                        ItemNBTHelper.setInt(stack, "mana", ItemNBTHelper.getInt(stack, "mana", 0) - 100 * repair);
                                        tool.setDamage(tool.getDamage() - repair);
                                    }
                                }
                            }
                            if (mana < (float) Integer.MAX_VALUE * 10f) {
                                mana += manaAmount;
                            }else break;
                        }
                    }
                    toolData.putInt(manacharge, (int) (mana / 10));
                }
            }
        }
    }
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage){
        ModDataNBT toolData =tool.getPersistentData();
        if (modifier.getLevel()>0&&context.getAttacker() instanceof Player player&&BOTloaded) {
            List<ItemStack> Items = player.getInventory().items;
            float mana = 0;
            for (ItemStack stack : Items) {
                int manaAmount = ItemNBTHelper.getInt(stack, "mana", 0);
                if (manaAmount > 0) {
                    if (mana < (float) Integer.MAX_VALUE * 10f) {
                        mana += manaAmount;
                    }else break;
                }
            }
            if (mana>=10000000) {
                float charge = mana / 10;
                int multiplier = (int) Math.pow((int) (Math.log10(charge)) - 3, 3);
                int drain = (int) Math.pow(10, (int) (Math.log10(charge) - 1));
                for (ItemStack stack : Items) {
                    int manaAmount = ItemNBTHelper.getInt(stack, "mana", 0);
                    int manadrain = Math.min(manaAmount, drain);
                    if (manaAmount > 0) {
                        if (ItemNBTHelper.verifyExistance(stack,"manaBacklog")) {
                            if (ManaItemHandler.instance().requestManaExactForTool(new ItemStack(tool.getItem()), player, manadrain, true)) {
                                drain -= manadrain;
                            }
                        } else {
                            ItemNBTHelper.setInt(stack, "mana", manaAmount - manadrain);
                            drain -= manadrain;
                        }
                    }
                    if (drain <= 0) {
                        break;
                    }
                }
                toolData.putInt(manacharge, (int) (mana / 10));
                return damage * Math.max(multiplier, 1);
            }
            toolData.putInt(manacharge, (int) (mana / 10));
        }
        return damage;
    }
    @Override
    public Boolean showDurabilityBar(IToolStackView tool, ModifierEntry modifier) {
        ModDataNBT toolData =tool.getPersistentData();
        if (toolData.getInt(manacharge)>0) {
            return true;
        }
        else return tool.getDamage()>0;
    }
    @Override
    public int getDurabilityWidth(IToolStackView tool, ModifierEntry modifier) {
        ModDataNBT toolData =tool.getPersistentData();
        int max = tool.getStats().getInt(ToolStats.DURABILITY);
        int amount =tool.getCurrentDurability();
        if (toolData.getInt(manacharge)>0) {
            if (toolData.getInt(manaactivated)<1e9) {
                return (int)(13 * Math.max( (double) toolData.getInt(manacharge) / (int) Math.pow(10,1+ (int) Math.log10(toolData.getInt(manacharge))), 0));
            }
            if (toolData.getInt(manaactivated)>=1e9){
                return (int) (13 * Math.max((double)  toolData.getInt(manacharge) /(double) Integer.MAX_VALUE, 0));
            }
        }
        return amount >= max ? 13 : 1 + 13 * (amount - 1) / max;
    }
    @Override
    public int getDurabilityRGB(IToolStackView tool, ModifierEntry modifier) {
        ModDataNBT toolData =tool.getPersistentData();
        return toolData.getInt(manacharge)>0 ? 0x35A9FF : - 1;
    }
    public void addTooltip(IToolStackView tool, ModifierEntry modifierEntry, @org.jetbrains.annotations.Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT toolData = tool.getPersistentData();
            tooltip.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.charge").append(String.valueOf(toolData.getInt(manacharge)))));
            if (toolData.getInt(manacharge)<1000000){
                tooltip.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.mananotenough")).withStyle(ChatFormatting.DARK_RED));
            }
            if (toolData.getInt(manacharge)>=1000000){
                int charge =toolData.getInt(manacharge);
                int multiplier = (int)Math.pow ((int)(Math.log10(charge)) - 3,3);
                tooltip.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.manaenough")).withStyle(ChatFormatting.BLUE));
                tooltip.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.multiplier").append(String.valueOf(multiplier))).withStyle(ChatFormatting.AQUA));
                tooltip.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.manadrain").append(String.valueOf((int) Math.pow(10,(int)(Math.log10(charge)-1))))).withStyle(ChatFormatting.DARK_AQUA));
            }
        }
    }
}
