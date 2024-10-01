package com.c2h6s.etshtinker;
import com.c2h6s.etshtinker.Event.LivingEvents;
import com.c2h6s.etshtinker.Mapping.*;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshDampenToolCap;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshRadiationShieldCap;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshVibrCap;
import com.c2h6s.etshtinker.client.book.etshtinkerBook;
//import com.c2h6s.etshtinker.client.gui.adrenaline.AdrenalineHUD;
import com.c2h6s.etshtinker.config.etshtinkerConfig;
import com.c2h6s.etshtinker.init.*;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import com.c2h6s.etshtinker.init.entityReg.etshtinkerBotEntity;
import com.c2h6s.etshtinker.init.modifierReg.etshtinkerBotModifier;
import com.c2h6s.etshtinker.network.handler.packetHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifierfluxed;
import com.c2h6s.etshtinker.init.ItemReg.*;

import java.security.SecureRandom;
import static com.c2h6s.etshtinker.util.modloaded.*;


@Mod(etshtinker.MOD_ID)

public class etshtinker {
    public static final String MOD_ID = "etshtinker";
    public static ResourceLocation getResourceLoc(String id) {
        return new ResourceLocation(MOD_ID,id);
    }
    public etshtinker() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::commonSetup);
        //eventBus.addListener(this::registerGuiOverlay);
        MinecraftForge.EVENT_BUS.register(new LivingEvents());
        etshtinkerItems.ITEMS.register(eventBus);//物品
        etshtinkerModifiers.MODIFIERS.register(eventBus);//词条类
        etshtinkerFluids.FLUIDS.register(eventBus);//流体类
        etshtinkerBlocks.BLOCKS.register(eventBus);//方块
        etshtinkerEffects.EFFECT.register(eventBus);//状态
        etshtinkerEntity.ENTITIES.register(eventBus);//实体
        etshtinkerParticleType.REGISTRY.register(eventBus);//粒子
        etshtinkerConfig.init();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> etshtinkerBook::initBook);
        if (Mekenabled){
            etshtinkerMekansimMaterial.ITEMS.register(eventBus);
            etshtinkerChemicals.INFUSE_TYPES.register(eventBus);
            etshtinkerFluids.etshtinkerFluidMekanism.FLUIDS.register(eventBus);
            etshtinkerModifiers.etshMekModifier.MODIFIERS.register(eventBus);
        }
        if (Cofhloaded){
            etshtinkerThermalMaterial.ITEMS.register(eventBus);
            etshtinkerFluids.etshtinkerFluidThermal.FLUIDS.register(eventBus);
        }
        if (BOTloaded){
            etshtinkerBotaniaMaterial.ITEMS.register(eventBus);
            etshtinkerBotEntity.ENTITIES.register(eventBus);
            etshtinkerBotModifier.MODIFIERS.register(eventBus);
        }
        if (Powahloaded){
            etshtinkerPowahMaterial.ITEMS.register(eventBus);
        }
        if (Adastraloaded){
            etshtinkerAdastraMaterial.ITEMS.register(eventBus);
            etshtinkerFluids.etshtinkerFluidAdastra.FLUIDS.register(eventBus);
        }
        if (IEloaded){
            etshtinkerIEMaterial.ITEMS.register(eventBus);
            etshtinkerFluids.etshtinkerFluidIE.FLUIDS.register(eventBus);
        }
        if (AE2loaded){
            etshtinkerAEMaterial.ITEMS.register(eventBus);
            etshtinkerFluids.etshtinkerFluidAE.FLUIDS.register(eventBus);
        }
        if (CYCloaded){
            etshtinkerFluids.etshtinkerFluidCyclic.FLUIDS.register(eventBus);
        }
        if (MBOTloaded){
            etshtinkerFluids.etshtinkerFluidMBOT.FLUIDS.register(eventBus);
        }
        if (Mekenabled&&AE2loaded&&Cofhloaded) {
            etshtinkerItems.configuredMaterial.ITEMSC.register(eventBus);
            etshtinkerFluids.moltenExoAlloy.FLUIDSa.register(eventBus);
        }


    }
    public static synchronized SecureRandom EtSHrnd(){
        return new SecureRandom();
    }
    private void commonSetup(FMLCommonSetupEvent event) {
        ToolCapabilityProvider.register(etshmodifierfluxed::new);
        ToolCapabilityProvider.register(etshDampenToolCap::new);
        ToolCapabilityProvider.register(etshVibrCap::new);
        if (Mekenabled){
            ToolCapabilityProvider.register(etshRadiationShieldCap::new);
        }
        event.enqueueWork(etshtinkerMaterialStats::setup);
        event.enqueueWork(ionizerFluidMap::extendMap);
        packetHandler.init();
        if (Mekenabled){
            event.enqueueWork(ionizerFluidMapMek::extendMap);
        }
    }
    @SubscribeEvent
    public void registerGuiOverlay(RegisterGuiOverlaysEvent event){
        if (FMLEnvironment.dist == Dist.CLIENT) {
            //event.registerAboveAll("adrenalin", AdrenalineHUD.ADRENALINE_OVERLAY);
        }
    }
}