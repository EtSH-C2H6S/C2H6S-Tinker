package com.c2h6s.etshtinker.network.packet;

import com.c2h6s.etshtinker.Modifiers.warpattack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.function.Supplier;

public class warpattackPacket {
    public warpattackPacket() {;
    }

    public static void encode(warpattackPacket packet, FriendlyByteBuf buf) {
    }

    public static warpattackPacket decode(FriendlyByteBuf buf) {
        return new warpattackPacket();
    }

    public static void handle(warpattackPacket packet, Supplier<NetworkEvent.Context> supplier) {
        if (supplier.get().getDirection().getReceptionSide().isServer()) {
            supplier.get().enqueueWork(() -> {
                Player player =supplier.get().getSender();
                if (player != null) {
                    warpattack.tryWarp(player,ToolStack.from(player.getMainHandItem()),player.getUsedItemHand());
                }
            });
        }
        supplier.get().setPacketHandled(true);
    }
}
