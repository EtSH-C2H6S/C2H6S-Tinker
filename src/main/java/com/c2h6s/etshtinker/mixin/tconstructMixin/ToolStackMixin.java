package com.c2h6s.etshtinker.mixin.tconstructMixin;

import com.c2h6s.etshtinker.Modifiers.PlasmaArrowModifier;
import com.c2h6s.etshtinker.Modifiers.PlasmaSputtering;
import com.c2h6s.etshtinker.init.EtshtinkerModifiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.List;

@Mixin(value = {ToolStack.class},remap = false)
public class ToolStackMixin {
    @Inject(at = @At(value = "RETURN"),method = "getModifiers",cancellable = true)
    private void addFluidModifiers(CallbackInfoReturnable<ModifierNBT> cir){
        ToolStack toolStack =(ToolStack) (Object) this;
        ModifierNBT modifierNBT =cir.getReturnValue();
        int level =modifierNBT.getLevel(EtshtinkerModifiers.ionizing_arrow.getId());
        int level2 =modifierNBT.getLevel(EtshtinkerModifiers.plasma_sputtering.getId());
        if (level>0) {
            List<ModifierEntry> list = PlasmaArrowModifier.getFluidModifiers(toolStack, level);
            list.addAll(modifierNBT.getModifiers());
            cir.setReturnValue(new ModifierNBT(list));
        }else if (level2>0){
            List<ModifierEntry> list = PlasmaSputtering.getFluidModifiers(toolStack, level2);
            list.addAll(modifierNBT.getModifiers());
            cir.setReturnValue(new ModifierNBT(list));
        }
    }
}
