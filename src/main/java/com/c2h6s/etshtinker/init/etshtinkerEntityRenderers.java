package com.c2h6s.etshtinker.init;


import com.c2h6s.etshtinker.init.entityReg.etshtinkerBotEntity;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import com.c2h6s.etshtinker.client.render.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;
import static com.c2h6s.etshtinker.util.modloaded.BOTloaded;


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
        event.registerEntityRenderer(etshtinkerEntity.plasmaexplosionentity.get(), NoopRenderer::new);
        event.registerEntityRenderer(etshtinkerEntity.slashentity.get(), renderSlash::new);
        event.registerEntityRenderer(etshtinkerEntity.phantomswordentity.get(), rendersword::new);
        event.registerEntityRenderer(etshtinkerEntity.annihilateexplosionentity.get(), NoopRenderer::new);
        event.registerEntityRenderer(etshtinkerEntity.enchantedswordentity.get(), renderenchantsword::new);
        event.registerEntityRenderer(etshtinkerEntity.nights_slash_entity.get(), renderNightSlash::new);
        event.registerEntityRenderer(etshtinkerEntity.nights_slash_entity_b.get(), renderNightSlash::new);
        event.registerEntityRenderer(etshtinkerEntity.plasma_slash_red.get(), plasmaSlashRenderer::new);
        event.registerEntityRenderer(etshtinkerEntity.plasma_slash_rainbow.get(), plasmaSlashRenderer::new);
        event.registerEntityRenderer(etshtinkerEntity.plasma_slash_lime.get(), plasmaSlashRenderer::new);
        event.registerEntityRenderer(etshtinkerEntity.plasma_slash_anti.get(), plasmaSlashRenderer::new);
        event.registerEntityRenderer(etshtinkerEntity.plasma_slash_blue.get(), plasmaSlashRenderer::new);
        event.registerEntityRenderer(etshtinkerEntity.plasma_slash_cyan.get(), plasmaSlashRenderer::new);
        event.registerEntityRenderer(etshtinkerEntity.plasma_slash_dark.get(), plasmaSlashRenderer::new);
        event.registerEntityRenderer(etshtinkerEntity.plasma_slash_green.get(), plasmaSlashRenderer::new);
        event.registerEntityRenderer(etshtinkerEntity.plasma_slash_orange.get(), plasmaSlashRenderer::new);
        event.registerEntityRenderer(etshtinkerEntity.plasma_slash_purple.get(), plasmaSlashRenderer::new);
        event.registerEntityRenderer(etshtinkerEntity.plasma_slash_yellow.get(), plasmaSlashRenderer::new);
        if (BOTloaded){
            event.registerEntityRenderer(etshtinkerBotEntity.ALFBURST.get(), NoopRenderer::new);
        }
    }
}

