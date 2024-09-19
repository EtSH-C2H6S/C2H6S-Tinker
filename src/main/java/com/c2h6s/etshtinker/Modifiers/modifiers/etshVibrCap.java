package com.c2h6s.etshtinker.Modifiers.modifiers;

import com.c2h6s.etshtinker.capability.IVibrationRecieve;
import com.c2h6s.etshtinker.capability.etshCap;
import com.c2h6s.etshtinker.init.etshtinkerToolStats;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.function.Supplier;

public class etshVibrCap implements IVibrationRecieve, ToolCapabilityProvider.IToolCapabilityProvider{
    public final Supplier<? extends IToolStackView> tool;
    public final LazyOptional<IVibrationRecieve> capOptional;
    public etshVibrCap(ItemStack stack, Supplier<? extends IToolStackView> toolStack){
        tool =toolStack;
        capOptional =LazyOptional.of(()->this);
    }

    @Override
    public <T> LazyOptional<T> getCapability(IToolStackView iToolStackView, Capability<T> capability) {
        return  iToolStackView.getStats().get(etshtinkerToolStats.VIBR)>0&& capability == etshCap.VIBR_CAPABILITY ? etshCap.VIBR_CAPABILITY.orEmpty(capability,this.capOptional):LazyOptional.empty();
    }


    @Override
    public float getVibrationRecieveCap() {
        return tool.get().getStats().get(etshtinkerToolStats.VIBR);
    }
}
