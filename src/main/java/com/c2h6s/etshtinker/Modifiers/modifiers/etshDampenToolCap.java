package com.c2h6s.etshtinker.Modifiers.modifiers;

import com.c2h6s.etshtinker.capability.IDampenCapability;
import com.c2h6s.etshtinker.capability.etshCap;
import com.c2h6s.etshtinker.init.etshtinkerToolStats;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.function.Supplier;

public class etshDampenToolCap implements IDampenCapability, ToolCapabilityProvider.IToolCapabilityProvider {
    public final Supplier<? extends IToolStackView> tool;
    public final LazyOptional<IDampenCapability> capOptional;
    public etshDampenToolCap(ItemStack stack, Supplier<? extends IToolStackView> toolStack){
        tool =toolStack;
        capOptional =LazyOptional.of(()->this);
    }

    @Override
    public <T> LazyOptional<T> getCapability(IToolStackView iToolStackView, Capability<T> capability) {
        return  iToolStackView.getStats().get(etshtinkerToolStats.DAMPEN)>0&& capability == etshCap.DAMPEN_CAPABILITY ? etshCap.DAMPEN_CAPABILITY.orEmpty(capability,this.capOptional):LazyOptional.empty();
    }

    @Override
    public float getDampenCap() {
        return tool.get().getStats().get(etshtinkerToolStats.DAMPEN);
    }
}
