package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import java.lang.Math;

import static com.c2h6s.etshtinker.util.getMainOrOff.*;


public class overweightex extends etshmodifieriii {
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        int vy2 = (int) Math.pow(holder.getDeltaMovement().y,2);
        if(holder instanceof Player player&&getMainLevel(player,this)>0&&isSelected&&player.getDeltaMovement().y<-0.1){
            int weilvl =getMainLevel(player,this);
            player.setDeltaMovement(player.getDeltaMovement().x,(player.getDeltaMovement().y)-0.35*weilvl,player.getDeltaMovement().z);
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED,2,vy2,false,false));
            player.fallDistance=player.fallDistance + vy2;
        }
    }

}
