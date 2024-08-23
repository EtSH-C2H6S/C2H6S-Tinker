package com.c2h6s.etshtinker.init;


import com.c2h6s.etshtinker.Entities.annihilateexplosionentity;
import com.c2h6s.etshtinker.Entities.plasmaexplosionentity;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import com.c2h6s.etshtinker.client.render.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;


@EventBusSubscriber(modid = MOD_ID, value = {Dist.CLIENT}, bus = Bus.MOD)
public class etshtinkerEntityRenderers {
    @SubscribeEvent
    static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(etshtinkerEntity.novascikle.get(), renderScikle::new);
        event.registerEntityRenderer(etshtinkerEntity.exoslash.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(etshtinkerEntity.shadowaxeentity.get(), renderScikle::new);
        event.registerEntityRenderer(etshtinkerEntity.lightningarrow.get(), lightningarrowRenderer::new);
        event.registerEntityRenderer(etshtinkerEntity.plasmawaveslashEntity.get(), renderSlash::new);
        event.registerEntityRenderer(etshtinkerEntity.plasmarrowEntity.get(), renderplasmarrow::new);
        event.registerEntityRenderer(etshtinkerEntity.plasmaexplosionentity.get(), renderSlash::new);
        event.registerEntityRenderer(etshtinkerEntity.slashentity.get(), renderSlash::new);
        event.registerEntityRenderer(etshtinkerEntity.phantomswordentity.get(), rendersword::new);
        event.registerEntityRenderer(etshtinkerEntity.annihilateexplosionentity.get(), renderSlash::new);
        event.registerEntityRenderer(etshtinkerEntity.enchantedswordentity.get(), renderenchantsword::new);
    }
}

