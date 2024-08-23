package com.c2h6s.etshtinker.network.packet;

import com.c2h6s.etshtinker.Modifiers.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;


import java.util.function.Supplier;

public class exoslashPacket {
    public exoslashPacket() {;
    }

    public static void encode(exoslashPacket packet, FriendlyByteBuf buf) {
    }

    public static exoslashPacket decode(FriendlyByteBuf buf) {
        return new exoslashPacket();
    }

    public static void handle(exoslashPacket packet, Supplier<NetworkEvent.Context> supplier) {
        if (supplier.get().getDirection().getReceptionSide().isServer()) {
            supplier.get().enqueueWork(() -> {
                Player player =supplier.get().getSender();
                if (player != null) {
                    exoblademodifier.summonScattererExoslash(player);
                }
            });
        }
        supplier.get().setPacketHandled(true);
    }
}
