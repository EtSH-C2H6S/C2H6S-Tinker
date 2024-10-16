package com.c2h6s.etshtinker.init;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;
public class etshtinkerParticleType {
    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MOD_ID);
    public static final RegistryObject<SimpleParticleType> electric = REGISTRY.register("electric", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> nova = REGISTRY.register("nova", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> laserparticle = REGISTRY.register("laserparticle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> plasmaexplosionpurple = REGISTRY.register("plasmaexplosionpurple", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> plasmaexplosionblue = REGISTRY.register("plasmaexplosionblue", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> plasmaexplosioncyan = REGISTRY.register("plasmaexplosioncyan", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> plasmaexplosiongreen = REGISTRY.register("plasmaexplosiongreen", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> plasmaexplosionyellow = REGISTRY.register("plasmaexplosionyellow", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> plasmaexplosionorange = REGISTRY.register("plasmaexplosionorange", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> plasmaexplosionred = REGISTRY.register("plasmaexplosionred", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> plasmaexplosionlime = REGISTRY.register("plasmaexplosionlime", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> slash = REGISTRY.register("slash", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> annihilateexplosionparticle = REGISTRY.register("annihilateexplosionparticle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> mana = REGISTRY.register("mana", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> curse = REGISTRY.register("curse", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> annihl = REGISTRY.register("annihl", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> annihl_scatter = REGISTRY.register("annihl_scatter", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> sonic_energy = REGISTRY.register("sonic_energy", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> atomic_dec = REGISTRY.register("atomic_dec", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> quark_disassemble = REGISTRY.register("quark_disassemble", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> exo = REGISTRY.register("exo", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> exo_ligh = REGISTRY.register("exo_lightning", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> exo_light_end = REGISTRY.register("exo_light_end", () -> new SimpleParticleType(false));
}
