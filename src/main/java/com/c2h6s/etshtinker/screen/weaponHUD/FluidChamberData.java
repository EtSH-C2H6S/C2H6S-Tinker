package com.c2h6s.etshtinker.screen.weaponHUD;

import net.minecraft.nbt.CompoundTag;

public class FluidChamberData {
    public static CompoundTag Nbt = null;
    public static void setNbt(CompoundTag nbt){
        Nbt =nbt;
    }
    public static CompoundTag getNbt(){
        return Nbt;
    }
}
