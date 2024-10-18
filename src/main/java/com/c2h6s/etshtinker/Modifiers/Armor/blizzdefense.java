package com.c2h6s.etshtinker.Modifiers.Armor;

import cofh.core.init.CoreMobEffects;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
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

public class blizzdefense extends etshmodifieriii {
    public static boolean enabled = ModList.get().isLoaded("cofh_core");
    public float modifierDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        SecureRandom random =EtSHrnd();
        if (tool.getModifierLevel(this)>0&&random.nextInt(20)>modifier.getLevel()){
            if (source.getEntity() instanceof LivingEntity attacker&&attacker!=context.getEntity()){
                attacker.addEffect(new MobEffectInstance(CoreMobEffects.CHILLED.get(),200,4));
            }
            return amount;
        }else return 0;
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (modifier.getLevel()>0&&isCorrectSlot&&holder!=null&&enabled){
            holder.addEffect(new MobEffectInstance(CoreMobEffects.COLD_RESISTANCE.get(),200,0,false,false));
        }
    }
}
