package com.c2h6s.etshtinker.Modifiers.Armor;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class UltradenseExArmor extends etshmodifieriii {
    private static final TinkerDataCapability.TinkerDataKey<Integer> key = TConstruct.createKey("ultradenseex");
    public UltradenseExArmor(){
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addModule(new ArmorLevelModule(key, false, (TagKey)null));
        builder.addHook(this, ModifierHooks.TOOL_STATS);
    }

    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    public void modifierAddToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        super.modifierAddToolStats(context, modifier, builder);
        ToolStats.ARMOR.multiply(builder,2);
        ToolStats.ARMOR_TOUGHNESS.multiply(builder,2);
    }

    private void livinghurtevent(LivingDamageEvent event) {
        LivingEntity living = event.getEntity();
        Entity entity =event.getSource().getEntity();
        living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
            int level = holder.get(key, 0);
            if (level > 0&&living instanceof Player&&event.getSource()!=DamageSource.OUT_OF_WORLD) {
                float am = event.getAmount();
                am = ForgeHooks.onLivingHurt(living,event.getSource(), am);
                am = CombatRules.getDamageAfterAbsorb(am, (float) living.getArmorValue(), (float) living.getAttributeValue(Attributes.ARMOR_TOUGHNESS));
                if (event.getSource().isBypassArmor()){
                    am = CombatRules.getDamageAfterAbsorb(am, (float) living.getArmorValue(), (float) living.getAttributeValue(Attributes.ARMOR_TOUGHNESS));
                }
                am = getMagicDR(am,event.getSource(),living);
                event.setAmount(am);
            }
        });
    }
    public float getMagicDR(float amount,DamageSource damageSource,LivingEntity living){
        int a;
        if (living.hasEffect(MobEffects.DAMAGE_RESISTANCE) && damageSource != DamageSource.OUT_OF_WORLD) {
            a = (living.getEffect(MobEffects.DAMAGE_RESISTANCE).getAmplifier() + 1) * 5;
            int b = 25 - a;
            float f = amount * (float) b;
            float f1 = amount;
            amount = Math.max(f / 25.0F, 0.0F);
            float f2 = f1 - amount;
            if (f2 > 0.0F && f2 < 3.4028235E37F) {
                if (living instanceof ServerPlayer) {
                    ((ServerPlayer)living).awardStat(Stats.CUSTOM.get(Stats.DAMAGE_RESISTED), Math.round(f2 * 10.0F));
                } else if (damageSource.getEntity() instanceof ServerPlayer) {
                    ((ServerPlayer)damageSource.getEntity()).awardStat(Stats.CUSTOM.get(Stats.DAMAGE_DEALT_RESISTED), Math.round(f2 * 10.0F));
                }
            }
        }
        if (amount <= 0.0F) {
            return 0.0F;
        } else if (damageSource.isBypassEnchantments()) {
            return amount;
        } else {
            a = EnchantmentHelper.getDamageProtection(living.getArmorSlots(), damageSource);
            if (a > 0) {
                amount = CombatRules.getDamageAfterMagicAbsorb(amount, (float) a);
            }
            return amount;
        }
    }


}
