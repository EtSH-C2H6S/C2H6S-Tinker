package com.c2h6s.etshtinker.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class etshCap {
    public static final Capability<IDampenCapability> DAMPEN_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
}
