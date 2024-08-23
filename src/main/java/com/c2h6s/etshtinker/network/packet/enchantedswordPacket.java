package com.c2h6s.etshtinker.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import static com.c2h6s.etshtinker.Modifiers.modifierenchantedsword.createEnchantedSword;

import java.util.function.Supplier;

public class enchantedswordPacket {
    public enchantedswordPacket() {;
    }

    public static void encode(enchantedswordPacket packet, FriendlyByteBuf buf) {
    }

    public static enchantedswordPacket decode(FriendlyByteBuf buf) {
        return new enchantedswordPacket();
    }

    public static void handle(enchantedswordPacket packet, Supplier<NetworkEvent.Context> supplier) {
        if (supplier.get().getDirection().getReceptionSide().isServer()) {
            supplier.get().enqueueWork(() -> {
                Player player =supplier.get().getSender();
                if (player != null) {
                    createEnchantedSword(player);
                }
            });
        }
        supplier.get().setPacketHandled(true);
    }
}
