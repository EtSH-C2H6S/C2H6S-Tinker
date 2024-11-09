package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.util.slotUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class extralight extends etshmodifieriii {
    public extralight(){
        MinecraftForge.EVENT_BUS.addListener(this::livingattackevent);
    }


    private static final TinkerDataCapability.TinkerDataKey<Integer> key = TConstruct.createKey("extralight");
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addModule(new ArmorLevelModule(key, false, (TagKey)null));
    }
    private void livingattackevent(LivingAttackEvent event) {
        LivingEntity entity =event.getEntity();
        Entity entity1 =event.getSource().getEntity();
        if (entity!=null){
            if (slotUtil.getAllTotalLevel(entity,this.getId())>0&&EtSHrnd().nextInt(4)==1){
                event.setCanceled(true);
            }
            if (entity1!=entity&&entity1 instanceof LivingEntity living&& slotUtil.getAllTotalLevel(living,this.getId())>0&&EtSHrnd().nextInt(19)==1&&entity1.invulnerableTime==0){
                entity1.invulnerableTime=2;
            }
        }
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if(isCorrectSlot&&!tool.isBroken()&&holder!=null){
            int lvl = slotUtil.getAllTotalLevel(holder,this.getId());
            holder.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,20,lvl+1,false,false));
            holder.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED,20,lvl*3,false,false));
        }
    }
}
