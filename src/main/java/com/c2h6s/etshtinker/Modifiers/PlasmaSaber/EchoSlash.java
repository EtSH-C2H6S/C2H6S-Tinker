package com.c2h6s.etshtinker.Modifiers.PlasmaSaber;

import com.c2h6s.etshtinker.Entities.PlasmaSlashEntity;
import com.c2h6s.etshtinker.hooks.PlasmaSlashCreateModifierHook;
import com.c2h6s.etshtinker.init.etshtinkerHook;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class EchoSlash extends Modifier implements PlasmaSlashCreateModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, etshtinkerHook.SLASH_CREATE);
    }

    @Override
    public PlasmaSlashEntity plasmaSlashCreate(IToolStackView tool, FluidStack fluidStack, ServerPlayer player, PlasmaSlashEntity slash) {
        slash.setEcho(slash.getEcho()+tool.getModifierLevel(this));
        return slash;
    }
}
