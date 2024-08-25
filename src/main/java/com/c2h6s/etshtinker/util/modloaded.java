package com.c2h6s.etshtinker.util;

import net.minecraftforge.fml.ModList;

public class modloaded {
    public static boolean Mekenabled = ModList.get().isLoaded("mekanism");
    public static boolean Cofhloaded = ModList.get().isLoaded("cofh_core");
    public static boolean BOTloaded = ModList.get().isLoaded("botania");
    public static boolean Powahloaded = ModList.get().isLoaded("powah");
    public static boolean Adastraloaded = ModList.get().isLoaded("ad_astra");
    public static boolean AE2loaded = ModList.get().isLoaded("ae2");
    public static boolean IEloaded=ModList.get().isLoaded("immersiveengineering");
    public static boolean CYCloaded=ModList.get().isLoaded("cyclic");
    public static boolean MBOTloaded = ModList.get().isLoaded("mythicbotany");
}
