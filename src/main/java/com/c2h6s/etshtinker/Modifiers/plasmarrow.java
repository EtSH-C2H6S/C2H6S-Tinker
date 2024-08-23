package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.function.Predicate;

public class plasmarrow extends etshmodifieriii {
    public boolean isNoLevels() {
        return true;
    }
    public int getPriority() {
        return 900;
    }
    public ItemStack modifierFindAmmo(IToolStackView tool, ModifierEntry modifiers, LivingEntity shooter, ItemStack itemStack, Predicate<ItemStack> predicate) {
        if ( !tool.isBroken() && shooter instanceof Player player) {
                return new ItemStack(etshtinkerItems.plasmarrowitem.get(), 64);
        }
        return ItemStack.EMPTY;
    }

}
