package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

import java.util.function.Predicate;

public class PhaseCharacteristic extends etshmodifieriii {

    @Override
    public ItemStack modifierFindAmmo(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, ItemStack itemStack, Predicate<ItemStack> predicate) {
        if (livingEntity instanceof ServerPlayer player){
            if (player.experienceLevel>20){
                player.experienceLevel-=20;
                return new ItemStack(Items.ARROW);
            }
        }
        return super.modifierFindAmmo(tool, modifiers, livingEntity, itemStack, predicate);
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean primary) {
        if (abstractArrow!=null){

        }
    }
}
