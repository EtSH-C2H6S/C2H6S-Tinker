package com.c2h6s.etshtinker.mixin;

import com.c2h6s.etshtinker.init.etshtinkerHook;
import com.c2h6s.etshtinker.init.EtshtinkerModifiers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(at = @At(value = "RETURN"),method = "hasFoil",cancellable = true)
    public void makeFoil(CallbackInfoReturnable<Boolean> cir){
        ItemStack stack = (ItemStack) (Object) this;
        if (!(stack.getItem() instanceof IModifiable)) return;
        ToolStack tool = ToolStack.from(stack);
        boolean b =cir.getReturnValueZ();
        for (ModifierEntry entry:tool.getModifierList()){
            b= entry.getHook(etshtinkerHook.FOIL).isFoil(tool,b);
        }
        cir.setReturnValue(b);
    }
    @Inject(at = @At(value = "RETURN"),method = "isCorrectToolForDrops",cancellable = true)
    public void AllowDropForAtomD(BlockState p_41450_, CallbackInfoReturnable<Boolean> cir){
        ItemStack stack =(ItemStack) (Object)this;
        if (stack.getItem() instanceof IModifiable&&!cir.getReturnValueZ()){
            ToolStack toolStack =ToolStack.from(stack);
            if (toolStack.getModifierLevel(EtshtinkerModifiers.atomic_decompose.getId())>0||toolStack.getModifierLevel(EtshtinkerModifiers.quark_disassemble.getId())>0){
                cir.setReturnValue(true);
            }
        }
    }
}
