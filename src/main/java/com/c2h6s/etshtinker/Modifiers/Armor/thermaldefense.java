package com.c2h6s.etshtinker.Modifiers.Armor;

import cofh.core.init.CoreMobEffects;
import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import java.security.SecureRandom;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class thermaldefense extends EtshModifieriii implements VolatileDataModifierHook {
    public static boolean enabled = ModList.get().isLoaded("cofh_core");
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.TOOL_STATS,ModifierHooks.VOLATILE_DATA);
    }

    public float modifierDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        SecureRandom random =EtSHrnd();
        if (random.nextInt(25)>modifier.getLevel()){
            LivingEntity entity =context.getEntity();
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,60,2,false,false));
            entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED,60,2,false,false));
            if(source.isExplosion()||source.isMagic()){
                amount*=0.5f;
            }
            return amount;
        }
        else return  0;
    }
    public void modifierOnAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (tool.getModifierLevel(this) > 0&&slotType.getType() ==EquipmentSlot.Type.ARMOR&&enabled) {
            Entity entity =source.getEntity();
            int modilvl =modifier.getLevel();
            if (entity instanceof LivingEntity attacker&&!(attacker instanceof Player)){
                attacker.addEffect(new MobEffectInstance(CoreMobEffects.SHOCKED.get(),400,modilvl*2));
                attacker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,400,4));
                attacker.addEffect(new MobEffectInstance(CoreMobEffects.ENDERFERENCE.get(), 400, modilvl * 2, false, false));
                attacker.addEffect(new MobEffectInstance(MobEffects.GLOWING, 400, 0, false, false));
                AttributeInstance attribute = attacker.getAttributes().getInstance(Attributes.ARMOR);
                if (attribute != null){
                    attribute.setBaseValue(attribute.getBaseValue()-0.5*attacker.getArmorValue());
                }
            }
        }
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if(enabled&&holder instanceof Player player&&isCorrectSlot){
            int modilvl2 = modifier.getLevel();
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,300,modilvl2,false,false));
            player.addEffect(new MobEffectInstance(CoreMobEffects.LIGHTNING_RESISTANCE.get(),300,modilvl2,false,false));
            player.addEffect(new MobEffectInstance(CoreMobEffects.COLD_RESISTANCE.get(),300,modilvl2,false,false));
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,300,modilvl2,false,false));
        }
    }

    @Override
    public void addVolatileData(IToolContext iToolContext, ModifierEntry modifierEntry, ModDataNBT modDataNBT) {
        modDataNBT.addSlots(SlotType.ABILITY,modifierEntry.getLevel());
        modDataNBT.addSlots(SlotType.DEFENSE,modifierEntry.getLevel()*2);
    }
}
