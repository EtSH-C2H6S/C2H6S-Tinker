package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifierfluxed;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.*;

import java.util.function.Predicate;

public class hyperfluxloaded extends fluxloaded{
    public int getCapacity(IToolContext context, ModifierEntry modifier, ModDataNBT volatileData) {
        return 10000000;
    }
    public int getPriority() {
        return 256;
    }
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage){
        if (etshmodifierfluxed.getEnergyStored(tool)>0){
            float dmgup = (float) etshmodifierfluxed.getEnergyStored(tool) /1000000;
            damage += dmgup;
            etshmodifierfluxed.removeEnergy(tool,etshmodifierfluxed.getEnergyStored(tool)/10,false,false);
        }
        return damage;
    }
    public ItemStack modifierFindAmmo(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, ItemStack itemStack, Predicate<ItemStack> predicate) {
        if (!(itemStack.getItem() instanceof ArrowItem)&&etshmodifierfluxed.getEnergyStored(tool)>10000){
            etshmodifierfluxed.removeEnergy(tool,10000,false,false);
            return new ItemStack(Items.ARROW);
        }
        return itemStack;
    }
    public void modifierOnProjectileLaunch(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean primary) {
        if (etshmodifierfluxed.getEnergyStored(tool)>0&&modifiers.getLevel()>0){
            if (etshmodifierfluxed.getEnergyStored(tool)>0){
                float dmgup = (float) etshmodifierfluxed.getEnergyStored(tool) /5000000;
                if (abstractArrow!=null) {
                    abstractArrow.setBaseDamage(dmgup + abstractArrow.getBaseDamage());
                    abstractArrow.setPierceLevel((byte)(int) (dmgup+(float) abstractArrow.getPierceLevel()));
                    abstractArrow.addTag("noinvltime");
                }
                etshmodifierfluxed.removeEnergy(tool,etshmodifierfluxed.getEnergyStored(tool)/10,false,false);
            }
        }
    }
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (projectile instanceof AbstractArrow arrow&&arrow.getTags().contains("noinvltime")&&target!=null){
            target.invulnerableTime=0;
        }
        return false;
    }

}
