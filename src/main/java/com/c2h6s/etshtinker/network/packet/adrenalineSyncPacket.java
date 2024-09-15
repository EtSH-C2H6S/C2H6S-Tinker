package com.c2h6s.etshtinker.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.c2h6s.etshtinker.Modifiers.alfrage.trySpawnAlfBurst;
import static com.c2h6s.etshtinker.client.gui.adrenaline.AdrenalineData.setAdrenaline;

public class adrenalineSyncPacket {
    private final int amount;
    private final boolean isHelding;
    public adrenalineSyncPacket(int Am,boolean isH) {;
        amount =Am;
        isHelding =isH;
    }

    public static void encode(adrenalineSyncPacket packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.amount);
        buf.writeBoolean(packet.isHelding);
    }

    public static adrenalineSyncPacket decode(FriendlyByteBuf buf) {
        return new adrenalineSyncPacket(buf.readInt(),buf.readBoolean());
    }

    public static void handle(adrenalineSyncPacket packet, Supplier<NetworkEvent.Context> supplier) {
        if (supplier.get().getDirection().getReceptionSide().isServer()) {
            supplier.get().enqueueWork(() -> {
                setAdrenaline(packet.amount,packet.isHelding);
            });
        }
        supplier.get().setPacketHandled(true);
    }
}
