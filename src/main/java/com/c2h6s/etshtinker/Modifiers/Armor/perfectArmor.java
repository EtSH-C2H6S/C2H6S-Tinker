package com.c2h6s.etshtinker.Modifiers.Armor;

import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;


public class perfectArmor extends EtshModifieriii {
    private static final TinkerDataCapability.TinkerDataKey<Integer> key = TConstruct.createKey("perfect_armor");
    public perfectArmor(){
        MinecraftForge.EVENT_BUS.addListener(this::livingAttack);
        MinecraftForge.EVENT_BUS.addListener(this::livingHurt);
    }

    private void livingHurt(LivingHurtEvent event) {
        float amount = event.getAmount();
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity living){
            living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
                int level = holder.get(key, 0);
                if (level > 0) {
                    float b =amount/(level+1);
                    if (b>10){
                        event.setAmount(b/10);
                    }
                }
            });
        }
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addModule(new ArmorLevelModule(key, false, (TagKey)null));
    }

    private void livingAttack(LivingAttackEvent event) {
        float amount = event.getAmount();
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity living){
            living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
                int level = holder.get(key, 0);
                if (level > 0) {
                    float b =amount/(level+1);
                    if (b<10){
                        event.setCanceled(true);
                    }
                }
            });
        }
    }
}
