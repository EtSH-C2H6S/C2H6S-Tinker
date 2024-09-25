package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.util.slotUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.item.armor.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class semicrystaline extends etshmodifieriii {
    public semicrystaline(){
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
    }
    private void livinghurtevent(LivingHurtEvent event) {
        LivingEntity living = event.getEntity();
        float dura =0;
        int level =0;
        if (living!=null){
            for (EquipmentSlot slot: slotUtil.ARMOR){
                ItemStack stack = living.getItemBySlot(slot);
                if (stack.getItem() instanceof ModifiableArmorItem){
                    ToolStack tool =ToolStack.from(stack);
                    dura+=tool.getCurrentDurability();
                    level+=tool.getModifierLevel(this);
                }
            }
        }
        dura/=2000;
        dura =Math.min(25*level,dura);
        if (dura==0){
            return;
        }
        if (event.getAmount()<=dura){
            event.setAmount(0);
            event.setCanceled(true);
        }
        else {
            event.setAmount(event.getAmount()-dura);
        }
    }
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage){
        return damage+Math.min(2000*modifier.getLevel(),tool.getCurrentDurability()/500);
    }
    public void modifierOnProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean primary) {
        if (abstractArrow != null) {
            abstractArrow.setBaseDamage(abstractArrow.getBaseDamage()+Math.min(500*modifier.getLevel(),tool.getCurrentDurability()/1000));
        }
    }

}
