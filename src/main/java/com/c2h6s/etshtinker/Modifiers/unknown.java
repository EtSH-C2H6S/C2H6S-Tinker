package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerToolStats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.TConstruct;
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
import static com.c2h6s.etshtinker.util.getMainOrOff.getMainLevel;
import static com.c2h6s.etshtinker.util.getMainOrOff.getOffLevel;

public class unknown extends etshmodifieriii implements ToolStatsModifierHook {
    private static final TinkerDataCapability.TinkerDataKey<Integer> key = TConstruct.createKey("unknown");
    public boolean isNoLevels() {
        return true;
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.TOOL_STATS);
        builder.addModule(new ArmorLevelModule(key, false, (TagKey)null));
    }
    public unknown(){
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
    }

    private void livinghurtevent(LivingHurtEvent event) {
        LivingEntity attacker =event.getEntity();
        SecureRandom random = EtSHrnd();
        if (attacker!=null){
            attacker.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
                int level = holder.get(key, 0);
                if (level > 0) {
                    if (event.getSource().isBypassArmor()) {
                        event.setCanceled(true);
                    }
                    else if (random.nextInt(2)==1){
                        event.setCanceled(true);
                    }
                }
            });
            if (getMainLevel(attacker,this)>0){
                if (event.getSource().isBypassArmor()) {
                    event.setCanceled(true);
                }
                else if (random.nextInt(2)==1){
                    event.setCanceled(true);
                }
            }
            else if (getOffLevel(attacker,this)>0){
                if (event.getSource().isBypassArmor()) {
                    event.setCanceled(true);
                }
                else if (random.nextInt(2)==1){
                    event.setCanceled(true);
                }
            }
        }
    }
    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        ToolStats.DURABILITY.multiply(builder, 0.005);
        ToolStats.ATTACK_SPEED.multiply(builder, 0.005);
        ToolStats.ATTACK_DAMAGE.multiply(builder, 0.005);
        ToolStats.ACCURACY.multiply(builder, 0.005);
        ToolStats.DRAW_SPEED.multiply(builder, 0.005);
        ToolStats.MINING_SPEED.multiply(builder, 0.005);
        ToolStats.ARMOR.multiply(builder, 0.005);
        ToolStats.ARMOR_TOUGHNESS.multiply(builder, 0.005);
        ToolStats.PROJECTILE_DAMAGE.multiply(builder, 0.005);
        ToolStats.KNOCKBACK_RESISTANCE.multiply(builder, 0.005);
        ToolStats.BLOCK_AMOUNT.multiply(builder, 0.005);
        ToolStats.BLOCK_ANGLE.multiply(builder, 0.005);
        etshtinkerToolStats.COOLDOWN.multiply(builder,5);
        etshtinkerToolStats.PLASMARANGE.multiply(builder, 0.005);
        etshtinkerToolStats.ENERGY_STORE.multiply(builder, 0.005);
        ToolTankHelper.CAPACITY_STAT.multiply(builder, 0.005);
    }
}
