package com.c2h6s.etshtinker.network.handler;

import com.c2h6s.etshtinker.network.packet.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class packetHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, "tinker_packet"), () -> "1", "1"::equals, "1"::equals);
    static int id = 0;

    public static void init() {
        INSTANCE.registerMessage(id++, warpattackPacket.class, warpattackPacket::encode, warpattackPacket::decode, warpattackPacket::handle);
        INSTANCE.registerMessage(id++, exoslashPacket.class, exoslashPacket::encode, exoslashPacket::decode, exoslashPacket::handle);
        INSTANCE.registerMessage(id++, enchantedswordPacket.class, enchantedswordPacket::encode, enchantedswordPacket::decode, enchantedswordPacket::handle);
        INSTANCE.registerMessage(id++, alfbeamPacket.class, alfbeamPacket::encode, alfbeamPacket::decode, alfbeamPacket::handle);
        INSTANCE.registerMessage(id++, nightslashPacket.class, nightslashPacket::encode, nightslashPacket::decode, nightslashPacket::handle);
        INSTANCE.registerMessage(id++, plasmaSlashPacket.class, plasmaSlashPacket::encode, plasmaSlashPacket::decode, plasmaSlashPacket::handle);

    }
}

