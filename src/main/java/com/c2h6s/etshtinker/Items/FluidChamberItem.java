package com.c2h6s.etshtinker.Items;

import com.c2h6s.etshtinker.init.etshtinkerTab;
import com.c2h6s.etshtinker.tools.stats.fluidChamberMaterialStats;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import slimeknights.mantle.client.SafeClientAccess;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.config.Config;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.helper.TooltipUtil;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;

import javax.annotation.Nullable;
import java.util.List;

public class FluidChamberItem extends ToolPartItem {
    private static final String MISSING_MATERIAL_KEY = TConstruct.makeTranslationKey("tooltip", "part.missing_material");
    private static final String MISSING_STATS_KEY = TConstruct.makeTranslationKey("tooltip", "part.missing_stats");
    public FluidChamberItem() {
        super(new Item.Properties().stacksTo(64).tab(etshtinkerTab.TOOLS), fluidChamberMaterialStats.ID);
    }

    @Override
    public boolean canUseMaterial(MaterialId material) {
        if (getStatType().canUseMaterial(material)) return true;
        return StatlessMaterialStats.BINDING.getIdentifier().canUseMaterial(material);
    }

    @Override
    public boolean canUseMaterial(IMaterial mat) {
        if (getStatType().canUseMaterial(mat.getIdentifier())) return true;
        return StatlessMaterialStats.BINDING.getIdentifier().canUseMaterial(mat.getIdentifier());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flag) {
        if (TooltipUtil.isDisplay(stack)) {
            return;
        }
        MaterialVariantId materialVariant = this.getMaterial(stack);
        MaterialId id = materialVariant.getId();
        if (!materialVariant.equals(IMaterial.UNKNOWN_ID)) {
            if (flag.isAdvanced()) {
                tooltip.add((Component.translatable(MATERIAL_KEY, materialVariant.toString())).withStyle(ChatFormatting.DARK_GRAY));
            }
            if (this.canUseMaterial(id)) {
                // add all valid traits
                TooltipKey key = SafeClientAccess.getTooltipKey();
                for (ModifierEntry entry : MaterialRegistry.getInstance().getTraits(id, this.getStatType())) {
                    if (!entry.isBound()) {
                        continue;
                    }
                    Component name = entry.getDisplayName();
                    if (flag.isAdvanced() && Config.CLIENT.modifiersIDsInAdvancedTooltips.get()) {
                        tooltip.add(Component.translatable(TooltipUtil.KEY_ID_FORMAT, name, Component.literal(entry.getModifier().getId().toString())).withStyle(ChatFormatting.DARK_GRAY));
                    } else {
                        tooltip.add(name);
                    }
                    if (key == TooltipKey.CONTROL) {
                        List<Component> description = entry.getModifier().getDescriptionList(entry.getLevel());
                        for (int i = 1; i < description.size(); i++) {
                            tooltip.add(description.get(i).plainCopy().withStyle(ChatFormatting.GRAY));
                        }
                    }
                }
                // add stats on shift
                if (key == TooltipKey.SHIFT || key == TooltipKey.UNKNOWN) {
                    MaterialRegistry.getInstance().getMaterialStats(id, this.getStatType()).ifPresentOrElse(stat -> {
                        List<Component> text = stat.getLocalizedInfo();
                        if (!text.isEmpty()) {
                            tooltip.add(Component.empty());
                            tooltip.add(stat.getLocalizedName().withStyle(ChatFormatting.WHITE, ChatFormatting.UNDERLINE));
                            tooltip.addAll(stat.getLocalizedInfo());
                        }
                    },()-> tooltip.add(Component.translatable("etshtinker.tool.tooltip.material_with_no_stat").withStyle(ChatFormatting.GOLD,ChatFormatting.UNDERLINE)));
                } else if (key != TooltipKey.CONTROL) {
                    tooltip.add(Component.empty());
                    tooltip.add(TooltipUtil.TOOLTIP_HOLD_SHIFT);
                    tooltip.add(TooltipUtil.TOOLTIP_HOLD_CTRL);
                }
            } else {
                IMaterial material = MaterialRegistry.getMaterial(id);
                if (material == IMaterial.UNKNOWN) {
                    tooltip.add(Component.translatable(MISSING_MATERIAL_KEY, id));
                } else {
                    tooltip.add(Component.translatable(MISSING_STATS_KEY, this.getStatType()).withStyle(ChatFormatting.GRAY));
                }
            }
        }
    }
}
