package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.etshtinker;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;


public class ultradenseex extends etshmodifieriii {
    public static final ResourceLocation UDE_LOCATION = etshtinker.getResourceLoc("ultradenseex");
    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        if (!tool.getPersistentData().getBoolean(UDE_LOCATION)) {
            tool.getPersistentData().putBoolean(UDE_LOCATION, true);
            for (ModifierEntry entry : tool.getModifierList()) {
                if (entry.getModifier() != modifier.getModifier()) {
                    damage = entry.getHook(ModifierHooks.MELEE_DAMAGE).getMeleeDamage(tool, modifier, context, baseDamage, damage);
                }
            }
        }
        tool.getPersistentData().putBoolean(UDE_LOCATION, false);

        return damage;
    }
}
