package com.c2h6s.etshtinker.Modifiers.modifiers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;
import slimeknights.tconstruct.library.tools.nbt.IModDataView;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import com.c2h6s.etshtinker.init.etshtinkerToolStats;
import java.util.function.Supplier;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class etshmodifierfluxed implements IEnergyStorage, ToolCapabilityProvider.IToolCapabilityProvider {
    public static final ResourceLocation MAX_ENERGY = new ResourceLocation(MOD_ID, "max_energy");
    public static final ResourceLocation STORED_ENERGY = new ResourceLocation(MOD_ID, "stored_energy");
    public static final ResourceLocation ENERGY_OWNER = new ResourceLocation(MOD_ID, "energy_owner");
    public static final int MAX_TRANSFER_RATE = 100000000;
    public final Supplier<? extends IToolStackView> tool;
    public final LazyOptional<IEnergyStorage> capOptional;

    public etshmodifierfluxed(ItemStack stack, Supplier<? extends IToolStackView> toolStack) {
        this.tool = toolStack;
        this.capOptional = LazyOptional.of(() -> {
            return this;
        });
    }

    public static int receiveEnergy(IToolStackView tool, int maxReceive, boolean simulate) {
        int energyStored = getEnergyStored(tool);
        int energyReceived = Math.min(getMaxEnergyStored(tool) - energyStored, maxReceive);
        if (!simulate) {
            ModDataNBT persistentData = tool.getPersistentData();
            persistentData.putInt(STORED_ENERGY, energyStored + energyReceived);
        }
        return energyReceived;
    }

    public static boolean removeEnergy(IToolStackView tool, int energyRemoved, boolean simulate, boolean drain) {
        int energyStored = getEnergyStored(tool);
        ModDataNBT persistentData;
        if (energyStored < energyRemoved) {
            if (drain && !simulate) {
                persistentData = tool.getPersistentData();
                persistentData.putInt(STORED_ENERGY, 0);
            }
            return false;
        }
        else {
            if (!simulate) {
                persistentData = tool.getPersistentData();
                persistentData.putInt(STORED_ENERGY, energyStored - energyRemoved);
            }
            return true;
        }
    }

    public static int getEnergyStored(IToolStackView tool) {
        ModDataNBT persistentData = tool.getPersistentData();
        return persistentData.contains(STORED_ENERGY, 3) ? persistentData.getInt(STORED_ENERGY) : 0;
    }

    public static int getMaxEnergyStored(IToolStackView tool) {
        IModDataView volatileData = tool.getVolatileData();
        if (volatileData.contains(MAX_ENERGY, 3)) {
            int energy_store = tool.getStats().getInt(etshtinkerToolStats.ENERGY_STORE);
            return energy_store > 0 ? volatileData.getInt(MAX_ENERGY) + energy_store : volatileData.getInt(MAX_ENERGY);
        } else {
            return 0;
        }
    }

    public <T> LazyOptional<T> getCapability(IToolStackView tool, Capability<T> cap) {
        return tool.getVolatileData().getInt(MAX_ENERGY) > 0 && cap == ForgeCapabilities.ENERGY ? ForgeCapabilities.ENERGY.orEmpty(cap, this.capOptional) : LazyOptional.empty();
    }

    public int receiveEnergy(int maxReceive, boolean simulate) {
        return receiveEnergy(this.tool.get(), getMaxEnergyStored(this.tool.get())/20, simulate);
    }

    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    public int getEnergyStored() {
        return getEnergyStored((IToolStackView)this.tool.get());
    }

    public int getMaxEnergyStored() {
        return getMaxEnergyStored((IToolStackView)this.tool.get());
    }

    public boolean canExtract() {
        return false;
    }

    public boolean canReceive() {
        return true;
    }
}
