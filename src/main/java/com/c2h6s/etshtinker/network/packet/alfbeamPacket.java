package com.c2h6s.etshtinker.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.c2h6s.etshtinker.Modifiers.alfrage.trySpawnAlfBurst;

public class alfbeamPacket {
    public alfbeamPacket() {;
    }

    public static void encode(alfbeamPacket packet, FriendlyByteBuf buf) {
    }

    public static alfbeamPacket decode(FriendlyByteBuf buf) {
        return new alfbeamPacket();
    }

    public static void handle(alfbeamPacket packet, Supplier<NetworkEvent.Context> supplier) {
        if (supplier.get().getDirection().getReceptionSide().isServer()) {
            supplier.get().enqueueWork(() -> {
                Player player =supplier.get().getSender();
                if (player != null) {
                    trySpawnAlfBurst(player);
                }
            });
        }
        supplier.get().setPacketHandled(true);
    }
}
