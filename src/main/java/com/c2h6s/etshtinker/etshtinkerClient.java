package com.c2h6s.etshtinker;

import com.c2h6s.etshtinker.client.book.etshtinkerBook;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import slimeknights.tconstruct.shared.CommonsClientEvents;

@Mod.EventBusSubscriber(
        modid = "etshtinker",
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = {Dist.CLIENT}
)
public class etshtinkerClient {
    @SubscribeEvent
    public static void clientCommon(FMLClientSetupEvent event) {
        etshtinkerBook.ETSH_GUIDE.fontRenderer = CommonsClientEvents.unicodeFontRender();
    }
}
