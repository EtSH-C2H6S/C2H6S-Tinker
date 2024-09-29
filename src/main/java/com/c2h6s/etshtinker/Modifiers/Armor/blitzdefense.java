package com.c2h6s.etshtinker.Modifiers.Armor;

import cofh.core.init.CoreMobEffects;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.security.SecureRandom;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;
import static com.c2h6s.etshtinker.util.thermalentityutil.*;

public class blitzdefense extends etshmodifieriii {
    public static boolean enabled = ModList.get().isLoaded("cofh_core");
    public float modifierDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        SecureRandom random =EtSHrnd();
        if (random.nextInt(20)>modifier.getLevel()){
            LivingEntity entity =context.getEntity();
            entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED,60,2,false,false));
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,60,2,false,false));
            summonArc(entity.level,entity,new Vec3(entity.getX(),entity.getY()+0.5*entity.getBbHeight(),entity.getZ()),2+modifier.getLevel()*2,5+modifier.getLevel());
            return amount;
        }else return 0;
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (modifier.getLevel()>0&&isCorrectSlot&&holder!=null&&enabled){
            holder.addEffect(new MobEffectInstance(CoreMobEffects.LIGHTNING_RESISTANCE.get(),200,4,false,false));
            holder.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,300,0,false,false));
        }
    }
}
