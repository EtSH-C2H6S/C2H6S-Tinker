package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.EtSTBaseModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import vazkii.botania.api.mana.ManaItemHandler;

public class ManaShot extends EtSTBaseModifier {
    @Override
    public void shrinkAmmo(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, ItemStack ammo, int needed) {
        if (shooter instanceof Player player) {
            if (ManaItemHandler.instance().requestManaExactForTool(shooter.getItemInHand(shooter.getUsedItemHand()),player,500,false)){
                ManaItemHandler.instance().requestManaForTool(shooter.getItemInHand(shooter.getUsedItemHand()),player,500,true);
                return;
            }
        }
        super.shrinkAmmo(tool, modifier, shooter, ammo, needed);
    }
}
