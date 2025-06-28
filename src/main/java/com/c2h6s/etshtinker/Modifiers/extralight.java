package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import com.c2h6s.etshtinker.util.slotUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.DamageBlockModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class extralight extends EtshModifieriii implements DamageBlockModifierHook {
    public extralight(){
        MinecraftForge.EVENT_BUS.addListener(this::livingattackevent);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.DAMAGE_BLOCK);
    }
    private void livingattackevent(LivingAttackEvent event) {
        LivingEntity entity =event.getEntity();
        Entity entity1 =event.getSource().getEntity();
        if (entity!=null){
            if (entity1!=entity&&entity1 instanceof LivingEntity living&& slotUtil.getAllTotalLevel(living,this.getId())>0&&EtSHrnd().nextInt(10)==1&&entity1.invulnerableTime==0){
                entity1.invulnerableTime=4;
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

    @Override
    public boolean isDamageBlocked(IToolStackView tool, ModifierEntry entry, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount) {
        return EtSHrnd().nextInt(4) == 0;
    }
}
