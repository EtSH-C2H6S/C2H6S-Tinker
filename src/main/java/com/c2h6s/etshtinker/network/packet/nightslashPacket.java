package com.c2h6s.etshtinker.network.packet;

import com.c2h6s.etshtinker.Modifiers.nightsedge;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class nightslashPacket {
    public nightslashPacket() {;
    }

    public static void encode(nightslashPacket packet, FriendlyByteBuf buf) {
    }

    public static nightslashPacket decode(FriendlyByteBuf buf) {
        return new nightslashPacket();
    }

    public static void handle(nightslashPacket packet, Supplier<NetworkEvent.Context> supplier) {
        if (supplier.get().getDirection().getReceptionSide().isServer()) {
            supplier.get().enqueueWork(() -> {
                Player player =supplier.get().getSender();
                if (player != null) {
                    nightsedge.createNightSlash(player);
                }
            });
        }
        supplier.get().setPacketHandled(true);
    }
}
