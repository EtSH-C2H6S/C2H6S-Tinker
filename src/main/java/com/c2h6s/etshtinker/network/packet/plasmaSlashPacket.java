package com.c2h6s.etshtinker.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.c2h6s.etshtinker.tools.item.tinker.ConstrainedPlasmaSaber.createSlash;


public class plasmaSlashPacket {
    public plasmaSlashPacket() {;
    }

    public static void encode(plasmaSlashPacket packet, FriendlyByteBuf buf) {
    }

    public static plasmaSlashPacket decode(FriendlyByteBuf buf) {
        return new plasmaSlashPacket();
    }

    public static void handle(plasmaSlashPacket packet, Supplier<NetworkEvent.Context> supplier) {
        if (supplier.get().getDirection().getReceptionSide().isServer()) {
            supplier.get().enqueueWork(() -> {
                ServerPlayer player =supplier.get().getSender();
                if (player !=null) {
                    createSlash(player);
                }
            });
        }
        supplier.get().setPacketHandled(true);
    }
}
