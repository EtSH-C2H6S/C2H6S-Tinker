package com.c2h6s.etshtinker.Modifiers.Armor;

import cofh.core.init.CoreMobEffects;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.security.SecureRandom;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;
import static com.c2h6s.etshtinker.util.getMainOrOff.getMainLevel;
import static com.c2h6s.etshtinker.util.thermalentityutil.summonElectricField;

public class thermaldefense extends etshmodifieriii {
    public static boolean enabled = ModList.get().isLoaded("cofh_core");
    private static final TinkerDataCapability.TinkerDataKey<Integer> key = TConstruct.createKey("thermaldefense");
    public thermaldefense(){
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.TOOL_STATS);
        builder.addModule(new ArmorLevelModule(key, false, (TagKey)null));
    }

    private void livinghurtevent(LivingHurtEvent event) {
        LivingEntity living = event.getEntity();
        living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
            int level = holder.get(key, 0);
            if (level > 0) {
                living.invulnerableTime += 5*level;
            }
        });
    }
    @Override
    public void addToolStats(IToolContext tool, ModifierEntry modifier, ModifierStatsBuilder builder) {
        ToolStats.ARMOR.multiply(builder,1+0.2*modifier.getLevel());
    }
    public float modifierDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        SecureRandom random =EtSHrnd();
        if (random.nextInt(20)>modifier.getLevel()){
            LivingEntity entity =context.getEntity();
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,60,2,false,false));
            return amount*(1-0.05f*modifier.getLevel());
        }
        else return  0;
    }
    public void modifierOnAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (tool.getModifierLevel(this) > 0&&slotType.getType() ==EquipmentSlot.Type.ARMOR&&enabled) {
            Entity entity =source.getEntity();
            int modilvl =modifier.getLevel();
            if (entity instanceof LivingEntity attacker){
                attacker.addEffect(new MobEffectInstance(CoreMobEffects.SHOCKED.get(),400,modilvl*2));
                attacker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,400,230));
                attacker.addEffect(new MobEffectInstance(CoreMobEffects.ENDERFERENCE.get(), 400, modilvl * 2, false, false));
                attacker.addEffect(new MobEffectInstance(MobEffects.GLOWING, 400, 0, false, false));
                AttributeInstance attribute = attacker.getAttributes().getInstance(Attributes.ARMOR);
                if (attribute!=null){
                    attribute.setBaseValue(attribute.getBaseValue()-0.5*attacker.getArmorValue());
                }
                if (source instanceof EntityDamageSource entityDamageSource&&entityDamageSource.isThorns()){
                    return;
                }
                summonElectricField(attacker.level, attacker, new Vec3(attacker.getX(), attacker.getY(), attacker.getZ()), 8, 120, modilvl);
            }
        }
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if(enabled&&holder instanceof Player player&&isCorrectSlot){
            int modilvl2 = getMainLevel(holder,this);
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,300,modilvl2,false,false));
            player.addEffect(new MobEffectInstance(CoreMobEffects.LIGHTNING_RESISTANCE.get(),300,modilvl2,false,false));
            player.addEffect(new MobEffectInstance(CoreMobEffects.EXPLOSION_RESISTANCE.get(),300,modilvl2,false,false));
            player.addEffect(new MobEffectInstance(CoreMobEffects.MAGIC_RESISTANCE.get(),300,modilvl2,false,false));
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,300,modilvl2,false,false));
        }
    }
}
