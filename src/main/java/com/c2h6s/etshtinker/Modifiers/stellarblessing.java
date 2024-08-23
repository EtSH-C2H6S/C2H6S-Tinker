package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import static com.c2h6s.etshtinker.util.vecCalc.getMold;

public class stellarblessing extends etshmodifieriii {
    private static final TinkerDataCapability.TinkerDataKey<Integer> key = TConstruct.createKey("stellarblessing");
    public stellarblessing(){
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addModule(new ArmorLevelModule(key, false, (TagKey)null));
    }
    private void livinghurtevent(LivingHurtEvent event) {
        LivingEntity living = event.getEntity();
        Entity entity =event.getSource().getEntity();
        living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
            int level = holder.get(key, 0);
            if (level > 0) {
                if (event.getSource().isFire()||event.getSource().isExplosion()){
                    event.setCanceled(true);
                }
                if (getMold(living.getDeltaMovement())>0.08){
                    event.setAmount(event.getAmount()*(1-(0.1f*level)));
                }
                if (entity instanceof LivingEntity attacker){
                    attacker.setNoGravity(true);
                    attacker.setSecondsOnFire(200);
                }
            }
        });
    }
    public void modifierOnEquip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        if (modifier.getLevel()>0&&context.getEntity() instanceof Player player){
            player.getAbilities().flying=true;
            player.getAbilities().mayfly=true;
        }
    }
    public void modifierOnUnequip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        if (modifier.getLevel()>0&&context.getEntity() instanceof Player player&&!player.isCreative()){
            player.getAbilities().flying=false;
            player.getAbilities().mayfly=false;
        }
    }
}
