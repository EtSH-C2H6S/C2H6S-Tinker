package com.c2h6s.etshtinker.Modifiers.modifiers;

import com.c2h6s.etshtinker.init.etshtinkerModifiers;
import mekanism.api.radiation.capability.IRadiationShielding;
import mekanism.common.capabilities.Capabilities;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.function.Supplier;

public class etshRadiationShieldCap implements IRadiationShielding , ToolCapabilityProvider.IToolCapabilityProvider{
    public final Supplier<? extends IToolStackView> tool;
    public final LazyOptional<IRadiationShielding> capOptional;
    public etshRadiationShieldCap(ItemStack stack, Supplier<? extends IToolStackView> toolStack){
        tool =toolStack;
        capOptional =LazyOptional.of(()->this);
    }
    @Override
    public double getRadiationShielding() {
        return 0.25;
    }

    @Override
    public <T> LazyOptional<T> getCapability(IToolStackView iToolStackView, Capability<T> capability) {
        return  iToolStackView.getModifierLevel(etshtinkerModifiers.etshMekModifier.radiationproof_STATIC_MODIFIER.get())>0&& capability == Capabilities.RADIATION_SHIELDING ? Capabilities.RADIATION_SHIELDING.orEmpty(capability,this.capOptional):LazyOptional.empty();
    }
}
