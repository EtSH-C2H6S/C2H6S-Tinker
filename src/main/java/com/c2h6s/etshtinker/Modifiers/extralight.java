package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.security.SecureRandom;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;
import static com.c2h6s.etshtinker.util.getMainOrOff.*;

public class extralight extends etshmodifieriii {
    public extralight(){
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
        MinecraftForge.EVENT_BUS.addListener(this::livingattackevent);
    }


    private static final TinkerDataCapability.TinkerDataKey<Integer> key = TConstruct.createKey("extralight");
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addModule(new ArmorLevelModule(key, false, (TagKey)null));
    }
    private void livinghurtevent(LivingHurtEvent event) {
        LivingEntity entity =event.getEntity();
        if (entity !=null) {
            int main = getMainLevel(entity,this);
            int off=getOffLevel(entity,this);
            if (main>0&&RANDOM.nextInt(100)<Math.min(24,main*8)) {
                event.setCanceled(true);
            }
            if (off>0&&RANDOM.nextInt(100)<Math.min(24,off*8)) {
                event.setCanceled(true);
            }
        }
    }
    private void livingattackevent(LivingAttackEvent event) {
        LivingEntity entity =event.getEntity();
        if (entity!=null){
            entity.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
                int level = holder.get(key, 0);
                if (level > 0) {
                    SecureRandom random =EtSHrnd();
                    if (random.nextInt(5)==1){
                        entity.invulnerableTime+=2;
                        event.setCanceled(true);
                    }
                }
            });
        }
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if(isCorrectSlot&&!tool.isBroken()){
            holder.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,20,tool.getModifierLevel(this)+1,false,false));
            holder.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED,20,tool.getModifierLevel(this)*2,false,false));
        }
    }
}
