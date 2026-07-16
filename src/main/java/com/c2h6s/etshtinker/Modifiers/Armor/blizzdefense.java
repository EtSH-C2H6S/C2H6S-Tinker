package com.c2h6s.etshtinker.Modifiers.Armor;

import cofh.core.init.CoreMobEffects;
import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.security.SecureRandom;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class blizzdefense extends EtshModifieriii {
    public static boolean enabled = ModList.get().isLoaded("cofh_core");
    public float modifierDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (source.getEntity() instanceof LivingEntity living){
            if (living.getArmorValue()>0)
                amount+=living.getArmorValue()*modifier.getLevel()*8;
        }
        return amount;
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (level.getGameTime()%100==0&&modifier.getLevel()>0&&isCorrectSlot&&holder!=null&&enabled){
            holder.addEffect(new MobEffectInstance(CoreMobEffects.COLD_RESISTANCE.get(),200,modifier.getLevel()*5-1,false,false));
        }
    }
}
