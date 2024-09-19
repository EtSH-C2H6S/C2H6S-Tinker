package com.c2h6s.etshtinker.mixin;

import com.c2h6s.etshtinker.capability.IDampenCapability;
import com.c2h6s.etshtinker.capability.etshCap;
import com.c2h6s.etshtinker.util.Cap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin({Entity.class})
public class EntityMixin {

    @Inject(at = @At(value = "HEAD"), method = "dampensVibrations",cancellable = true)
    public void etshDampen(CallbackInfoReturnable<Boolean> callbackinfo){
        float dampen =0;
        List<EquipmentSlot> Slots =List.of(EquipmentSlot.CHEST,EquipmentSlot.FEET,EquipmentSlot.HEAD,EquipmentSlot.LEGS,EquipmentSlot.MAINHAND,EquipmentSlot.MAINHAND);
        Entity entity = (Entity) (Object) this;
        if(entity instanceof LivingEntity living) {
            for (EquipmentSlot slot : Slots) {
                ItemStack stack =living.getItemBySlot(slot);
                Optional<IDampenCapability> capability = Cap.getCapability(stack, etshCap.DAMPEN_CAPABILITY,null).resolve();
                if(capability.isPresent()){
                    dampen+= capability.get().getDampenCap();
                }
            }
        }
        callbackinfo.setReturnValue(dampen>=1);
    }

}
