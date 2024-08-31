package com.c2h6s.etshtinker.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import com.c2h6s.etshtinker.Effects.*;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;
import static net.minecraftforge.registries.ForgeRegistries.MOB_EFFECTS;

public class etshtinkerEffects {
    public static final DeferredRegister<MobEffect> EFFECT = DeferredRegister.create(MOB_EFFECTS, MOD_ID);
    public static final RegistryObject<MobEffect>novaradiation = EFFECT.register("novaradiation",novaradiation::new);
    public static final RegistryObject<MobEffect>ionized = EFFECT.register("ionized",ionized::new);
    public static final RegistryObject<MobEffect>cursefire = EFFECT.register("cursefire", cursefireEffect::new);
}
