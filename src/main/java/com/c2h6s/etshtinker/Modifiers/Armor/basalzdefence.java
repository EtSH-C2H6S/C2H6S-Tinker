package com.c2h6s.etshtinker.Modifiers.Armor;

import cofh.core.init.CoreMobEffects;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class basalzdefence extends etshmodifieriii implements ToolStatsModifierHook {
    public static boolean enabled = ModList.get().isLoaded("cofh_core");
    private static final TinkerDataCapability.TinkerDataKey<Integer> key = TConstruct.createKey("basalzdefence");
    public basalzdefence(){
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
        ToolStats.ARMOR.multiply(builder,1+0.1*modifier.getLevel());
    }
    public float modifierDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (tool.getModifierLevel(this)>0){
            LivingEntity entity =context.getEntity();
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,60,1,false,false));
            return amount*(1-0.05f*modifier.getLevel());
        }
        return amount;
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (modifier.getLevel()>0&&isCorrectSlot&&holder!=null&&enabled){
            holder.addEffect(new MobEffectInstance(CoreMobEffects.EXPLOSION_RESISTANCE.get(),200,0,false,false));
        }
    }
}
