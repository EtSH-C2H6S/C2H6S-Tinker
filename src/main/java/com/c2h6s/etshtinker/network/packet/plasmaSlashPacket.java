package com.c2h6s.etshtinker.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

import static com.c2h6s.etshtinker.tools.item.tinker.ConstrainedPlasmaSaber.createSlash;


public class plasmaSlashPacket {
    public final int playerID;
    public plasmaSlashPacket(int id) {
        this.playerID =id;
    }

    public static void encode(plasmaSlashPacket packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.playerID);
    }

    public static plasmaSlashPacket decode(FriendlyByteBuf buf) {
        return new plasmaSlashPacket(buf.readInt());
    }

    public static void handle(plasmaSlashPacket packet, Supplier<NetworkEvent.Context> supplier) {
        if (supplier.get().getDirection().getReceptionSide().isServer()) {
            supplier.get().enqueueWork(() -> {
                ServerPlayer player =supplier.get().getSender();
                if (player !=null&&player.getId()==packet.playerID) {
                    createSlash(player);
                }
            });
        }
        supplier.get().setPacketHandled(true);
    }
}
