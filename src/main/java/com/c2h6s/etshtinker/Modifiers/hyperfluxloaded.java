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
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.*;

import java.util.function.Predicate;

public class hyperfluxloaded extends fluxloaded{
    public int getCapacity(IToolContext context, ModifierEntry modifier, ModDataNBT volatileData) {
        return 100000;
    }
    public int getPriority() {
        return 256;
    }
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage){
        if (etshmodifierfluxed.getEnergyStored(tool)>2000) {
            int energyDraw = Math.min(etshmodifierfluxed.getEnergyStored(tool),modifier.getLevel()*1000);
            damage += energyDraw/100f;
            etshmodifierfluxed.removeEnergy(tool,energyDraw,false,false);
        }
        return damage;
    }
    public ItemStack modifierFindAmmo(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, ItemStack itemStack, Predicate<ItemStack> predicate) {
        if (!(itemStack.getItem() instanceof ArrowItem)&&etshmodifierfluxed.getEnergyStored(tool)>1000){
            etshmodifierfluxed.removeEnergy(tool,1000,false,false);
            return new ItemStack(Items.ARROW,64);
        }
        return super.modifierFindAmmo(tool, modifiers, livingEntity, itemStack, predicate);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.BOW_AMMO);
    }

    @Override
    public void shrinkAmmo(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, ItemStack ammo, int needed) {
        if (!ammo.is(Items.ARROW)){
            if(etshmodifierfluxed.getEnergyStored(tool)<2000){
                ammo.shrink(needed);
            }else etshmodifierfluxed.removeEnergy(tool,2000,false,false);
        }
    }

    public void modifierOnProjectileLaunch(IToolStackView tool, ModifierEntry entry, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean primary) {
        if (etshmodifierfluxed.getEnergyStored(tool)>1000) {

            int energyDraw = Math.min(etshmodifierfluxed.getEnergyStored(tool),entry.getLevel()*200);
            if (abstractArrow != null) {
                abstractArrow.setBaseDamage(energyDraw/100f + abstractArrow.getBaseDamage());
                abstractArrow.setPierceLevel((byte) (int) (energyDraw/1000f + (float) abstractArrow.getPierceLevel()));
                abstractArrow.addTag("noinvltime");
            }
            etshmodifierfluxed.removeEnergy(tool, energyDraw, false, false);
        }

    }
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (projectile instanceof AbstractArrow arrow&&arrow.getTags().contains("noinvltime")&&target!=null){
            target.invulnerableTime=0;
        }
        return false;
    }

}
