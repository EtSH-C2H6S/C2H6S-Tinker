package com.c2h6s.etshtinker.init;

import com.c2h6s.etshtinker.client.particle.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class etshtinkerParticle {
    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.register(etshtinkerParticleType.electric.get(), ElectricParticle::provider);
        event.register(etshtinkerParticleType.nova.get(), NovaParticle::provider);
        event.register(etshtinkerParticleType.laserparticle.get(), laserparticle::provider);
        event.register(etshtinkerParticleType.plasmaexplosionpurple.get(), plasmaexplosionpurpleparticle::provider);
        event.register(etshtinkerParticleType.plasmaexplosionblue.get(), plasmaexplosionblueparticle::provider);
        event.register(etshtinkerParticleType.plasmaexplosioncyan.get(), plasmaexplosioncyanparticle::provider);
        event.register(etshtinkerParticleType.plasmaexplosiongreen.get(), plasmaexplosiongreenparticle::provider);
        event.register(etshtinkerParticleType.plasmaexplosionyellow.get(), plasmaexplosionyellowparticle::provider);
        event.register(etshtinkerParticleType.plasmaexplosionorange.get(), plasmaexplosionorangeparticle::provider);
        event.register(etshtinkerParticleType.plasmaexplosionred.get(), plasmaexplosionredparticle::provider);
        event.register(etshtinkerParticleType.plasmaexplosionlime.get(), plasmaexplosionlimeparticle::provider);
        event.register(etshtinkerParticleType.slash.get(), slashparticle::provider);
        event.register(etshtinkerParticleType.annihilateexplosionparticle.get(), annihilateexplosionparticle::provider);
        event.register(etshtinkerParticleType.mana.get(), manaparticle::provider);
        event.register(etshtinkerParticleType.curse.get(), curseParticle::provider);
        event.register(etshtinkerParticleType.annihl.get(), annhilParticle::provider);
        event.register(etshtinkerParticleType.annihl_scatter.get(), annhilScatterParticle::provider);
    }
}
