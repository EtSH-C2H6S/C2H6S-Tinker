package com.c2h6s.etshtinker.network.packet;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.c2h6s.etshtinker.screen.weaponHUD.FluidChamberData.*;

public class FluidChamberSync {
    private CompoundTag nbt;
    public FluidChamberSync(CompoundTag nbt) {;
        this.nbt =nbt;
    }
    public FluidChamberSync(FriendlyByteBuf buf){
        this.nbt =buf.readNbt();
    }

    public static void encode(FluidChamberSync packet, FriendlyByteBuf buf) {
        buf.writeNbt(packet.nbt);
    }

    public static FluidChamberSync decode(FriendlyByteBuf buf) {
        return new FluidChamberSync(buf.readNbt());
    }

    public static void handle(FluidChamberSync packet, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
                setNbt(packet.nbt);
        });
        supplier.get().setPacketHandled(true);
    }
}
