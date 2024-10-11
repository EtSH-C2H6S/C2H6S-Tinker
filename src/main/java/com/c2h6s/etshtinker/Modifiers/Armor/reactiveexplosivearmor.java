package com.c2h6s.etshtinker.Modifiers.Armor;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;

public class reactiveexplosivearmor extends etshmodifieriii {
    private static final TinkerDataCapability.TinkerDataKey<Integer> key = TConstruct.createKey("reactiveexplosivearmor");
    public reactiveexplosivearmor(){
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addModule(new ArmorLevelModule(key, false, (TagKey)null));
    }
    private void livinghurtevent(LivingHurtEvent event) {
        if (event.getSource() instanceof EntityDamageSource entityDamageSource&&entityDamageSource.isThorns()){
            return;
        }
        LivingEntity living = event.getEntity();
        Entity entity =event.getSource().getEntity();
        living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
            int level = holder.get(key, 0);
            if (level > 0) {
                if (event.getSource().isExplosion()){
                    event.setCanceled(true);
                }
                living.level.explode(living,living.getX(),living.getY()+0.5*living.getBbHeight(),living.getZ(),event.getAmount()*0.25f, Explosion.BlockInteraction.NONE);
                event.setAmount(event.getAmount()*0.75f);
            }
        });
    }
}
