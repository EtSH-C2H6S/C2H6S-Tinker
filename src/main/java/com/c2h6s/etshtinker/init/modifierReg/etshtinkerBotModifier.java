package com.c2h6s.etshtinker.init.modifierReg;

import com.c2h6s.etshtinker.Modifiers.*;
import com.c2h6s.etshtinker.Modifiers.Armor.threshold;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class etshtinkerBotModifier {
    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(MOD_ID);
    public static final StaticModifier<alfrage> alfrage_STATIC_MODIFIER= MODIFIERS.register("alfrage", alfrage::new);
    public static final StaticModifier<alfsmind>alfsmind_STATIC_MODIFIER= MODIFIERS.register("alfsmind", alfsmind::new);
    public static final StaticModifier<threshold>threshold_STATIC_MODIFIER= MODIFIERS.register("threshold", threshold::new);
}
