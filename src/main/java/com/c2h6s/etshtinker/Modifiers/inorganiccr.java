package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class inorganiccr extends etshmodifieriii {
    public boolean isNoLevels() {
        return true;
    }
    public inorganiccr(){
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
    }

    private void livinghurtevent(LivingHurtEvent event) {
        if (event.getEntity() instanceof LivingEntity&&event.getEntity().getPersistentData().getInt("dmg_amplifier")>1){
            event.setAmount(event.getAmount()*event.getEntity().getPersistentData().getFloat("dmg_amplifier"));
        }
    }

    public void modifierOnAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        Entity entity =source.getEntity();
        if (entity instanceof LivingEntity attacker){
            AttributeInstance instance =attacker.getAttribute(Attributes.ARMOR);
            if (instance!=null){
                instance.setBaseValue(instance.getBaseValue()-amount);
            }
            CompoundTag tag =attacker.getPersistentData();
            if (!tag.contains("dmg_amplifier")){
                tag.putFloat("dmg_amplifier",1.5f);
            }
            else {
                tag.putFloat("dmg_amplifier",Math.max(1.5f,tag.getFloat("dmg_amplifier")+0.5f));
            }
        }
    }
}
