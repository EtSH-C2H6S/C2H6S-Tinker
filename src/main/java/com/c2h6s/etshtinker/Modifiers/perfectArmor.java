package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;


public class perfectArmor extends etshmodifieriii {
    private static final TinkerDataCapability.TinkerDataKey<Integer> key = TConstruct.createKey("perfect_armor");
    public perfectArmor(){
        MinecraftForge.EVENT_BUS.addListener(this::livingHurtevent);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addModule(new ArmorLevelModule(key, false, (TagKey)null));
    }

    private void livingHurtevent(LivingHurtEvent event) {
        float amount = event.getAmount();
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity living){
            living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
                int level = holder.get(key, 0);
                if (level > 0) {
                    float b =amount/level;
                    if (b<=4){
                        event.setAmount(0);
                    }
                    else event.setAmount(10);
                }
            });
        }
    }
}
