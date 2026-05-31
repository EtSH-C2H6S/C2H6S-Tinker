package com.c2h6s.etshtinker.network.packet;

import com.c2h6s.etshtinker.Modifiers.warpattack;
import com.c2h6s.etshtinker.Modifiers.warpattackex;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.function.Supplier;

public class warpattackPacket {
    public final boolean Ex;
    public warpattackPacket(boolean Ex) {
        this.Ex =Ex;
    }

    public static void encode(warpattackPacket packet, FriendlyByteBuf buf) {
        buf.writeBoolean(packet.Ex);
    }

    public static warpattackPacket decode(FriendlyByteBuf buf) {
        return new warpattackPacket(buf.readBoolean());
    }

    public static void handle(warpattackPacket packet, Supplier<NetworkEvent.Context> supplier) {
        if (supplier.get().getDirection().getReceptionSide().isServer()) {
            supplier.get().enqueueWork(() -> {
                Player player =supplier.get().getSender();
                if (player != null) {
                    if (packet.Ex){
                        warpattackex.tryWarp(player,ToolStack.from(player.getMainHandItem()),InteractionHand.MAIN_HAND);
                    }
                    warpattack.tryWarp(player,ToolStack.from(player.getMainHandItem()), InteractionHand.MAIN_HAND);
                }
            });
        }
        supplier.get().setPacketHandled(true);
    }
}
