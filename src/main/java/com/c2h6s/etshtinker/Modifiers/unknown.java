package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import com.c2h6s.etshtinker.init.etshtinkerToolStats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.security.SecureRandom;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class unknown extends EtshModifieriii {
    @Override
    public int getPriority() {
        return 512;
    }

    private static final TinkerDataCapability.TinkerDataKey<Integer> key = TConstruct.createKey("unknown");
    public boolean isNoLevels() {
        return true;
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addModule(new ArmorLevelModule(key, false, TinkerTags.Items.MODIFIABLE));
    }
    public unknown(){
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
    }

    private void livinghurtevent(LivingAttackEvent event) {
        LivingEntity attacker =event.getEntity();
        SecureRandom random = EtSHrnd();
        if (attacker!=null){
            attacker.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
                int level = holder.get(key, 0);
                if (level > 0) {
                    if (random.nextInt(2)==1){
                        event.setCanceled(true);
                    }
                }
            });
        }
    }
}
