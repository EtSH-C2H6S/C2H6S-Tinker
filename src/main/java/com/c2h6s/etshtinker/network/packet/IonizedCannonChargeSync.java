package com.c2h6s.etshtinker.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.c2h6s.etshtinker.screen.weaponHUD.IonizedCannonDrawtime.*;

public class IonizedCannonChargeSync {
    private float percentage;
    public IonizedCannonChargeSync(float percentage) {;
        this.percentage =percentage;
    }
    public IonizedCannonChargeSync(FriendlyByteBuf buf){
        this.percentage =buf.readFloat();
    }

    public static void encode(IonizedCannonChargeSync packet, FriendlyByteBuf buf) {
        buf.writeFloat(packet.percentage);
    }

    public static IonizedCannonChargeSync decode(FriendlyByteBuf buf) {
        return new IonizedCannonChargeSync(buf.readFloat());
    }

    public static void handle(IonizedCannonChargeSync packet, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            setPercentage(packet.percentage);
        });
        supplier.get().setPacketHandled(true);
    }
}
